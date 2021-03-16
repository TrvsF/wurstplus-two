package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

// Travis.


public class WurstplusHoleESP extends WurstplusHack {

	public WurstplusHoleESP() {
		super(WurstplusCategory.WURSTPLUS_RENDER);

		this.name        = "Hole ESP";
		this.tag         = "HoleESP";
		this.description = "lets you know where holes are";
	}

	WurstplusSetting mode 				= create("Mode", "HoleESPMode", "Pretty", combobox("Pretty", "Solid", "Outline"));
	WurstplusSetting off_set 			= create("Height", "HoleESPOffSetSide", 0.2, 0.0, 1.0);
	WurstplusSetting range   			= create("Range", "HoleESPRange", 6, 1, 12);
	WurstplusSetting hide_own         	= create("Hide Own", "HoleESPHideOwn", true);

	WurstplusSetting bedrock_view 		= create("info", "HoleESPbedrock", "Bedrock");
	WurstplusSetting bedrock_enable 	= create("Bedrock Holes", "HoleESPBedrockHoles", true);
	// WurstplusSetting rgb_b 				= create("RGB Effect", "HoleColorRGBEffect", true);
	WurstplusSetting rb 				= create("R", "HoleESPRb", 0, 0, 255);
	WurstplusSetting gb 				= create("G", "HoleESPGb", 255, 0, 255);
	WurstplusSetting bb 				= create("B", "HoleESPBb", 0, 0, 255);
	WurstplusSetting ab				    = create("A", "HoleESPAb", 50, 0, 255);

	WurstplusSetting obsidian_view 		= create("info", "HoleESPObsidian", "Obsidian");
	WurstplusSetting obsidian_enable	= create("Obsidian Holes", "HoleESPObsidianHoles", true);
	// WurstplusSetting rgb_o 				= create("RGB Effect", "HoleColorRGBEffect", true);
	WurstplusSetting ro 				= create("R", "HoleESPRo", 255, 0, 255);
	WurstplusSetting go				    = create("G", "HoleESPGo", 0, 0, 255);
	WurstplusSetting bo 				= create("B", "HoleESPBo", 0, 0, 255);
	WurstplusSetting ao 				= create("A", "HoleESPAo", 50, 0, 255);

	WurstplusSetting dual_view 		= create("info", "HoleESPDual", "Double");
	WurstplusSetting dual_enable	= create("Dual Holes", "HoleESPTwoHoles", false);

