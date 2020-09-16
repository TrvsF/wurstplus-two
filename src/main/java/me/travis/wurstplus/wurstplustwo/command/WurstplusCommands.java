package me.travis.wurstplus.wurstplustwo.command;

import me.travis.turok.values.TurokString;
import me.travis.wurstplus.wurstplustwo.command.commands.*;
import net.minecraft.util.text.Style;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class WurstplusCommands {
	public static ArrayList<WurstplusCommand> command_list = new ArrayList<>();
	static HashMap<java.lang.String, WurstplusCommand> list_command  = new HashMap<>();

	public static final TurokString prefix = new TurokString("Prefix", "Prefix", ".");

	public final Style style;

	public WurstplusCommands(Style style_) {
		style = style_;

		add_command(new WurstplusBind());
		add_command(new WurstplusPrefix());
		add_command(new WurstplusSettings());
		add_command(new WurstplusToggle());
		add_command(new WurstplusAlert());
		add_command(new WurstplusHelp());
		add_command(new WurstplusFriend());
		add_command(new WurstplusDrawn());
		add_command(new WurstplusEzMessage());
		add_command(new WurstplusEnemy());
		add_command(new WurstplusConfig());

		command_list.sort(Comparator.comparing(WurstplusCommand::get_name));
	}

	public static void add_command(WurstplusCommand command) {
		command_list.add(command);

		list_command.put(command.get_name().toLowerCase(), command);
	}

	public java.lang.String[] get_message(java.lang.String message) {
		java.lang.String[] arguments = {};

		if (has_prefix(message)) {
			arguments = message.replaceFirst(prefix.get_value(), "").split(" ");
		}

		return arguments;
	}

	public boolean has_prefix(java.lang.String message) {
		return message.startsWith(prefix.get_value());
	}

	public void set_prefix(java.lang.String new_prefix) {
		prefix.set_value(new_prefix);
	}

	public java.lang.String get_prefix() {
		return prefix.get_value();
	}

	public static ArrayList<WurstplusCommand> get_pure_command_list() {
		return command_list;
	}

	public static WurstplusCommand get_command_with_name(java.lang.String name) {
		return list_command.get(name.toLowerCase());
	}
}