package me.travis.wurstplus.wurstplustwo.command.commands;


import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;


public class WurstplusPrefix extends WurstplusCommand {
	public WurstplusPrefix() {
		super("prefix", "Change prefix.");
	}

	public boolean get_message(String[] message) {
		String prefix = "null";

		if (message.length > 1) {
			prefix = message[1];
		}

		if (message.length > 2) {
			WurstplusMessageUtil.send_client_error_message(current_prefix() + "prefix <character>");

			return true;
		}

		if (prefix.equals("null")) {
			WurstplusMessageUtil.send_client_error_message(current_prefix() + "prefix <character>");

			return true;
		}

		WurstplusCommandManager.set_prefix(prefix);

		WurstplusMessageUtil.send_client_message("The new prefix is " + prefix);

		return true;
	}
}