package me.travis.wurstplus.wurstplustwo.hacks;

public enum WurstplusCategory {
	WURSTPLUS_CHAT ("Chat", "WurstplusChat", false),
	WURSTPLUS_COMBAT ("Combat", "WurstplusCombat", false),
	WURSTPLUS_MOVEMENT ("Movement", "WurstplusMovement", false),
	WURSTPLUS_RENDER ("Render", "WurstplusRender", false),
	WURSTPLUS_EXPLOIT ("Exploit", "WurstplusExploit", false),
	WURSTPLUS_MISC ("Misc", "WurstplusMisc", false),
	WURSTPLUS_GUI ("GUI", "WurstplusGUI", false),
	WURSTPLUS_BETA ("Beta", "WurstplusBeta", false),
	WURSTPLUS_HIDDEN ("Hidden", "WurstplusHidden", true);

	String name;
	String tag;
	boolean hidden;

	WurstplusCategory(String name, String tag, boolean hidden) {
		this.name   = name;
		this.tag    = tag;
		this.hidden = hidden;
	}

	public boolean is_hidden() {
		return this.hidden;
	}

	public String get_name() {
		return this.name;
	}

	public String get_tag() {
		return this.tag;
	}
}