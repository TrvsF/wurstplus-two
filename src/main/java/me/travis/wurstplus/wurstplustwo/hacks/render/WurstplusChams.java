package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class WurstplusChams extends WurstplusHack {

    public WurstplusChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Chams";
        this.tag = "Chams";
        this.description = "see even less (now with epic colours)";
    }

    WurstplusSetting mode = create("Mode", "ChamsMode", "Outline", combobox("Outline", "Wireframe"));
    WurstplusSetting players = create("Players", "ChamsPlayers", true);
    WurstplusSetting mobs = create("Mobs", "ChamsMobs", true);
    WurstplusSetting self = create("Self", "ChamsSelf", true);
    WurstplusSetting items = create("Items", "ChamsItems", true);
    WurstplusSetting xporbs = create("Xp Orbs", "ChamsXPO", true);
    WurstplusSetting xpbottles = create("Xp Bottles", "ChamsBottles", true);
    WurstplusSetting pearl = create("Pearls", "ChamsPearls", true);
    WurstplusSetting top = create("Top", "ChamsTop", true);
    WurstplusSetting scale = create("Factor", "ChamsFactor", 0, -1f, 1f);
    WurstplusSetting r = create("R", "ChamsR", 255, 0, 255);
    WurstplusSetting g = create("G", "ChamsG", 255, 0, 255);
    WurstplusSetting b = create("B", "ChamsB", 255, 0, 255);
    WurstplusSetting a = create("A", "ChamsA", 100, 0, 255);
    WurstplusSetting box_a = create("Box A", "ChamsABox", 100, 0, 255);
    WurstplusSetting width = create("Width", "ChamsWdith", 2, 0.5, 5);
    WurstplusSetting rainbow_mode = create("Rainbow", "ChamsRainbow", false);
    WurstplusSetting sat = create("Satiation", "ChamsSatiation", 0.8, 0, 1);
    WurstplusSetting brightness = create("Brightness", "ChamsBrightness", 0.8, 0, 1);

    @Override
    public void update() {
        if (rainbow_mode.get_value(true)) {
            cycle_rainbow();
        }
    }

    public void cycle_rainbow() {

        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };

        int color_rgb_o = Color.HSBtoRGB(tick_color[0], sat.get_value(1), brightness.get_value(1));

        r.set_value((color_rgb_o >> 16) & 0xFF);
        g.set_value((color_rgb_o >> 8) & 0xFF);
        b.set_value(color_rgb_o & 0xFF);

    }

    @Override
    public void render(WurstplusEventRender event) {
        if (items.get_value(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityItem && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.get_value(1)), r.get_value(1) / 255.0f, g.get_value(1) / 255.0f, b.get_value(1) / 255.0f, box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.get_value(1)), new Color(r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (xporbs.get_value(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityXPOrb && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.get_value(1)), r.get_value(1) / 255.0f, g.get_value(1) / 255.0f, b.get_value(1) / 255.0f, box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.get_value(1)), new Color(r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (pearl.get_value(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderPearl && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.get_value(1)), r.get_value(1) / 255.0f, g.get_value(1) / 255.0f, b.get_value(1) / 255.0f, box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.get_value(1)), new Color(r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

        if (xpbottles.get_value(true)) {
            int i = 0;
            for (final Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle && mc.player.getDistanceSq(entity) < 2500.0) {
                    final Vec3d interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.0f);
                    RenderGlobal.renderFilledBox(bb.grow(scale.get_value(1)), r.get_value(1) / 255.0f, g.get_value(1) / 255.0f, b.get_value(1) / 255.0f, box_a.get_value(1) / 255.0f);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    WurstplusRenderUtil.drawBlockOutline(bb.grow(scale.get_value(1)), new Color(r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1)), 1);
                    if (++i >= 50) {
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void on_render_model(final WurstplusEventRenderEntityModel event) {
        if (event.stage != 0 || event.entity == null || !self.get_value(true) && event.entity.equals(mc.player) || !players.get_value(true) && event.entity instanceof EntityPlayer || !mobs.get_value(true) && event.entity instanceof EntityMob) {
            return;
        }
        final Color color = new Color(r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1));
        final boolean fancyGraphics = mc.gameSettings.fancyGraphics;
        mc.gameSettings.fancyGraphics = false;
        final float gamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10000.0f;
        if (top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (mode.in("outline")) {
            WurstplusRenderUtil.renderOne(width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)width.get_value(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)width.get_value(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)width.get_value(1));
            WurstplusRenderUtil.renderFive();
        }
        else {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1028, 6913);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.glLineWidth((float)width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            mc.gameSettings.fancyGraphics = fancyGraphics;
            mc.gameSettings.gammaSetting = gamma;
        }
        catch (Exception ignore) {}
        event.cancel();
    }

}
