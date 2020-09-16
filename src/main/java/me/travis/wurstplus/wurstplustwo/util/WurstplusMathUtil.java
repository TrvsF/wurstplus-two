package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class WurstplusMathUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static Random random = new Random();
    
    public static Vec3d interpolateEntity(Entity entity, float time)
    {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }

    public static double[] directionSpeedNoForward(double speed)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = 1f;
        
        if (mc.gameSettings.keyBindLeft.isPressed() || mc.gameSettings.keyBindRight.isPressed() || mc.gameSettings.keyBindBack.isPressed() || mc.gameSettings.keyBindForward.isPressed())
            forward = mc.player.movementInput.moveForward;
        
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw
                + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

        if (forward != 0)
        {
            if (side > 0)
            {
                yaw += (forward > 0 ? -45 : 45);
            }
            else if (side < 0)
            {
                yaw += (forward > 0 ? 45 : -45);
            }
            side = 0;

            // forward = clamp(forward, 0, 1);
            if (forward > 0)
            {
                forward = 1;
            }
            else if (forward < 0)
            {
                forward = -1;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90));
        final double cos = Math.cos(Math.toRadians(yaw + 90));
        final double posX = (forward * speed * cos + side * speed * sin);
        final double posZ = (forward * speed * sin - side * speed * cos);
        return new double[]
        { posX, posZ };
    }
    
    public static Vec3d mult(Vec3d factor, Vec3d multiplier)
    {
        return new Vec3d(factor.x * multiplier.x, factor.y * multiplier.y, factor.z * multiplier.z);
    }

    public static Vec3d mult(Vec3d factor, float multiplier)
    {
        return new Vec3d(factor.x * multiplier, factor.y * multiplier, factor.z * multiplier);
    }

    public static Vec3d div(Vec3d factor, Vec3d divisor)
    {
        return new Vec3d(factor.x / divisor.x, factor.y / divisor.y, factor.z / divisor.z);
    }

    public static Vec3d div(Vec3d factor, float divisor)
    {
        return new Vec3d(factor.x / divisor, factor.y / divisor, factor.z / divisor);
    }

    public static float clamp2(float num, float min, float max)
    {
        if (num < min)
        {
            return min;
        }
        else
        {
            return num > max ? max : num;
        }
    }

    // linearly maps value from the range (a..b) to (c..d)
    public static double map(double value, double a, double b, double c, double d)
    {
        // first map value from (a..b) to (0..1)
        value = (value - a) / (b - a);
        // then map it from (0..1) to (c..d) and return it
        return c + value * (d - c);
    }

    public static double linear(double from, double to, double incline)
    {
        return (from < to - incline) ? (from + incline) : ((from > to + incline) ? (from - incline) : to);
    }

    public static double parabolic(double from, double to, double incline)
    {
        return from + (to - from) / incline;
    }

    public static double getDistance(Vec3d pos, double x, double y, double z)
    {
        final double deltaX = pos.x - x;
        final double deltaY = pos.y - y;
        final double deltaZ = pos.z - z;
        return (double) MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public static double[] calcIntersection(double[] line, double[] line2)
    {
        final double a1 = line[3] - line[1];
        final double b1 = line[0] - line[2];
        final double c1 = a1 * line[0] + b1 * line[1];

        final double a2 = line2[3] - line2[1];
        final double b2 = line2[0] - line2[2];
        final double c2 = a2 * line2[0] + b2 * line2[1];

        final double delta = a1 * b2 - a2 * b1;

        return new double[]
        { (b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta };
    }
    
    public static double calculateAngle(double x1, double y1, double x2, double y2)
    {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil( -angle / 360 ) * 360;

        return angle;
    }

    public static int getRandom(final int min, final int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static double getRandom(final double min, final double max) {
        return MathHelper.clamp(min + random.nextDouble() * max, min, max);
    }

    public static float getRandom(final float min, final float max) {
        return MathHelper.clamp(min + random.nextFloat() * max, min, max);
    }

    public static int clamp(final int num, final int min, final int max) {
        return (num < min) ? min : Math.min(num, max);
    }

    public static float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : Math.min(num, max);
    }

    public static double clamp(final double num, final double min, final double max) {
        return (num < min) ? min : Math.min(num, max);
    }

    public static float sin(final float value) {
        return MathHelper.sin(value);
    }

    public static float cos(final float value) {
        return MathHelper.cos(value);
    }

    public static float wrapDegrees(final float value) {
        return MathHelper.wrapDegrees(value);
    }

    public static double wrapDegrees(final double value) {
        return MathHelper.wrapDegrees(value);
    }

    public static Vec3d roundVec(final Vec3d vec3d, final int places) {
        return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
    }

    public static double square(final double input) {
        return input * input;
    }

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static float wrap(final float valI) {
        float val = valI % 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }

    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }

    public static float round(final float value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.floatValue();
    }

//    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map, final boolean descending) {
//        final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
//        if (descending) {
//            list.sort((Comparator<? super Map.Entry<K, V>>)Map.Entry.comparingByValue(Comparator.reverseOrder()));
//        }
//        else {
//            list.sort(Map.Entry.comparingByValue());
//        }
//        final Map<K, V> result = new LinkedHashMap<K, V>();
//        for (final Map.Entry<K, V> entry : list) {
//            result.put(entry.getKey(), entry.getValue());
//        }
//        return result;
//    }

    public static String getTimeOfDay() {
        final Calendar c = Calendar.getInstance();
        final int timeOfDay = c.get(11);
        if (timeOfDay < 12) {
            return "Good Morning ";
        }
        if (timeOfDay < 16) {
            return "Good Afternoon ";
        }
        if (timeOfDay < 21) {
            return "Good Evening ";
        }
        return "Good Night ";
    }

    public static double radToDeg(final double rad) {
        return rad * 57.295780181884766;
    }

    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }

    public static double getIncremental(final double val, final double inc) {
        final double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }

    public static double[] directionSpeed(final double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }

    public static List<Vec3d> getBlockBlocks(final Entity entity) {
        final List<Vec3d> vec3ds = new ArrayList<Vec3d>();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        final double y = entity.posY;
        final double minX = round(bb.minX, 0);
        final double minZ = round(bb.minZ, 0);
        final double maxX = round(bb.maxX, 0);
        final double maxZ = round(bb.maxZ, 0);
        if (minX != maxX) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(maxX, y, minZ));
            if (minZ != maxZ) {
                vec3ds.add(new Vec3d(minX, y, maxZ));
                vec3ds.add(new Vec3d(maxX, y, maxZ));
                return vec3ds;
            }
        }
        else if (minZ != maxZ) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(minX, y, maxZ));
            return vec3ds;
        }
        vec3ds.add(entity.getPositionVector());
        return vec3ds;
    }

    public static boolean areVec3dsAlignedRetarded(final Vec3d vec3d1, final Vec3d vec3d2) {
        final BlockPos pos1 = new BlockPos(vec3d1);
        final BlockPos pos2 = new BlockPos(vec3d2.x, vec3d1.y, vec3d2.z);
        return pos1.equals((Object)pos2);
    }

    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }

    static {
        random = new Random();
    }

}