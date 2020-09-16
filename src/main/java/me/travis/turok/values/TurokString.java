package me.travis.turok.values;

/**
* @author me
*
* Created by me.
* 08/04/20.
*
*/
public class TurokString {
	private java.lang.String name;
	private java.lang.String tag;
	private java.lang.String value;

	public TurokString(java.lang.String name, java.lang.String tag, java.lang.String string) {
		this.name  = name;
		this.tag   = tag;
		this.value = string;
	}

	public void set_value(java.lang.String string) {
		this.value = string;
	}

	public java.lang.String get_name() {
		return this.name;
	}

	public java.lang.String get_tag() {
		return this.tag;
	}

	public java.lang.String get_value() {
		return this.value;
	}

	public static java.lang.String to_string(java.lang.String value) {
		return value;
	}

	public static java.lang.String to_string(boolean value) {
		return Boolean.toString(value);
	}
	
	public static java.lang.String to_string(double value) {
		return Double.toString(value);
	}

	public static java.lang.String to_string(float value) {
		return Float.toString(value);
	}
	
	public static java.lang.String to_string(int value) {
		return Integer.toString(value);
	}
}