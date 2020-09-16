package me.travis.wurstplus.wurstplustwo.guiscreen.render.components.widgets;


import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusAbstractWidget;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusModuleButton;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;


public class WurstplusButton extends WurstplusAbstractWidget {
	private WurstplusFrame        frame;
	private WurstplusModuleButton master;
	private WurstplusSetting      setting;

	private String button_name;

	private int x;
	private int y;

	private int width;
	private int height;

	private int save_y;

	private boolean can;

	private WurstplusDraw font = new WurstplusDraw(1);

	private int border_size = 0;

	public WurstplusButton(WurstplusFrame frame, WurstplusModuleButton master, String tag, int update_postion) {
		this.frame   = frame;
		this.master  = master;
		this.setting = Wurstplus.get_setting_manager().get_setting_with_tag(master.get_module(), tag);

		this.x = master.get_x();
		this.y = update_postion;

		this.save_y = this.y;

		this.width  = master.get_width();
		this.height = font.get_string_height();

		this.button_name = this.setting.get_name();

		this.can = true;
	}

	public WurstplusSetting get_setting() {
		return this.setting;
	}

	@Override
	public void does_can(boolean value) {
		this.can = value;
	}

	@Override
	public void set_x(int x) {
		this.x = x;
	}

	@Override
	public void set_y(int y) {
		this.y = y;
	}

	@Override
	public void set_width(int width) {
		this.width = width;
	}

	@Override
	public void set_height(int height) {
		this.height = height;
	}

	@Override
	public int get_x() {
		return this.x;
	}

	@Override
	public int get_y() {
		return this.y;
	}

	@Override
	public int get_width() {
		return this.width;
	}

	@Override
	public int get_height() {
		return this.height;
	}

	public int get_save_y() {
		return this.save_y;
	}

	@Override
	public boolean motion_pass(int mx, int my) {
		return motion(mx, my);
	}

	public boolean motion(int mx, int my) {
		if (mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height()) {
			return true;
		}

		return false;
	}

	public boolean can() {
		return this.can;
	}

	@Override
	public void mouse(int mx, int my, int mouse) {
		if (mouse == 0) {
			if (motion(mx, my) && this.master.is_open() && can()) {
				this.frame.does_can(false);

				this.setting.set_value(!(this.setting.get_value(true)));
			}
		}
	}

	@Override
	public void render(int master_y, int separe, int absolute_x, int absolute_y) {
		set_width(this.master.get_width() - separe);

		this.save_y = this.y + master_y;

		int ns_r = Wurstplus.click_gui.theme_widget_name_r;
		int ns_g = Wurstplus.click_gui.theme_widget_name_g;
		int ns_b = Wurstplus.click_gui.theme_widget_name_b;
		int ns_a = Wurstplus.click_gui.theme_widget_name_a;

		int bg_r = Wurstplus.click_gui.theme_widget_background_r;
		int bg_g = Wurstplus.click_gui.theme_widget_background_g;
		int bg_b = Wurstplus.click_gui.theme_widget_background_b;
		int bg_a = Wurstplus.click_gui.theme_widget_background_a;

		int bd_r = Wurstplus.click_gui.theme_widget_border_r;
		int bd_g = Wurstplus.click_gui.theme_widget_border_g;
		int bd_b = Wurstplus.click_gui.theme_widget_border_b;

		if (this.setting.get_value(true)) { // filling in the button box if button is enabled
			WurstplusDraw.draw_rect(get_x(), this.save_y, get_x() + this.width, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
		}
		WurstplusDraw.draw_string(this.button_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
	}
}