package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;

public class WurstplusPosManager {

    private static double x;
    private static double y;
    private static double z;
    private static boolean onground;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void updatePosition() {
        x = mc.player.posX;
        y = mc.player.posY;
        z = mc.player.posZ;
        onground = mc.player.onGround;
    }

    public static void restorePosition() {
        mc.player.posX = x;
        mc.player.posY = y;
        mc.player.posZ = z;
        mc.player.onGround = onground;
    }

}
