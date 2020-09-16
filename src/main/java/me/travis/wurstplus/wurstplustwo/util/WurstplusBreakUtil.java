package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class WurstplusBreakUtil {

    private final static Minecraft mc = Minecraft.getMinecraft();

    private static BlockPos current_block = null;
    private static boolean is_mining = false;

    public static void set_current_block(BlockPos pos) {
        current_block = pos;
    }

    private static boolean is_done(IBlockState state) {
        return state.getBlock() == Blocks.BEDROCK || state.getBlock() == Blocks.AIR || state.getBlock() instanceof BlockLiquid;
    }

    public static boolean update(float range, boolean ray_trace) {
        if (current_block == null) return false;

        IBlockState state = mc.world.getBlockState(current_block);

        if (is_done(state) || mc.player.getDistanceSq(current_block) > (range*range)) {
            current_block = null;
            return false;
        }

        mc.player.swingArm(EnumHand.MAIN_HAND);

        EnumFacing facing = EnumFacing.UP;

        if (ray_trace) {
            RayTraceResult r = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ),
                    new Vec3d(current_block.getX() + 0.5, current_block.getY() - 0.5, current_block.getZ() + 0.5));

            if (r != null && r.sideHit != null) {
                facing = r.sideHit;
            }
        }

        if (!is_mining) {
            is_mining = true;
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, current_block, facing));
        } else {
            mc.playerController.onPlayerDamageBlock(current_block, facing);
        }

        return true;

    }


}
