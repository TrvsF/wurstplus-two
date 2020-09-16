package me.travis.wurstplus.wurstplustwo.hacks;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;

public class WurstplusClickHUD extends WurstplusHack {

	public WurstplusClickHUD() {
		super(WurstplusCategory.WURSTPLUS_GUI);

		this.name        = "HUD";
		this.tag         = "HUD";
		this.description = "gui for pinnables";
	}

	WurstplusSetting frame_view = create("info", "HUDStringsList", "Strings");

	WurstplusSetting strings_r = create("Color R", "HUDStringsColorR", 255, 0, 255);
	WurstplusSetting strings_g = create("Color G", "HUDStringsColorG", 255, 0, 255);
	WurstplusSetting strings_b = create("Color B", "HUDStringsColorB", 255, 0, 255);
	WurstplusSetting strings_a = create("Alpha", "HUDStringsColorA", 230, 0, 255);
	WurstplusSetting compass_scale = create("Compass Scale", "HUDCompassScale", 16, 1, 60);
	WurstplusSetting arraylist_mode = create("ArrayList", "HUDArrayList", "Free", combobox("Free", "Top R", "Top L", "Bottom R", "Bottom L"));
	WurstplusSetting show_all_pots = create("All Potions", "HUDAllPotions", false);
	WurstplusSetting max_player_list = create("Max Players", "HUDMaxPlayers", 24, 1, 64);

	@Override
	public void enable() {
		if (mc.world != null && mc.player != null) {
			Wurstplus.get_hack_manager().get_module_with_tag("GUI").set_active(false);
				
			Wurstplus.click_hud.back = false;

			mc.displayGuiScreen(Wurstplus.click_hud);
		}
	}
}