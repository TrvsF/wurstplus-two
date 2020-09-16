package me.travis.wurstplus.wurstplustwo.hacks.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class WurstplusMiddleClickFriends extends WurstplusHack {
    
    public WurstplusMiddleClickFriends() {
        super(WurstplusCategory.WURSTPLUS_MISC);

		this.name        = "Middleclick Friends";
		this.tag         = "MiddleclickFriends";
		this.description = "you press button and the world becomes a better place :D";
    }

    private boolean clicked = false;

    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    @Override
	public void update() {
        
        if (mc.currentScreen != null) {
            return;
        }

        if (!Mouse.isButtonDown(2)) {
            clicked = false;
            return;
        }

        if (!clicked) {

            clicked = true;

            final RayTraceResult result = mc.objectMouseOver;

            if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY) {
                return;
            }

            if (!(result.entityHit instanceof EntityPlayer)) return;

            Entity player = result.entityHit;

            if (WurstplusFriendUtil.isFriend(player.getName())) {

                WurstplusFriendUtil.Friend f = WurstplusFriendUtil.friends.stream().filter(friend -> friend.getUsername().equalsIgnoreCase(player.getName())).findFirst().get();
                WurstplusFriendUtil.friends.remove(f);
                WurstplusMessageUtil.send_client_message("Player " + red + bold + player.getName() + reset + " is now not your friend :(");
                            
            } else {
                WurstplusFriendUtil.Friend f = WurstplusFriendUtil.get_friend_object(player.getName());
                WurstplusFriendUtil.friends.add(f);
                WurstplusMessageUtil.send_client_message("Player " + green + bold + player.getName() + reset + " is now your friend :D");
            }

        }

	}

}