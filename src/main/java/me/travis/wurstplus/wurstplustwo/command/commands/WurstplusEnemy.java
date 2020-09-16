package me.travis.wurstplus.wurstplustwo.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

public class WurstplusEnemy extends WurstplusCommand {

    public WurstplusEnemy() {
        super("enemy", "To add enemy");
    }

    public static ChatFormatting red = ChatFormatting.GREEN;
    public static ChatFormatting green = ChatFormatting.RED;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_message("Add - add enemy");
            WurstplusMessageUtil.send_client_message("Del - delete enemy");
            WurstplusMessageUtil.send_client_message("List - list enemies");

            return true;
        }

        if (message.length == 2) {
            if (message[1].equalsIgnoreCase("list")) {
                if (WurstplusEnemyUtil.enemies.isEmpty()) {
                    WurstplusMessageUtil.send_client_message("You appear to have " + red + bold + "no" + reset + " enemies :)");
                } else {
                    for (WurstplusEnemyUtil.Enemy Enemy : WurstplusEnemyUtil.enemies) {
                        WurstplusMessageUtil.send_client_message("" + green + bold +  Enemy.getUsername());
                    }
                }
                return true;
            } else {
                if (WurstplusEnemyUtil.isEnemy(message[1])) {
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[1] + reset + " is your Enemy D:");
                    return true;
                } else {
                    WurstplusMessageUtil.send_client_error_message("Player " + red + bold + message[1] + reset + " is not your Enemy :)");
                    return true;
                }
            }
        }

        if (message.length >= 3) {
            if (message[1].equalsIgnoreCase("add")) {
                if (WurstplusEnemyUtil.isEnemy(message[2])) {
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[2] + reset + " is already your Enemy D:");
                    return true;
                } else {
                    WurstplusEnemyUtil.Enemy f = WurstplusEnemyUtil.get_enemy_object(message[2]);
                    if (f == null) {
                        WurstplusMessageUtil.send_client_error_message("Cannot find " + red + bold + "UUID" + reset + " for that player :(");
                        return true;
                    }
                    WurstplusEnemyUtil.enemies.add(f);
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[2] + reset + " is now your Enemy D:");
                    return true;
                }
            } else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                if (!WurstplusEnemyUtil.isEnemy(message[2])) {
                    WurstplusMessageUtil.send_client_message("Player " + red + bold + message[2] + reset + " is already not your Enemy :/");
                    return true;
                } else {
                    WurstplusEnemyUtil.Enemy f = WurstplusEnemyUtil.enemies.stream().filter(Enemy -> Enemy.getUsername().equalsIgnoreCase(message[2])).findFirst().get();
                    WurstplusEnemyUtil.enemies.remove(f);
                    WurstplusMessageUtil.send_client_message("Player " + red + bold + message[2]  + reset + " is now not your Enemy :)");
                    return true;
                }
            }
        }

        return true;

    }

}