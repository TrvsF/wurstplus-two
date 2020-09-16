package me.travis.wurstplus.wurstplustwo.hacks.combat;


import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WurstplusTrap extends WurstplusHack {

    public WurstplusTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name        = "Trap";
        this.tag         = "Trap";
        this.description = "cover people in obsidian :o";
    }

    WurstplusSetting place_mode = create("Place Mode", "TrapPlaceMode", "Extra", combobox("Extra", "Face", "Normal", "Feet"));
    WurstplusSetting blocks_per_tick = create("Speed", "TrapSpeed", 4, 0, 8);
    WurstplusSetting rotate = create("Rotation", "TrapRotation", true);
    WurstplusSetting chad_mode = create("Chad Mode", "TrapChadMode", true);
    WurstplusSetting range = create("Range", "TrapRange", 4, 1, 6);
    WurstplusSetting swing = create("Swing", "TrapSwing", "Mainhand", combobox("Mainhand", "Offhand", "Both", "None"));

    private final Vec3d[] offsets_default = new Vec3d[]{
        new Vec3d(0.0, 0.0, -1.0),
        new Vec3d(1.0, 0.0, 0.0), 
        new Vec3d(0.0, 0.0, 1.0), 
        new Vec3d(-1.0, 0.0, 0.0), 
        new Vec3d(0.0, 1.0, -1.0), 
        new Vec3d(1.0, 1.0, 0.0), 
        new Vec3d(0.0, 1.0, 1.0), 
        new Vec3d(-1.0, 1.0, 0.0), 
        new Vec3d(0.0, 2.0, -1.0), 
        new Vec3d(1.0, 2.0, 0.0), 
        new Vec3d(0.0, 2.0, 1.0), 
        new Vec3d(-1.0, 2.0, 0.0), 
        new Vec3d(0.0, 3.0, -1.0), 
        new Vec3d(0.0, 3.0, 1.0), 
        new Vec3d(1.0, 3.0, 0.0), 
        new Vec3d(-1.0, 3.0, 0.0), 
        new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsets_face = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(1.0, 1.0, 0.0),
            new Vec3d(0.0, 1.0, 1.0),
            new Vec3d(-1.0, 1.0, 0.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsets_feet = new Vec3d[]{
            new Vec3d(0.0, 0.0, -1.0),
            new Vec3d(1.0, 0.0, 0.0),
            new Vec3d(0.0, 0.0, 1.0),
            new Vec3d(-1.0, 0.0, 0.0),
            new Vec3d(0.0, 1.0, -1.0),
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(1.0, 2.0, 0.0),
            new Vec3d(0.0, 2.0, 1.0),
            new Vec3d(-1.0, 2.0, 0.0),
            new Vec3d(0.0, 3.0, -1.0),
            new Vec3d(0.0, 3.0, 1.0),
            new Vec3d(1.0, 3.0, 0.0),
            new Vec3d(-1.0, 3.0, 0.0),
            new Vec3d(0.0, 3.0, 0.0)
    };

    private final Vec3d[] offsets_extra = new Vec3d[]{
        new Vec3d(0.0, 0.0, -1.0), 
        new Vec3d(1.0, 0.0, 0.0), 
        new Vec3d(0.0, 0.0, 1.0), 
        new Vec3d(-1.0, 0.0, 0.0), 
        new Vec3d(0.0, 1.0, -1.0), 
        new Vec3d(1.0, 1.0, 0.0), 
        new Vec3d(0.0, 1.0, 1.0), 
        new Vec3d(-1.0, 1.0, 0.0), 
        new Vec3d(0.0, 2.0, -1.0), 
        new Vec3d(1.0, 2.0, 0.0), 
        new Vec3d(0.0, 2.0, 1.0), 
        new Vec3d(-1.0, 2.0, 0.0), 
        new Vec3d(0.0, 3.0, -1.0), 
        new Vec3d(0.0, 3.0, 0.0), 
        new Vec3d(0.0, 4.0, 0.0)
    };

    private String last_tick_target_name = "";

    private int offset_step = 0;
    private int timeout_ticker = 0;
    private int y_level;

    private boolean first_run = true;

    @Override
	public void enable() {

        timeout_ticker = 0;

        y_level = (int) Math.round(mc.player.posY);

        first_run = true;

        if (find_obi_in_hotbar() == -1) {
            this.set_disable();
        }

	}
    
    @Override
	public void update() {

        int timeout_ticks = 20;
        if (timeout_ticker > timeout_ticks && chad_mode.get_value(true)) {
            timeout_ticker =  0;
            this.set_disable();
            return;
        }

        EntityPlayer closest_target = find_closest_target();

        if (closest_target == null) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }

        if (chad_mode.get_value(true) && (int) Math.round(mc.player.posY) != y_level) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }

        if (first_run) {

            first_run = false;
            last_tick_target_name = closest_target.getName();

        } else if (!last_tick_target_name.equals(closest_target.getName())) {

            last_tick_target_name = closest_target.getName();
            offset_step = 0;

        }

        final List<Vec3d> place_targets = new ArrayList<Vec3d>();

        if (place_mode.in("Normal")) {
            Collections.addAll(place_targets, offsets_default);
        } else if (place_mode.in("Extra")) {
            Collections.addAll(place_targets, offsets_extra);
        } else if (place_mode.in("Feet")) {
            Collections.addAll(place_targets, offsets_feet);
        } else {
            Collections.addAll(place_targets, offsets_face);
        }

        int blocks_placed = 0;

        while (blocks_placed < blocks_per_tick.get_value(1)) {

            if (offset_step >= place_targets.size()) {
                offset_step = 0;
                break;
            }

            final BlockPos offset_pos = new BlockPos((Vec3d) place_targets.get(offset_step));
            final BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = true;

            if (!mc.world.getBlockState(target_pos).getMaterial().isReplaceable()) 
                should_try_place = false;
            
                for (final Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(target_pos))) {

                    if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                        should_try_place = false;
                        break;
                    }

                }

                if (should_try_place && WurstplusBlockUtil.placeBlock(target_pos, find_obi_in_hotbar(), rotate.get_value(true), rotate.get_value(true), swing)) {
                    ++blocks_placed;
                }

                offset_step++;

        }

        timeout_ticker++;

    }

    private int find_obi_in_hotbar()
    {
        for (int i = 0; i < 9; ++i)
        {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock)
            {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest)
                    return i;
                
                else if (block instanceof BlockObsidian)
                    return i;
                
            }
        }
        return -1;
    }

    public EntityPlayer find_closest_target()  {

    	if (mc.world.playerEntities.isEmpty())
    		return null;
    	
    	EntityPlayer closestTarget = null;
    	
        for (final EntityPlayer target : mc.world.playerEntities) 
        {
            if (target == mc.player)
                continue;
            
            if (WurstplusFriendUtil.isFriend(target.getName()))
                continue;
            
            if (!WurstplusEntityUtil.isLiving((Entity)target))
                continue;
            
            if (target.getHealth() <= 0.0f)
                continue;

            if (closestTarget != null)
            	if (mc.player.getDistance(target) > mc.player.getDistance(closestTarget))
            		continue;

            closestTarget = target;
        }
        
        return closestTarget;
    }
    
}

