package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper.ValidResult;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSelfTrap extends WurstplusHack {
    
    public WurstplusSelfTrap() {

        super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "Self Trap";
		this.tag         = "SelfTrap";
		this.description = "oh 'eck, ive trapped me sen again";
    }

    WurstplusSetting toggle = create("Toggle", "SelfTrapToggle", false);
    WurstplusSetting rotate = create("Rotate", "SelfTrapRotate", false);
    WurstplusSetting swing = create("Swing", "SelfTrapSwing", "Mainhand", combobox("Mainhand", "Offhand", "Both", "None"));

    private BlockPos trap_pos;

    @Override
    protected void enable() {
        if (find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
    }

    @Override
    public void update() {
        final Vec3d pos = WurstplusMathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
        trap_pos = new BlockPos(pos.x, pos.y + 2, pos.z);
        if (is_trapped()) {

            if (!toggle.get_value(true)) {
                toggle();
                return;
            } 

        }

        ValidResult result = WurstplusBlockInteractHelper.valid(trap_pos);

        if (result == ValidResult.AlreadyBlockThere && !mc.world.getBlockState(trap_pos).getMaterial().isReplaceable()) {
            return;
        } 

        if (result == ValidResult.NoNeighbors) {

            BlockPos[] tests = {
                trap_pos.north(),
                trap_pos.south(),
                trap_pos.east(),
                trap_pos.west(),
                trap_pos.up(),
                trap_pos.down().west() // ????? salhack is weird and i dont care enough to remove this. who the fuck uses this shit anyways fr fucking jumpy
            };

            for (BlockPos pos_ : tests) {

                ValidResult result_ = WurstplusBlockInteractHelper.valid(pos_);

                if (result_ == ValidResult.NoNeighbors || result_ == ValidResult.NoEntityCollision) continue;

                if (WurstplusBlockUtil.placeBlock(pos_, find_in_hotbar(), rotate.get_value(true), rotate.get_value(true), swing)) {
                    return;
                }

            }

            return;

        }

        WurstplusBlockUtil.placeBlock(trap_pos, find_in_hotbar(), rotate.get_value(true), rotate.get_value(true), swing);

    }

    public boolean is_trapped() {

        if (trap_pos == null) return false;

        IBlockState state = mc.world.getBlockState(trap_pos);

        return state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.LAVA;

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