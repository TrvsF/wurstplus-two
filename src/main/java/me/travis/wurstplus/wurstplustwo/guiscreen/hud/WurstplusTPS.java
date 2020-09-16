package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventHandler;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusTPS extends WurstplusPinnable {

    public WurstplusTPS() {
        super("TPS", "TPS", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "TPS: " + getTPS();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
    }

    public String getTPS() {
        try {
            int tps = Math.round(WurstplusEventHandler.INSTANCE.get_tick_rate());
        if (tps >= 16) {
            return "\u00A7a"+Integer.toString(tps);
        } else if (tps >= 10) {
            return "\u00A73"+Integer.toString(tps);
        } else {
            return "\u00A74"+Integer.toString(tps);
        }
        } catch (Exception e) {
            return "oh no " +e;
        }
    }
    
}