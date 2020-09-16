package me.travis.wurstplus.wurstplustwo.guiscreen;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusFrame;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnableButton;
import net.minecraft.client.gui.GuiScreen;


public class WurstplusHUD extends GuiScreen {
	private final WurstplusFrame frame;

	private int frame_height;

	public boolean on_gui;
	public boolean back;

	public WurstplusHUD() {
		this.frame  = new WurstplusFrame("Wurst+2 HUD", "WurstplusHUD", 40, 40);
		this.back   = false;
		this.on_gui = false;
	}

	public WurstplusFrame get_frame_hud() {
		return this.frame;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		this.on_gui = true;

		WurstplusFrame.nc_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameR").get_value(1);
		WurstplusFrame.nc_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameG").get_value(1);
		WurstplusFrame.nc_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameB").get_value(1);
		
		WurstplusFrame.bg_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameR").get_value(1);
		WurstplusFrame.bg_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameG").get_value(1);
		WurstplusFrame.bg_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameB").get_value(1);
		WurstplusFrame.bg_a = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameA").get_value(1);
		
		WurstplusFrame.bd_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameR").get_value(1);
		WurstplusFrame.bd_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameG").get_value(1);
		WurstplusFrame.bd_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameB").get_value(1);
		WurstplusFrame.bd_a = 0;
		
		WurstplusFrame.bdw_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").get_value(1);
		WurstplusFrame.bdw_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").get_value(1);
		WurstplusFrame.bdw_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").get_value(1);
		WurstplusFrame.bdw_a = 255;

		WurstplusPinnableButton.nc_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetR").get_value(1);
		WurstplusPinnableButton.nc_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetG").get_value(1);
		WurstplusPinnableButton.nc_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetB").get_value(1);
	
		WurstplusPinnableButton.bg_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetR").get_value(1);
		WurstplusPinnableButton.bg_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetG").get_value(1);
		WurstplusPinnableButton.bg_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetB").get_value(1);
		WurstplusPinnableButton.bg_a = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetA").get_value(1);
	
		WurstplusPinnableButton.bd_r = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").get_value(1);
		WurstplusPinnableButton.bd_g = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").get_value(1);
		WurstplusPinnableButton.bd_b = Wurstplus.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").get_value(1);
	}

	@Override
	public void onGuiClosed() {
		if (this.back) {
			Wurstplus.get_hack_manager().get_module_with_tag("GUI").set_active(true);
			Wurstplus.get_hack_manager().get_module_with_tag("HUD").set_active(false);
		} else {
			Wurstplus.get_hack_manager().get_module_with_tag("HUD").set_active(false);
			Wurstplus.get_hack_manager().get_module_with_tag("GUI").set_active(false);
		}

		this.on_gui = false;

		Wurstplus.get_config_manager().save_settings();
	}

	@Override
	protected void mouseClicked(int mx, int my, int mouse) {
		this.frame.mouse(mx, my, mouse);

		if (mouse == 0) {
			if (this.frame.motion(mx, my) && this.frame.can()) {
				this.frame.set_move(true);

				this.frame.set_move_x(mx - this.frame.get_x());
				this.frame.set_move_y(my - this.frame.get_y());
			}
		}
	}

	@Override
	protected void mouseReleased(int mx, int my, int state) {
		this.frame.release(mx, my, state);

		this.frame.set_move(false);
	}

	@Override
	public void drawScreen(int mx, int my, float tick) {
		this.drawDefaultBackground();

		this.frame.render(mx, my, 2);
	}
}