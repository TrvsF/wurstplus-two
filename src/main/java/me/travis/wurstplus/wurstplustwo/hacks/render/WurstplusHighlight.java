package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

// Travis.


public class WurstplusHighlight extends WurstplusHack {

	public WurstplusHighlight() {
		super(WurstplusCategory.WURSTPLUS_RENDER);

		this.name        = "Block Highlight";
		this.tag         = "BlockHighlight";
		this.description = "see what block ur targeting";
	}

	WurstplusSetting mode = create("Mode", "HighlightMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
	
	WurstplusSetting rgb = create("RGB Effect", "HighlightRGBEffect", true);
	
	WurstplusSetting r = create("R", "HighlightR", 255, 0, 255);
	WurstplusSetting g = create("G", "HighlightG", 255, 0, 255);
	WurstplusSetting b = create("B", "HighlightB", 255, 0, 255);
	WurstplusSetting a = create("A", "HighlightA", 100, 0, 255);
	
	WurstplusSetting l_a = create("Outline A", "HighlightLineA", 255, 0, 255);

	int color_r;
	int color_g;
	int color_b;

	boolean outline = false;
	boolean solid   = false;

	@Override
	public void disable() {
		outline = false;
		solid   = false;
	}

	@Override
	public void render(WurstplusEventRender event) {
		if (mc.player != null && mc.world != null) {
			float[] tick_color = {
				(System.currentTimeMillis() % (360 * 32)) / (360f * 32)
			};
	
			int color_rgb = Color.HSBtoRGB(tick_color[0], 1, 1);
	
			if (rgb.get_value(true)) {
				color_r = ((color_rgb >> 16) & 0xFF);
				color_g = ((color_rgb >> 8) & 0xFF);
				color_b = (color_rgb & 0xFF);
	
				r.set_value(color_r);
				g.set_value(color_g);
				b.set_value(color_b);
			} else {
				color_r = r.get_value(1);
				color_g = g.get_value(2);
				color_b = b.get_value(3);
			}
	
			if (mode.in("Pretty")) {
				outline = true;
				solid   = true;
			}
	
			if (mode.in("Solid")) {
				outline = false;
				solid   = true;
			}
	
			if (mode.in("Outline")) {
				outline = true;
				solid   = false;
			}
	
			RayTraceResult result = mc.objectMouseOver;
	
			if (result != null) {
				if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos block_pos = result.getBlockPos();
	
					// Solid.
					if (solid) {
						RenderHelp.prepare("quads");
						RenderHelp.draw_cube(block_pos, color_r, color_g, color_b, a.get_value(1), "all");
						RenderHelp.release();
					}
	
					// Outline.
					if (outline) {
						RenderHelp.prepare("lines");
						RenderHelp.draw_cube_line(block_pos, color_r, color_g, color_b, l_a.get_value(1), "all");
						RenderHelp.release();
					}
				}
			}
		}
	}
}