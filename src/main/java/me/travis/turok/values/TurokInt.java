package me.travis.turok.values;

/**
* @author me
*
* Created by me.
* 08/04/20.
*
*/
public class TurokInt {
	private TurokString name;
	private TurokString tag;

	private int value;
	private int max;
	private int min;

	public TurokInt(TurokString name, TurokString tag, int _int, int min, int max) {
		this.name  = name;
		this.tag   = tag;
		this.value = _int;
		this.max   = max;
		this.min   = min;
	}

	public void set_value(int _int) {
		this.value = _int;
	}

	public void set_slider_value(int _int) {
		if (_int >= this.max) {
			this.value = this.max;
		} else if (_int <= this.min) {
			this.value = this.min;
		} else {
			this.value = _int;
		}
	}

	public TurokString get_name() {
		return this.name;
	}

	public TurokString get_tag() {
		return this.tag;
	}

	public int get_value() {
		return this.value;
	}
}