package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.google.common.collect.Lists;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class WurstplusArrayList extends WurstplusPinnable {
	public WurstplusArrayList() {
		super("Array List", "ArrayList", 1, 0, 0);
	}

	boolean flag = true;

	private int scaled_width;
	private int scaled_height;
	private int scale_factor;

	@Override
	public void render() {
		updateResolution();
		int position_update_y = 2;

		int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		List<WurstplusHack> pretty_modules = Wurstplus.get_hack_manager().get_array_active_hacks().stream()
			.sorted(Comparator.comparing(modules -> get(modules.array_detail() == null ? modules.get_tag() : modules.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules.array_detail() + Wurstplus.g + "]" + Wurstplus.r, "width")))
			.collect(Collectors.toList());

		int count = 0;

		if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R") || Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L") ) {
			pretty_modules = Lists.reverse(pretty_modules);
		}

		for (WurstplusHack modules : pretty_modules) {

			flag = true;

			if (modules.get_category().get_tag().equals("WurstplusGUI")) {
				continue;
			}

			for (String s : WurstplusDrawnUtil.hidden_tags) {
				if (modules.get_tag().equalsIgnoreCase(s)) {
					flag = false;
					break;
				}
				if (!flag) break;
			}
			
			if (flag) {
				String module_name = (
					modules.array_detail() == null ? modules.get_tag() :
					modules.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules.array_detail() + Wurstplus.g + "]" + Wurstplus.r
				);

				if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Free")) {
					create_line(module_name, this.docking(2, module_name), position_update_y, nl_r, nl_g, nl_b, nl_a);

					position_update_y += get(module_name, "height") + 2;

					if (get(module_name, "width") > this.get_width()) {
						this.set_width(get(module_name, "width") + 2);
					}

					this.set_height(position_update_y);
				} else {
					if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R")) {
						mc.fontRenderer.drawStringWithShadow(module_name, scaled_width - 2 - mc.fontRenderer.getStringWidth(module_name), 3 + count * 10, new WurstplusDraw.TravisColor(nl_r,nl_g,nl_b,nl_a).hex());
						count++;
					}
					if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
						mc.fontRenderer.drawStringWithShadow(module_name, 2, 3 + count * 10, new WurstplusDraw.TravisColor(nl_r,nl_g,nl_b,nl_a).hex());
						count++;
					}
					if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom R")) {
						mc.fontRenderer.drawStringWithShadow(module_name, scaled_width - 2 - mc.fontRenderer.getStringWidth(module_name), scaled_height - (count * 10), new WurstplusDraw.TravisColor(nl_r,nl_g,nl_b,nl_a).hex());
						count++;
					}
					if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom L")) {
						mc.fontRenderer.drawStringWithShadow(module_name, 2, scaled_height - (count * 10), new WurstplusDraw.TravisColor(nl_r,nl_g,nl_b,nl_a).hex());
						count++;
					}
				}


			}			
		}
	}

	public void updateResolution() {
		this.scaled_width = mc.displayWidth;
		this.scaled_height = mc.displayHeight;
		this.scale_factor = 1;
		final boolean flag = mc.isUnicode();
		int i = mc.gameSettings.guiScale;
		if (i == 0) {
			i = 1000;
		}
		while (this.scale_factor < i && this.scaled_width / (this.scale_factor + 1) >= 320 && this.scaled_height / (this.scale_factor + 1) >= 240) {
			++this.scale_factor;
		}
		if (flag && this.scale_factor % 2 != 0 && this.scale_factor != 1) {
			--this.scale_factor;
		}
		final double scaledWidthD = this.scaled_width / (double)this.scale_factor;
		final double scaledHeightD = this.scaled_height / (double)this.scale_factor;
		this.scaled_width = MathHelper.ceil(scaledWidthD);
		this.scaled_height = MathHelper.ceil(scaledHeightD);
	}
}