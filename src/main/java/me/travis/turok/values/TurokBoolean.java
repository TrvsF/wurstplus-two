package me.travis.turok.values;

// Values.

/**
* @author me
*
* Created by me.
* 08/04/20.
*
*/
public class TurokBoolean {
	private TurokString name;
	private TurokString tag;

	private TurokGeneric<Boolean> value;

	public TurokBoolean(TurokString name, TurokString tag, boolean _bool) {
		this.name  = name;
		this.tag   = tag;
		this.value = new TurokGeneric(_bool);
	}

	public void set_value(boolean _bool) {
		this.value.set_value(_bool);
	}

	public TurokString get_name() {
		return this.name;
	}

	public TurokString get_tag() {
		return this.tag;
	}

	public boolean get_value() {
		return this.value.get_value();
	}
}