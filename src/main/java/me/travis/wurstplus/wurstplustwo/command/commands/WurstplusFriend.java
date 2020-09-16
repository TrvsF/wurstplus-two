package me.travis.wurstplus.wurstplustwo.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

public class WurstplusFriend extends WurstplusCommand {
    
    public WurstplusFriend() {
        super("friend", "To add friends");
    }

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            WurstplusMessageUtil.send_client_message("Add - add friend");
            WurstplusMessageUtil.send_client_message("Del - delete friend");
            WurstplusMessageUtil.send_client_message("List - list friends");

            return true;
        }

        if (message.length == 2) {
            if (message[1].equalsIgnoreCase("list")) {
                if (WurstplusFriendUtil.friends.isEmpty()) {
                    WurstplusMessageUtil.send_client_message("You appear to have " + red + bold + "no" + reset + " friends :(");
                } else {
                    for (WurstplusFriendUtil.Friend friend : WurstplusFriendUtil.friends) {
                        WurstplusMessageUtil.send_client_message("" + green + bold +  friend.getUsername());
                    }
                }
                return true;
            } else {
                if (WurstplusFriendUtil.isFriend(message[1])) {
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[1] + reset + " is your friend :D");
                    return true;
                } else {
                    WurstplusMessageUtil.send_client_error_message("Player " + red + bold + message[1] + reset + " is not your friend :(");
                    return true;
                }
            }
        }

        if (message.length >= 3) {
            if (message[1].equalsIgnoreCase("add")) {
                if (WurstplusFriendUtil.isFriend(message[2])) {
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[2] + reset + " is already your friend :D");
                    return true;
                } else {
                    WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object(message[2]);
                    if (f == null) {
                        WurstplusMessageUtil.send_client_error_message("Cannot find " + red + bold + "UUID" + reset + " for that player :(");
                        return true;
                    }
                    WurstplusFriendUtil.friends.add(f);
                    WurstplusMessageUtil.send_client_message("Player " + green + bold + message[2] + reset + " is now your friend :D");
                    return true;
                }
            } else if (message[1].equalsIgnoreCase("del") || message[1].equalsIgnoreCase("remove") || message[1].equalsIgnoreCase("delete")) {
                if (!WurstplusFriendUtil.isFriend(message[2])) {
                    WurstplusMessageUtil.send_client_message("Player " + red + bold + message[2] + reset + " is already not your friend :/");
                    return true;
                } else {
                    WurstplusFriendUtil.Friend f = WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(message[2])).findFirst().get();
                    WurstplusFriendUtil.friends.remove(f);
                    WurstplusMessageUtil.send_client_message("Player " + red + bold + message[2]  + reset + " is now not your friend :(");
                    return true;
                }
            }
        }

        return true;

    }

}