package me.travis.wurstplus.wurstplustwo.util;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class WurstplusBlockUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static List<Block> emptyBlocks;
    public static List<Block> rightclickableBlocks;

    static
    {
        emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE);
        rightclickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }

    public static boolean canSeeBlock(final BlockPos p_Pos) {
        return mc.player != null && mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(p_Pos.getX(), p_Pos.getY(), p_Pos.getZ()), false, true, false) == null;
    }

    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
        final RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }

    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), pos.getY() + height, pos.getZ()), false, true, false) == null;
    }

    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }


    public static void openBlock(BlockPos pos)
    {
        EnumFacing[] facings = EnumFacing.values();

        for (EnumFacing f : facings)
        {
            Block neighborBlock = mc.world.getBlockState(pos.offset(f)).getBlock();

            if (emptyBlocks.contains(neighborBlock))
            {
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos, f.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);

                return;
            }
        }
    }

    public static void swingArm(WurstplusSetting setting) {
        if (setting.in("Mainhand") || setting.in("Both")) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (setting.in("Offhand") || setting.in("Both")) {
            mc.player.swingArm(EnumHand.OFF_HAND);
        }
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack, WurstplusSetting setting) {
        if (isBlockEmpty(pos)) {
            int old_slot = -1;
            if (slot != mc.player.inventory.currentItem) {
                old_slot = mc.player.inventory.currentItem;
                mc.player.inventory.currentItem = slot;
            }

            EnumFacing[] facings = EnumFacing.values();

            for (EnumFacing f : facings) {
                Block neighborBlock = mc.world.getBlockState(pos.offset(f)).getBlock();
                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) f.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) f.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) f.getZOffset() * 0.5D);

                if (!emptyBlocks.contains(neighborBlock) && mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25D) {
                    float[] rot = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};

                    if (rotate) {
                        rotatePacket(vec.x, vec.y, vec.z);
                    }

                    if (rightclickableBlocks.contains(neighborBlock)) {
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
                    }

                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(f), f.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                    if (rightclickableBlocks.contains(neighborBlock)) {
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
                    }

                    if (rotateBack) {
                        mc.player.connection.sendPacket(new Rotation(rot[0], rot[1], mc.player.onGround));
                    }

                    swingArm(setting);

                    if (old_slot != -1) {
                        mc.player.inventory.currentItem = old_slot;
                    }

                    return true;
                }
            }

        }

        return false;
    }

    public static boolean isBlockEmpty(BlockPos pos) {
        try {
            if (emptyBlocks.contains(mc.world.getBlockState(pos).getBlock())) {
                AxisAlignedBB box = new AxisAlignedBB(pos);
                Iterator entityIter = mc.world.loadedEntityList.iterator();

                Entity e;

                do {
                    if (!entityIter.hasNext()) {
                        return true;
                    }

                    e = (Entity) entityIter.next();
                } while (!(e instanceof EntityLivingBase) || !box.intersects(e.getEntityBoundingBox()));

            }
        } catch (Exception ignored) { }
        return false;
    }

    public static boolean canPlaceBlock(BlockPos pos)
    {
        if (isBlockEmpty(pos)) {
            EnumFacing[] facings = EnumFacing.values();

            for (EnumFacing f : facings) {
                if (!emptyBlocks.contains(mc.world.getBlockState(pos.offset(f)).getBlock()) && mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(new Vec3d(pos.getX() + 0.5D + (double) f.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) f.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) f.getZOffset() * 0.5D)) <= 4.25D) {
                    return true;
                }
            }

        }
        return false;
    }

    public static void rotatePacket(double x, double y, double z)
    {
        double diffX = x - mc.player.posX;
        double diffY = y - (mc.player.posY + (double) mc.player.getEyeHeight());
        double diffZ = z - mc.player.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

        mc.player.connection.sendPacket(new Rotation(yaw, pitch, mc.player.onGround));
    }
}
