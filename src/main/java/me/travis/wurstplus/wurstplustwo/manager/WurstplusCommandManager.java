 package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.command.WurstplusCommands;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

 public class WurstplusCommandManager {

	public static WurstplusCommands command_list;

	public WurstplusCommandManager() {
		command_list = new WurstplusCommands(new Style().setColor(TextFormatting.BLUE));
	}

	public static void set_prefix(String new_prefix) {
		command_list.set_prefix(new_prefix);
	}

	public static String get_prefix() {
		return command_list.get_prefix();
	}

}