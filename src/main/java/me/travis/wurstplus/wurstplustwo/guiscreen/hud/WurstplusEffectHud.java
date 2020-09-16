package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WurstplusEffectHud extends WurstplusPinnable {

    public WurstplusEffectHud() {
        super("Effect Hud", "effecthud", 1, 0, 0);
    }

    @Override
    public void render() {

        int counter = 12;

        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        final List<PotionEffect> effects = new ArrayList<>(mc.player.getActivePotionEffects());

        final Comparator<PotionEffect> comparator = (first, second) -> {

            final String first_effect = get_friendly_potion_name(first) + " " + ChatFormatting.GRAY + Potion.getPotionDurationString(first, 1.0F);
            final String second_effect = get_friendly_potion_name(second) + " " + ChatFormatting.GRAY + Potion.getPotionDurationString(second, 1.0F);
            final float dif = mc.fontRenderer.getStringWidth(second_effect) - mc.fontRenderer.getStringWidth(first_effect);
            return dif != 0 ? (int) dif : second_effect.compareTo(first_effect);

        };

        effects.sort(comparator);

        for (PotionEffect effect : effects) {
            if (effect.getPotion() == MobEffects.STRENGTH) {
                final String e = ChatFormatting.DARK_RED + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString(effect, 1.0f);
                create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
            } else if (effect.getPotion() == MobEffects.SPEED) {
                final String e = ChatFormatting.BLUE + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString(effect, 1.0f);
                create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
            } else if (effect.getPotion() == MobEffects.WEAKNESS) {
                final String e = ChatFormatting.GRAY + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString(effect, 1.0f);
                create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
            } else if (effect.getPotion() == MobEffects.JUMP_BOOST) {
                final String e = ChatFormatting.GREEN + get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString(effect, 1.0f);
                create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
            } else if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDAllPotions").get_value(true)) {
                final String e = get_friendly_potion_name(effect) + " " + Potion.getPotionDurationString(effect, 1.0f);
                create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
            }

        }

        this.set_width(this.get("weakness", "width") + 12);
        this.set_height(this.get("weakness", "height") + 36);

    }

    public static String get_friendly_potion_name(PotionEffect potionEffect) {
        
        String effectName = I18n.format(potionEffect.getPotion().getName());
        if (potionEffect.getAmplifier() == 1) {
            effectName = effectName + " " + I18n.format("enchantment.level.2");
        } else if (potionEffect.getAmplifier() == 2) {
            effectName = effectName + " " + I18n.format("enchantment.level.3");
        } else if (potionEffect.getAmplifier() == 3) {
            effectName = effectName + " " + I18n.format("enchantment.level.4");
        }

        return effectName;
    }

    public static String get_name_duration_string(PotionEffect potionEffect) {
        return String.format("%s (%s)", get_friendly_potion_name(potionEffect), Potion.getPotionDurationString(potionEffect, 1.0F));
    }

}
