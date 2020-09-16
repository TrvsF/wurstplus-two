package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class WurstplusSurroundBlocks extends WurstplusPinnable {

    public WurstplusSurroundBlocks() {
        super("Surround Blocks", "SurroundBlocks", 1, 0, 0);
    }

    @Override
    public void render() {

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        
        Block west = get_neg_x();
        Block east = get_pos_x();
        Block north = get_neg_z();
        Block south = get_pos_z();

        switch (WurstplusPlayerUtil.GetFacing())
        {
            case North:
                west = get_neg_x();
                east = get_pos_x();
                north = get_neg_z();
                south = get_pos_z();
                break;
            case East:
                west = get_neg_z();
                east = get_pos_z();
                north = get_pos_x();
                south = get_neg_x();
                break;
            case South:
                west = get_pos_x();
                east = get_neg_x();
                north = get_pos_z();
                south = get_neg_z();
                break;
            case West:
                west = get_pos_z();
                east = get_neg_z();
                north = get_neg_x();
                south = get_pos_x();
                break;
            default:
                break;
        }

        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(west), this.get_x()-20, this.get_y());
        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(east), this.get_x()+20, this.get_y());
        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(north), this.get_x(), this.get_y()-20);
        mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(south), this.get_x(), this.get_y()+20);

        // create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b);

        RenderHelper.disableStandardItemLighting();			
		GlStateManager.popMatrix();

        this.set_width(50);
        this.set_height(25);
    }

    public Block get_neg_x() {
        BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        Block b = mc.world.getBlockState(player_block.west()).getBlock();
        if (b != null) {
            return b;
        }
        return null;
    }

    public Block get_pos_x() {
        BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        Block b = mc.world.getBlockState(player_block.east()).getBlock();
        if (b != null) {
            return b;
        }
        return null;
    }

    public Block get_pos_z() {
        BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        Block b = mc.world.getBlockState(player_block.south()).getBlock();
        if (b != null) {
            return b;
        }
        return null;
    }

    public Block get_neg_z() {
        BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        Block b = mc.world.getBlockState(player_block.north()).getBlock();
        if (b != null) {
            return b;
        }
        return null;
    }
}