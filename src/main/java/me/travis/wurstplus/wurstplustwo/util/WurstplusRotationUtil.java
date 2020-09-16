package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusRotationUtil {

   private static final Minecraft mc = Minecraft.getMinecraft();

   private static float yaw;
   private static float pitch;

   public static void updateRotations() {
      yaw = mc.player.rotationYaw;
      pitch = mc.player.rotationPitch;
   }

   public static void restoreRotations() {
      mc.player.rotationYaw = yaw;
      mc.player.rotationYawHead = yaw;
      mc.player.rotationPitch = pitch;
   }

   public static void setPlayerRotations(final float yaw, final float pitch) {
      mc.player.rotationYaw = yaw;
      mc.player.rotationYawHead = yaw;
      mc.player.rotationPitch = pitch;
   }

   public void setPlayerYaw(final float yaw) {
      mc.player.rotationYaw = yaw;
      mc.player.rotationYawHead = yaw;
   }

   public void lookAtPos(final BlockPos pos) {
      final float[] angle = WurstplusMathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
      this.setPlayerRotations(angle[0], angle[1]);
   }

   public void lookAtVec3d(final Vec3d vec3d) {
      final float[] angle = WurstplusMathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
      this.setPlayerRotations(angle[0], angle[1]);
   }

   public void lookAtVec3d(final double x, final double y, final double z) {
      final Vec3d vec3d = new Vec3d(x, y, z);
      this.lookAtVec3d(vec3d);
   }

   public void lookAtEntity(final Entity entity) {
      final float[] angle = WurstplusMathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionEyes(mc.getRenderPartialTicks()));
      this.setPlayerRotations(angle[0], angle[1]);
   }

   public void setPlayerPitch(final float pitch) {
      mc.player.rotationPitch = pitch;
   }

   public float getYaw() {
      return yaw;
   }

   public void setYaw(final float yaw) {
      WurstplusRotationUtil.yaw = yaw;
   }

   public float getPitch() {
      return pitch;
   }

   public void setPitch(final float pitch) {
      WurstplusRotationUtil.pitch = pitch;
   }

   public int getDirection4D() {
      return getDirection4D();
   }

   public String getDirection4D(final boolean northRed) {
      return getDirection4D(northRed);
   }
    
}