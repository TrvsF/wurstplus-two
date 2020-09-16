package me.travis.wurstplus.wurstplustwo.hacks.misc;


import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class WurstplusAutoNomadHut extends WurstplusHack {
    
    public WurstplusAutoNomadHut() {

        super(WurstplusCategory.WURSTPLUS_MISC);

        this.name = "Auto NomadHut";
        this.tag = "AutoNomadHut";
        this.description = "i fucking hate fit";
    }

    WurstplusSetting rotate         = create("Rotate",           "NomadSmoth",       true);
	WurstplusSetting triggerable    = create("Toggle",			 "NomadToggle", 		true);
	WurstplusSetting tick_for_place = create("Blocks per tick",	 "NomadTickToPlace", 2, 1, 8);

    Vec3d[] targets = new Vec3d[] { new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 1.0), new Vec3d(2.0, 0.0, -1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(1.0, 0.0, 2.0), new Vec3d(-1.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(-1.0, 0.0, -2.0), new Vec3d(1.0, 0.0, -2.0), new Vec3d(2.0, 1.0, -1.0), new Vec3d(2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, 0.0), new Vec3d(-2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(-1.0, 1.0, 2.0), new Vec3d(0.0, 1.0, -2.0), new Vec3d(1.0, 1.0, -2.0), new Vec3d(-1.0, 1.0, -2.0), new Vec3d(2.0, 2.0, -1.0), new Vec3d(2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 2.0), new Vec3d(-1.0, 2.0, 2.0), new Vec3d(1.0, 2.0, -2.0), new Vec3d(-1.0, 2.0, -2.0), new Vec3d(2.0, 3.0, 0.0), new Vec3d(2.0, 3.0, -1.0), new Vec3d(2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, 0.0), new Vec3d(-2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 2.0), new Vec3d(1.0, 3.0, 2.0), new Vec3d(-1.0, 3.0, 2.0), new Vec3d(0.0, 3.0, -2.0), new Vec3d(1.0, 3.0, -2.0), new Vec3d(-1.0, 3.0, -2.0), new Vec3d(0.0, 4.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(-1.0, 4.0, 0.0), new Vec3d(0.0, 4.0, 1.0), new Vec3d(0.0, 4.0, -1.0), new Vec3d(1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, -1.0), new Vec3d(1.0, 4.0, -1.0), new Vec3d(2.0, 4.0, 0.0), new Vec3d(2.0, 4.0, 1.0), new Vec3d(2.0, 4.0, -1.0) };

    int new_slot    	= 0;
	int old_slot    	= 0;
	int y_level 		= 0;
	int tick_runs  		= 0;
	int blocks_placed 	= 0;
    int offset_step 	= 0;
    
    boolean sneak = false;

    @Override
	public void enable() {

		if (mc.player != null) {

			old_slot = mc.player.inventory.currentItem;
			new_slot = find_in_hotbar();

			if (new_slot == -1) {
				WurstplusMessageUtil.send_client_error_message("cannot find obi in hotbar");
				set_active(false);
			}

			y_level = (int) Math.round(mc.player.posY);

		}

	}

	@Override
	public void disable() {

		if (mc.player != null) {

			if (new_slot != old_slot && old_slot != - 1) {
				mc.player.inventory.currentItem = old_slot;
			}

			if (sneak) {
				mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

				sneak = false;
			}

			old_slot = - 1;
			new_slot = - 1;
		}

	}

	@Override
	public void update() {

		if (mc.player != null) {

			blocks_placed = 0;

			while (blocks_placed < this.tick_for_place.get_value(1)) {

				if (this.offset_step >= this.targets.length) {
					this.offset_step = 0;
					break;
				}

				BlockPos offsetPos = new BlockPos(this.targets[this.offset_step]);
				BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ()).down();

				boolean try_to_place = true;

				if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
					try_to_place = false;
				}

				for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
					if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
					try_to_place = false;
					break;
				}

				if (try_to_place && this.place_blocks(targetPos)) {
					++blocks_placed;
				}

				++offset_step;

			}

			if (blocks_placed > 0 && this.new_slot != this.old_slot) {
				mc.player.inventory.currentItem = this.old_slot;
			}

			++this.tick_runs;

		}
	}

	private boolean place_blocks(BlockPos pos) {

        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
		}
		
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
		}
		
        for (EnumFacing side : EnumFacing.values()) {

            Block neighborPos;
			BlockPos neighbor = pos.offset(side);
			
			EnumFacing side2 = side.getOpposite();
			
			if (!WurstplusBlockInteractHelper.canBeClicked(neighbor)) continue;
			
			mc.player.inventory.currentItem = new_slot;

            if (WurstplusBlockInteractHelper.blackList.contains((Object)(neighborPos = mc.world.getBlockState(neighbor).getBlock()))) {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.sneak = true;
			}
			
			Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
			
            if (this.rotate.get_value(true)) {
                WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
			}
			
            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
			mc.player.swingArm(EnumHand.MAIN_HAND);
			
            return true;
		}
		
		return false;
		
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