package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.item.*;

public class WurstplusDamageUtil {

    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }

    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }

    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }

}
