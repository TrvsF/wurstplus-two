package me.travis.wurstplus.wurstplustwo.guiscreen.hud;


import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimeUtil;

public class WurstplusTime extends WurstplusPinnable {
    
    public WurstplusTime() {
        super("Time", "Time", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "";

		line += WurstplusTimeUtil.get_hour() < 10 ? "0" + WurstplusTimeUtil.get_hour() : WurstplusTimeUtil.get_hour();
		line += ":";
		line += WurstplusTimeUtil.get_minuite() < 10 ? "0" + WurstplusTimeUtil.get_minuite() : WurstplusTimeUtil.get_minuite();
		line += ":";
		line += WurstplusTimeUtil.get_second() < 10 ? "0" + WurstplusTimeUtil.get_second() : WurstplusTimeUtil.get_second();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}

}