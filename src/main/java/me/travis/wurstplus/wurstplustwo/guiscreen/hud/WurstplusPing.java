package me.travis.wurstplus.wurstplustwo.guiscreen.hud;


import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusPing extends WurstplusPinnable {
    
    public WurstplusPing() {
        super("Ping", "Ping", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "Ping: " + get_ping();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
    }
    
    public String get_ping() {
        try {
            int ping = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
		if (ping <= 50) {
			return "\u00A7a"+Integer.toString(ping);
		} else if (ping <= 150) {
			return "\u00A73"+Integer.toString(ping);
		} else {
			return "\u00A74"+Integer.toString(ping);
		}
        } catch (Exception e) {
            return "oh no";
        }
		
	}

}