	WurstplusSetting line_a = create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);

	ArrayList<WurstplusPair<BlockPos, Boolean>> holes = new ArrayList<>();

	boolean outline = false;
	boolean solid   = false;
	boolean docking = false;

	int color_r_o;
	int color_g_o;
	int color_b_o;

	int color_r_b;
	int color_g_b;
	int color_b_b;

	int color_r;
	int color_g;
	int color_b;
	int color_a;

	int safe_sides;

	@Override
	public void update() {
		// float[] tick_color = {
		// 	(System.currentTimeMillis() % (360 * 32)) / (360f * 32)
		// };
	
		// int color_rgb_o = Color.HSBtoRGB(tick_color[0], 1, 1);
		// int color_rgb_b = Color.HSBtoRGB(tick_color[0], 1, 1);
	
		// if (rgb_o.get_value(true)) {
		// 	color_r_o = ((color_rgb_o >> 16) & 0xFF);
		// 	color_g_o = ((color_rgb_o >> 8) & 0xFF);
		// 	color_b_o = (color_rgb_o & 0xFF);
	
		// 	r_o.set_value(color_r_o);
		// 	g_o.set_value(color_g_o);
		// 	b_o.set_value(color_b_o);
		// } else {
		// 	color_r_o = r_o.get_value(1);
		// 	color_g_o = g_o.get_value(2);
		// 	color_b_o = b_o.get_value(3);
		// }

		// if (rgb_b.get_value(true)) {
		// 	color_r_b = ((color_rgb_b >> 16) & 0xFF);
		// 	color_g_b = ((color_rgb_b >> 8) & 0xFF);
		// 	color_b_b = (color_rgb_b & 0xFF);
	
		// 	r_b.set_value(color_r_b);
		// 	g_b.set_value(color_g_b);
		// 	b_b.set_value(color_b_b);
		// } else {
		// 	color_r_b = r_b.get_value(1);
		// 	color_g_b = g_b.get_value(2);
		// 	color_b_b = b_b.get_value(3);
		// }

		color_r_b = rb.get_value(1);
		color_g_b = gb.get_value(1);
		color_b_b = bb.get_value(1);

		color_r_o = ro.get_value(1);
		color_g_o = go.get_value(1);
		color_b_o = bo.get_value(1);

		holes.clear();

		if (mc.player != null || mc.world != null) {
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

			int colapso_range = (int) Math.ceil(range.get_value(1));

			List<BlockPos> spheres = sphere(player_as_blockpos(), colapso_range, colapso_range);

			for (BlockPos pos : spheres) {
				if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				boolean possible = true;

				safe_sides = 0;

				int air_orient =-1;
				int counter    = 0;

				for (BlockPos seems_blocks : new BlockPos[]{
						new BlockPos( 0,-1, 0),
						new BlockPos( 0, 0,-1),
						new BlockPos( 1, 0, 0),
						new BlockPos( 0, 0, 1),
						new BlockPos(-1, 0, 0)
				}) {
					Block block = mc.world.getBlockState(pos.add(seems_blocks)).getBlock();

					if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
						possible = false;

						if (counter == 0) break;

						if (air_orient != -1) {
							air_orient = -1;
							break;
						}

						if (block.equals(Blocks.AIR)) {
								air_orient = counter;
						} else {
							break;
						}
					}

					if (block == Blocks.BEDROCK) {
						safe_sides++;

					}
					counter++;
				}

				if (possible) {
					if (safe_sides == 5) {
						if (!this.bedrock_enable.get_value(true)) continue;
						holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
					} else {
						if (!this.obsidian_enable.get_value(true)) continue;
						holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
					}
					continue;
				}

				if (!this.dual_enable.get_value(true) || air_orient < 0) continue;
				BlockPos second_pos = pos.add(orientConv(air_orient));
				if (checkDual(second_pos, air_orient)) {

					boolean low_ceiling_hole = mc.world.getBlockState(second_pos.add(0,1,0)).getBlock().equals(Blocks.AIR) &&

							!mc.world.getBlockState(second_pos.add(0,2,0)).getBlock().equals(Blocks.AIR);
							// to avoid rendering the same hole twice

					if(safe_sides == 8) {
						holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
						if (low_ceiling_hole) holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, true));
					}
					else {
						holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
						if (low_ceiling_hole) holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, false));
					}

				}

			}
		}
	}

	private static BlockPos orientConv(int orient_count) {
		BlockPos converted = null;

		switch(orient_count) {
			case 0:
			//return EnumFacing.DOWN.getDirectionVec();
				converted = new BlockPos( 0, -1,  0);
				break;
			case 1:
				//return EnumFacing.NORTH.getDirectionVec();
				converted = new BlockPos( 0,  0, -1);
				break;
			case 2:
				//return EnumFacing.EAST.getDirectionVec();
				converted = new BlockPos( 1,  0,  0);
				break;
			case 3:
				//return EnumFacing.SOUTH.getDirectionVec();
				converted = new BlockPos( 0,  0,  1);
				break;
			case 4:
				//return EnumFacing.WEST.getDirectionVec();
				converted = new BlockPos(-1,  0,  0);
				break;
			case 5:
				converted = new BlockPos(0,  1,  0);
				break;
		}
		return converted;
	}

	private static int oppositeIntOrient(int orient_count) {

		int opposite = 0;

		switch(orient_count)
		{
			case 0:
				opposite = 5;
				break;
			case 1:
				opposite = 3;
				break;
			case 2:
				opposite = 4;
				break;
			case 3:
				opposite = 1;
				break;
			case 4:
				opposite = 2;
				break;
		}
		return opposite;
	}

	private boolean checkDual(BlockPos second_block, int counter) {
		int i = -1;

		/*
			lets check down from second block to not have esp of a dual hole of one space
			missing a bottom block
		*/
		for (BlockPos seems_blocks : new BlockPos[] {
			new BlockPos( 0,  -1, 0), //Down
			new BlockPos( 0,  0, -1), //N
			new BlockPos( 1,  0,  0), //E
			new BlockPos( 0,  0,  1), //S
			new BlockPos(-1,  0,  0)  //W

		}) {
			i++;
			//skips opposite direction check, since its air



			if(counter == oppositeIntOrient(i)) {
				continue;
			}



			Block block = mc.world.getBlockState(second_block.add(seems_blocks)).getBlock();
			if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
				return false;
			}

			if (block == Blocks.BEDROCK) {
				safe_sides++;
			}

		}
		return true;
	}

	@Override
	public void render(WurstplusEventRender event) {
		float off_set_h = 0;
 
		if (!holes.isEmpty()) {
			off_set_h = (float) off_set.get_value(1.0);

			for (WurstplusPair<BlockPos, Boolean> hole : holes) {
				if (hole.getValue()) {
					color_r = color_r_b;
					color_g = color_g_b;
					color_b = color_b_b;
					color_a = ab.get_value(1);
				} else if (!hole.getValue()) {
					color_r = color_r_o;
					color_g = color_g_o;
					color_b = color_b_o;
					color_a = ao.get_value(1);
				} else continue;

				if (hide_own.get_value(true) && hole.getKey().equals((Object)new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ))) {
					continue;
				}

				if (solid) {
					RenderHelp.prepare("quads");
					RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
						hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(),
						1, off_set_h, 1,
						color_r, color_g, color_b, color_a,
						"all"
					);

					RenderHelp.release();
				}

				if (outline) {
					RenderHelp.prepare("lines");
					RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(),
						hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(),
						1, off_set_h, 1,
						color_r, color_g, color_b, line_a.get_value(1),
						"all"
					);

					RenderHelp.release();
				}
			}
		}
	}

    public List<BlockPos> sphere(BlockPos pos, float r, int h) {
    	boolean hollow = false;
    	boolean sphere = true;

    	int plus_y = 0;

		List<BlockPos> sphere_block = new ArrayList<BlockPos>();

		int cx = pos.getX();
		int cy = pos.getY();
		int cz = pos.getZ();

		for (int x = cx - (int)r; x <= cx + r; ++x) {
			for (int z = cz - (int)r; z <= cz + r; ++z) {
				for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
					if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
						BlockPos spheres = new BlockPos(x, y + plus_y, z);

						sphere_block.add(spheres);
					}
				}
			}
		}

		return sphere_block;
	}

	public BlockPos player_as_blockpos() {
		return new BlockPos(Math.floor((double) mc.player.posX), Math.floor((double) mc.player.posY), Math.floor((double) mc.player.posZ));
	}
}