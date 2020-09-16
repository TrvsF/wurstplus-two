package me.travis.wurstplus.wurstplustwo.guiscreen.settings;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

import java.util.List;

public class WurstplusSetting {
	private WurstplusHack master;

	private String name;
	private String tag;

	private boolean button;

	private List<String> combobox;
	private      String  current;

	private String label;

	private double slider;
	private double min;
	private double max;

	private String type;

	public WurstplusSetting(WurstplusHack master, String name, String tag, boolean value) {
		this.master = master;
		this.name   = name;
		this.tag    = tag;
		this.button = value;
		this.type   = "button";
	}

	public WurstplusSetting(WurstplusHack master, String name, String tag, List<String> values, String value) {
		this.master   = master;
		this.name     = name;
		this.tag      = tag;
		this.combobox = values;
		this.current  = value;
		this.type     = "combobox";
	}

	public WurstplusSetting(WurstplusHack master, String name, String tag, String value) {
		this.master = master;
		this.name   = name;
		this.tag    = tag;
		this.label  = value;
		this.type   = "label";
	}

	public WurstplusSetting(WurstplusHack master, String name, String tag, double value, double min, double max) {
		this.master = master;
		this.name   = name;
		this.tag    = tag;
		this.slider = value;
		this.min    = min;
		this.max    = max;
		this.type   = "doubleslider";
	}

	public WurstplusSetting(WurstplusHack master, String name, String tag, int value, int min, int max) {
		this.master = master;
		this.name   = name;
		this.tag    = tag;
		this.slider = value;
		this.min    = min;
		this.max    = max;
		this.type   = "integerslider";
	}

	public WurstplusHack get_master() {
		return this.master;
	}

	public String get_name() {
		return this.name;
	}

	public String get_tag() {
		return this.tag;
	}

	public void set_value(boolean value) {
		this.button = value;
	}

	public void set_current_value(String value) {
		this.current = value;
	}

	public void set_value(String value) {
		this.label = value;
	}

	public void set_value(double value) {
		if (value >= get_max(value)) {
			this.slider = get_max(value);
		} else if (value <= get_min(value)) {
			this.slider = get_min(value);
		} else {
			this.slider = value;
		}
	}

	public void set_value(int value) {
		if (value >= get_max(value)) {
			this.slider = get_max(value);
		} else if (value <= get_min(value)) {
			this.slider = get_min(value);
		} else {
			this.slider = value;
		}
	}

	public boolean is_info() {
		return this.name.equalsIgnoreCase("info");
	}

	public boolean in(String value) {
		return this.current.equalsIgnoreCase(value);
	}

	public boolean get_value(boolean type) {
		return this.button;
	}

	public List<String> get_values() {
		return this.combobox;
	}

	public String get_current_value() {
		return this.current;
	}

	public String get_value(String type) {
		return this.label;
	}

	public double get_value(double type) {
		return this.slider;
	}

	public int get_value(int type) {
		return ((int) Math.round(this.slider));
	}

	public double get_min(double type) {
		return this.min;
	}

	public double get_max(double type) {
		return this.max;
	}

	public int get_min(int type) {
		return ((int) this.min);
	}

	public int get_max(int type) {
		return ((int) this.max);
	}

	public String get_type() {
		return this.type;
	}
}