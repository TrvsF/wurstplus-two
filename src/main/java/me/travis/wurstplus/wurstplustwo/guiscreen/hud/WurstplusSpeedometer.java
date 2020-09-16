package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class WurstplusSpeedometer extends WurstplusPinnable {

    public WurstplusSpeedometer() {
        super("Speedometer", "Speedometer", 1, 0, 0);
    }

    @Override
    public void render() {

        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        final double x = mc.player.posX - mc.player.prevPosX;
        final double z = mc.player.posZ - mc.player.prevPosZ;
        final float tr = (mc.timer.tickLength / 1000.0f);

        String bps = "M/s: " + new DecimalFormat("#.#").format(MathHelper.sqrt(x * x + z * z) / tr);

        create_line(bps, this.docking(1, bps), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(bps, "width") + 2);
        this.set_height(this.get(bps, "height")  + 2);

    }

}