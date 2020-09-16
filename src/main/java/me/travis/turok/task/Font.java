package me.travis.turok.task;

/**
* @author me
*
* Created by me.
* 08/05/20.
*
*/
public class Font {
	public static String smoth(String base) {
		String new_base = base;

		new_base = new_base.replace("a", "\u1d00");
		new_base = new_base.replace("b", "\u0299");
		new_base = new_base.replace("c", "\u1d04");
		new_base = new_base.replace("d", "\u1d05");
		new_base = new_base.replace("e", "\u1d07");
		new_base = new_base.replace("f", "\u0493");
		new_base = new_base.replace("g", "\u0262");
		new_base = new_base.replace("h", "\u029c");
		new_base = new_base.replace("i", "\u026a");
		new_base = new_base.replace("j", "\u1d0a");
		new_base = new_base.replace("k", "\u1d0b");
		new_base = new_base.replace("l", "\u029f");
		new_base = new_base.replace("m", "\u1d0d");
		new_base = new_base.replace("n", "\u0274");
		new_base = new_base.replace("o", "\u1d0f");
		new_base = new_base.replace("p", "\u1d18");
		new_base = new_base.replace("q", "\u01eb");
		new_base = new_base.replace("r", "\u0280");
		new_base = new_base.replace("s", "\u0455");
		new_base = new_base.replace("t", "\u1d1b");
		new_base = new_base.replace("u", "\u1d1c");
		new_base = new_base.replace("v", "\u1d20");
		new_base = new_base.replace("w", "\u1d21");
		new_base = new_base.replace("x", "\u0445");
		new_base = new_base.replace("y", "\u028f");
		new_base = new_base.replace("z", "\u1d22");

		return new_base;
	}
}