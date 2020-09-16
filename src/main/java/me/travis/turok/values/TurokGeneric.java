package me.travis.turok.values;

public class TurokGeneric<S> {
	S value;

	public TurokGeneric(S value) {
		this.value = value;
	}

	public void set_value(S value) {
		this.value = value;
	}

	public S get_value() {
		return this.value;
	}
}