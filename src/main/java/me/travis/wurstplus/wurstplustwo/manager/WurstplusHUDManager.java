package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.guiscreen.hud.*;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

import java.util.ArrayList;
import java.util.Comparator;


public class WurstplusHUDManager {

	public static ArrayList<WurstplusPinnable> array_hud = new ArrayList<>();

	public WurstplusHUDManager() {

		add_component_pinnable(new WurstplusWatermark());
		add_component_pinnable(new WurstplusArrayList());
		add_component_pinnable(new WurstplusCoordinates());
		add_component_pinnable(new WurstplusInventoryPreview());
		add_component_pinnable(new WurstplusInventoryXCarryPreview());
		add_component_pinnable(new WurstplusArmorPreview());
		add_component_pinnable(new WurstplusUser());
		add_component_pinnable(new WurstplusTotemCount());
		add_component_pinnable(new WurstplusCrystalCount());
		add_component_pinnable(new WurstplusEXPCount());
		add_component_pinnable(new WurstplusGappleCount());
		add_component_pinnable(new WurstplusTime());
		add_component_pinnable(new WurstplusLogo());
		add_component_pinnable(new WurstplusFPS());
		add_component_pinnable(new WurstplusPing());
		add_component_pinnable(new WurstplusSurroundBlocks());
		add_component_pinnable(new WurstplusFriendList());
		add_component_pinnable(new WurstplusArmorDurabilityWarner());
		add_component_pinnable(new WurstplusPvpHud());
		add_component_pinnable(new WurstplusCompass());
		add_component_pinnable(new WurstplusEffectHud());
		add_component_pinnable(new WurstplusSpeedometer());
		add_component_pinnable(new WurstplusEntityList());
		add_component_pinnable(new WurstplusTPS());
		add_component_pinnable(new WurstplusPlayerList());
		add_component_pinnable(new WurstplusDirection());

		array_hud.sort(Comparator.comparing(WurstplusPinnable::get_title));
	}

	public void add_component_pinnable(WurstplusPinnable module) {
		array_hud.add(module);
	}

	public ArrayList<WurstplusPinnable> get_array_huds() {
		return array_hud;
	}

	public void render() {
		for (WurstplusPinnable pinnables : get_array_huds()) {
			if (pinnables.is_active()) {
				pinnables.render();
			}
		}
	}

	public WurstplusPinnable get_pinnable_with_tag(String tag) {
		WurstplusPinnable pinnable_requested = null;

		for (WurstplusPinnable pinnables : get_array_huds()) {
			if (pinnables.get_tag().equalsIgnoreCase(tag)) {
				pinnable_requested = pinnables;
			}
		}

		return pinnable_requested;
	}

}