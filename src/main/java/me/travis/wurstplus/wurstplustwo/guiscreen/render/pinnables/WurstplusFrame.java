package me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;


public class WurstplusFrame {
	private ArrayList<WurstplusPinnableButton> pinnable_button;

	private String name;
	private String tag;

	private int x;
	private int y;

	private int move_x;
	private int move_y;

	private int width;
	private int height;

	private boolean move;
	private boolean can;

	private int border_size = 2;

	private WurstplusDraw font = new WurstplusDraw(1);

	public static int nc_r = 0;
	public static int nc_g = 0;
	public static int nc_b = 0;
	public static int nc_a = 0;

	public static int bg_r = 0;
	public static int bg_g = 0;
	public static int bg_b = 0;
	public static int bg_a = 0;

	public static int bd_r = 0;
	public static int bd_g = 0;
	public static int bd_b = 0;
	public static int bd_a = 0;

	public static int bdw_r = 0;
	public static int bdw_g = 0;
	public static int bdw_b = 0;
	public static int bdw_a = 255;

	public final Minecraft mc = Minecraft.getMinecraft();

	public WurstplusFrame(String name, String tag, int initial_x, int initial_y) {
		this.pinnable_button = new ArrayList<>();

		this.name = name;
		this.tag  = tag;

		this.x = initial_x;
		this.y = initial_y;

		this.move_x = 0;
		this.move_y = 0;

		this.width  = 100;
		this.height = 25;

		this.can = true;

		int size  = Wurstplus.get_hud_manager().get_array_huds().size();
		int count = 0;

		for (WurstplusPinnable pinnables : Wurstplus.get_hud_manager().get_array_huds()) {
			WurstplusPinnableButton pinnables_buttons = new WurstplusPinnableButton(this, pinnables.get_title(), pinnables.get_tag());

			pinnables_buttons.set_y(this.height);

			this.pinnable_button.add(pinnables_buttons);

			count++;

			if (count >= size) {
				this.height += 5;
			} else {
				this.height += 10 + 2;
			}
		}
	}

	public void set_move(boolean value) {
		this.move = value;
	}

	public void does_can(boolean value) {
		this.can = value;
	}

	public void set_x(int x) {
		this.x = x;
	}

	public void set_y(int y) {
		this.y = y;
	}

	public void set_move_x(int x) {
		this.move_x = x;
	}

	public void set_move_y(int y) {
		this.move_y = y;
	}

	public void set_width(int width) {
		this.width = width;
	}

	public void set_height(int height) {
		this.height = height;
	}

	public String get_name() {
		return this.name;
	}

	public String get_tag() {
		return this.tag;
	}

	public boolean is_moving() {
		return this.move;
	}

	public boolean can() {
		return this.can;
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

	public boolean motion(int mx, int my) {
		return mx >= get_x() && my >= get_y() && mx <= get_x() + get_width() && my <= get_y() + get_height();
	}

	public void crush(int mx, int my) {
		// Get current screen real length.
		int screen_x = (mc.displayWidth / 2);
		int screen_y = (mc.displayHeight / 2);

		set_x(mx - this.move_x);
		set_y(my - this.move_y);

		if (this.x + this.width >= screen_x) {
			this.x = screen_x - this.width - 1;
		}

		if (this.x <= 0) {
			this.x = 1;
		}

		if (this.y + this.height >= screen_y) {
			this.y = screen_y - this.height - 1;
		}

		if (this.y <= 0) {
			this.y = 1;
		}

		if (this.x % 2 != 0) {
			this.x += this.x % 2;
		}

		if (this.y % 2 != 0) {
			this.y += this.y % 2;
		}
	}

	public void mouse(int mx, int my, int mouse) {
		for (WurstplusPinnableButton pinnables_buttons : this.pinnable_button) {
			pinnables_buttons.click(mx, my, mouse);
		}
	}

	public void release(int mx, int my, int mouse) {
		for (WurstplusPinnableButton pinnables_buttons : this.pinnable_button) {
			pinnables_buttons.release(mx, my, mouse);
		}

		set_move(false);
	}

	public void render(int mx, int my, int separate) {
		float[] tick_color = {
			(System.currentTimeMillis() % (360 * 32)) / (360f * 32)
		};

		int color_b = Color.HSBtoRGB(tick_color[0], 1, 1);
		int color   = (color_b);

		if ((color_b) <= 50) {
			color = 50;
		} else color = Math.min((color_b), 120);

		bd_a  = color;
		bdw_a = 255;

		WurstplusDraw.draw_rect(this.x, this.y, this.x + this.width, this.y + this.height, this.bg_r, this.bg_g, this.bg_b, this.bg_a);
		WurstplusDraw.draw_rect(this.x - 1, this.y, this.width + 1, this.height, this.bd_r, this.bd_g, this.bd_b, this.bd_a, this.border_size, "left-right");

		WurstplusDraw.draw_string(this.name, this.x + 4, this.y + 4, this.nc_r, this.nc_g, this.nc_b, this.nc_a);

		if (is_moving()) {
			crush(mx, my);
		}
	
		for (WurstplusPinnableButton pinnables_buttons : this.pinnable_button) {
			pinnables_buttons.set_x(this.x + separate);

			pinnables_buttons.render(mx, my, separate);

			if (pinnables_buttons.motion(mx, my)) {
				WurstplusDraw.draw_rect(get_x() - 1, pinnables_buttons.get_save_y(), get_width() + 1, pinnables_buttons.get_height(), this.bdw_r, this.bdw_g, this.bdw_b, this.bdw_a, this.border_size, "right-left");
			}
		}
	}
}