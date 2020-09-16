package me.travis.wurstplus.wurstplustwo.hacks.chat;


import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusClearChat extends WurstplusHack {
    public WurstplusClearChat() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Clear Chatbox";
        this.tag = "ClearChatbox";
        this.description = "Removes the default minecraft chat outline.";
    }
}