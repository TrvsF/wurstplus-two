package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper.ValidResult;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class WurstplusSocks extends WurstplusHack {

    // if you use this ur actually bad

    public WurstplusSocks() {
		super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "Socks"; 
		this.tag         = "Socks";
		this.description = "Protects your feet";
    }

    WurstplusSetting rotate = create("Rotate", "SocksRotate", false);
    WurstplusSetting swing = create("Swing", "SocksSwing", "Mainhand", combobox("Mainhand", "Offhand", "Both", "None"));

    @Override
    protected void enable() {
        if (find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
    }

    @Override
	public void update() {

        final int slot = find_in_hotbar();

        if (slot == -1) return;

        BlockPos center_pos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        ArrayList<BlockPos> blocks_to_fill = new ArrayList<>();

        switch (WurstplusPlayerUtil.GetFacing())
        {
            case East:
                blocks_to_fill.add(center_pos.east().east());
                blocks_to_fill.add(center_pos.east().east().up());
                blocks_to_fill.add(center_pos.east().east().east());
                blocks_to_fill.add(center_pos.east().east().east().up());
                break;
            case North:
                blocks_to_fill.add(center_pos.north().north());
                blocks_to_fill.add(center_pos.north().north().up());
                blocks_to_fill.add(center_pos.north().north().north());
                blocks_to_fill.add(center_pos.north().north().north().up());
                break;
            case South:
                blocks_to_fill.add(center_pos.south().south());
                blocks_to_fill.add(center_pos.south().south().up());
                blocks_to_fill.add(center_pos.south().south().south());
                blocks_to_fill.add(center_pos.south().south().south().up());
                break;
            case West:
                blocks_to_fill.add(center_pos.west().west());
                blocks_to_fill.add(center_pos.west().west().up());
                blocks_to_fill.add(center_pos.west().west().west());
                blocks_to_fill.add(center_pos.west().west().west().up());
                break;
            default:
                break;
        }

        BlockPos pos_to_fill = null;

        for (BlockPos pos : blocks_to_fill) {

            ValidResult result = WurstplusBlockInteractHelper.valid(pos);

            if (result != ValidResult.Ok) continue;

            if (pos == null) continue;

            pos_to_fill = pos;
            break;

        }

        WurstplusBlockUtil.placeBlock(pos_to_fill, find_in_hotbar(), rotate.get_value(true), rotate.get_value(true), swing);

    }

    private int find_in_hotbar() {

        for (int i = 0; i < 9; ++i) {

            final ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {

                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest)
                    return i;
                
                else if (block instanceof BlockObsidian)
                    return i;
                
            }
        }
        return -1;
    }

}