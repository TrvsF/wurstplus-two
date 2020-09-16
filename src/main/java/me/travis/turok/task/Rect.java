package me.travis.turok.task;

/**
* @author me
*
* Created by me.
* 25/04/20.
*
*/
public class Rect {
	private String tag;

	private int x;
	private int y;

	private int width;
	private int height;

	public Rect(String tag, int width, int height) {
		this.tag    = tag;
		this.width  = width;
		this.height = height;
	}

	public void transform(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void transform(int x, int y, int width, int height) {
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
	}

	public boolean event_mouse(int mx, int my) {
		if (mx >= get_x() && my >= get_y() && mx <= get_screen_width() && my <= get_screen_height()) {
			return true;
		}

		return false;
	}

	public String get_tag() {
		return this.tag;
	}

	public int get_x() {
		return this.x;
	}

	public int get_y() {
		return this.y;
	}

	public int get_width() {
		return this.width;
	}

	public int get_height() {
		return this.height;
	}

	public int get_screen_width() {
		return ((int) this.x + this.width);
	}

	public int get_screen_height() {
		return ((int) this.y + this.height);
	}
}