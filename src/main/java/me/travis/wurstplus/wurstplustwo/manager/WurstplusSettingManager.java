package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

import java.util.ArrayList;


public class WurstplusSettingManager {

	public ArrayList<WurstplusSetting> array_setting;

	public WurstplusSettingManager() {
		this.array_setting = new ArrayList<>();
	}

	public void register(WurstplusSetting setting) {
		this.array_setting.add(setting);
	}

	public ArrayList<WurstplusSetting> get_array_settings() {
		return this.array_setting;
	}

	public WurstplusSetting get_setting_with_tag(WurstplusHack module, String tag) {
		WurstplusSetting setting_requested = null;

		for (WurstplusSetting settings : get_array_settings()) {
			if (settings.get_master().equals(module) && settings.get_tag().equalsIgnoreCase(tag)) {
				setting_requested = settings;
			}
		}

		return setting_requested;
	}

	public WurstplusSetting get_setting_with_tag(String tag, String tag_) {
		WurstplusSetting setting_requested = null;

		for (WurstplusSetting settings : get_array_settings()) {
			if (settings.get_master().get_tag().equalsIgnoreCase(tag) && settings.get_tag().equalsIgnoreCase(tag_)) {
				setting_requested = settings;
				break;
			}
		}

		return setting_requested;
	}

	public ArrayList<WurstplusSetting> get_settings_with_hack(WurstplusHack module) {
		ArrayList<WurstplusSetting> setting_requesteds = new ArrayList<>();

		for (WurstplusSetting settings : get_array_settings()) {
			if (settings.get_master().equals(module)) {
				setting_requesteds.add(settings);
			}
		}

		return setting_requesteds;
	}
}