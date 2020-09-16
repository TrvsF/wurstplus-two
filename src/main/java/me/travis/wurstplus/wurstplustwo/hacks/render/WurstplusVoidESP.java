package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class WurstplusVoidESP extends WurstplusHack {

    public WurstplusVoidESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name        = "Void ESP";
        this.tag         = "VoidESP";
        this.description = "OH FUCK A DEEP HOLE";
    }

    WurstplusSetting void_radius = create("Range", "VoidESPRange", 6, 1, 10);

    public final List<BlockPos> void_blocks = new ArrayList<>();

    private final ICamera camera = new Frustum();

    @Override
    public void update() {
        
        if (mc.player == null) return;

        void_blocks.clear();

        final Vec3i player_pos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);

        for (int x = player_pos.getX() - void_radius.get_value(1); x < player_pos.getX() + void_radius.get_value(1); x++) {
            for (int z = player_pos.getZ() - void_radius.get_value(1); z < player_pos.getZ() + void_radius.get_value(1); z++) {
                for (int y = player_pos.getY() + void_radius.get_value(1); y > player_pos.getY() - void_radius.get_value(1); y--) {
                    final BlockPos blockPos = new BlockPos(x, y, z);

                    if (is_void_hole(blockPos))
                        void_blocks.add(blockPos);
                }
            }
        }

    }

    public boolean is_void_hole(BlockPos blockPos) {
        if (blockPos.getY() != 0)
            return false;

        if (mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR)
            return false;

        return true;

    }

    @Override
	public void render(WurstplusEventRender event) {

        int r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);

        new ArrayList<>(void_blocks).forEach(pos -> {

            final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mc.getRenderManager().viewerPosX, pos.getY() - mc.getRenderManager().viewerPosY,
                    pos.getZ() - mc.getRenderManager().viewerPosZ, pos.getX() + 1 - mc.getRenderManager().viewerPosX, pos.getY() + 1 - mc.getRenderManager().viewerPosY,
                    pos.getZ() + 1 - mc.getRenderManager().viewerPosZ);

            camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

            if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ,
                    bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ)))
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                GL11.glLineWidth(1.5f);

                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 255, 20, 30, 0.50f);
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 255, 20, 30, 0.22f);

                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        });

    }
    
}