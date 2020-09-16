package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusTextureHelper {

    final static Minecraft mc = Minecraft.getMinecraft();
    
    public static void drawTexture(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height) {
        GL11.glPushMatrix();
        final float size = width / 2.0f;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0f / size, 0.0f / size);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(0.0f / size, (0.0f + size) / size);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2d((0.0f + size) / size, (0.0f + size) / size);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2d((0.0f + size) / size, 0.0f / size);
        GL11.glVertex2d((x + width), y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.DIAMOND_HELMET), 999999, 999999); // this NEEDS to be here or everything gets fucked (don't ask me why)
    }
    
    public static void bindTexture(final ResourceLocation resourceLocation) {
        try {
            ITextureObject texture = mc.getTextureManager().getTexture(resourceLocation);
            GL11.glBindTexture(3553, texture.getGlTextureId());
        } catch (Exception ignored) {}
    }

}