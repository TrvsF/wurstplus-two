package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Random;

// Zero alpine manager.


public class WurstplusChatSuffix extends WurstplusHack {
	public WurstplusChatSuffix() {
		super(WurstplusCategory.WURSTPLUS_CHAT);

		this.name        = "Chat Suffix";
		this.tag         = "ChatSuffix";
		this.description = "show off how cool u are";
	}

	WurstplusSetting ignore = create("Ignore", "ChatSuffixIgnore", true);
	WurstplusSetting type   = create("Type", "ChatSuffixType", "Default", combobox("Default", "Random"));

	boolean accept_suffix;
	boolean suffix_default;
	boolean suffix_random;

	StringBuilder suffix;

	String[] random_client_name = {
		"chad",
		"tabott",
		"travis",
		"unco",
		"kiwi",
		"xdolf",
		"eightsixfour",
		"biggus",
		"naughty",
		"jumpy",
		"chae",
		"wurst",
		"buttfuhk"
	};

	String[] random_client_finish = {
		" plus",
		" epic",
		"god",
		" sex",
		" blue",
		" brown",
		" gay",
		"plus",
		""
	};

	@EventHandler
	private Listener<WurstplusEventPacket.SendPacket> listener = new Listener<>(event -> {
		// If not be the CPacketChatMessage return.
		if (!(event.get_packet() instanceof CPacketChatMessage)) {
			return;
		}

		// Start event suffix.
		accept_suffix = true;

		// Get value.
		boolean ignore_prefix = ignore.get_value(true);

		String message = ((CPacketChatMessage) event.get_packet()).getMessage();

		// If is with some caracther.
		if (message.startsWith("/")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("\\") && ignore_prefix) accept_suffix = false;
		if (message.startsWith("!")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith(":")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith(";")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith(".")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith(",")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("@")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("&")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("*")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("$")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("#")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith("(")  && ignore_prefix) accept_suffix = false;
		if (message.startsWith(")")  && ignore_prefix) accept_suffix = false;

		// Compare the values type.
		if (type.in("Default")) {
			suffix_default = true;
			suffix_random  = false;
		}

		if (type.in("Random")) {
			suffix_default = false;
			suffix_random  = true;
		}

		// If accept.
		if (accept_suffix) {
			if (suffix_default) {
				// Just default.
				message += Wurstplus.WURSTPLUS_SIGN + convert_base("wurstplus two");
			}

			if (suffix_random) {
				// Create first the string builder.
				StringBuilder suffix_with_randoms = new StringBuilder();

				// Convert the base using the TravisFont.
				suffix_with_randoms.append(convert_base(random_string(random_client_name)));
				suffix_with_randoms.append(convert_base(random_string(random_client_finish)));

				message += Wurstplus.WURSTPLUS_SIGN + suffix_with_randoms.toString(); 
			}

			// If message 256 string length substring.
			if (message.length() >= 256) {
				message.substring(0, 256);
			}
		}

		// Send the message.
		((CPacketChatMessage) event.get_packet()).message = message;
	});

	// Get the random values string.
	public String random_string(String[] list) {
		return list[new Random().nextInt(list.length)];
	}

	// Convert the base using the TravisFont.
	public String convert_base(String base) {
		return Wurstplus.smoth(base);
	}

	@Override
	public String array_detail() {
		// Update the detail.
		return this.type.get_current_value();
	}
}