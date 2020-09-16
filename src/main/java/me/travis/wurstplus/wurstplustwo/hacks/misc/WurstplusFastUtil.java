package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;


public class WurstplusFastUtil extends WurstplusHack {

	public WurstplusFastUtil() {
		super(WurstplusCategory.WURSTPLUS_MISC);

		this.name        = "Fast Util"; 
		this.tag         = "FastUtil";
		this.description = "use things faster";
	}

	WurstplusSetting fast_place = create("Place","WurstplusFastPlace", false);
	WurstplusSetting fast_break = create("Break","WurstplusFastBreak",false);
	WurstplusSetting crystal = create("Crystal", "WurstplusFastCrystal",false);
	WurstplusSetting exp = create("EXP","WurstplusFastExp",true);

	@Override
	public void update() {
		Item main = mc.player.getHeldItemMainhand().getItem();
		Item off  = mc.player.getHeldItemOffhand().getItem();

		boolean main_exp = main instanceof ItemExpBottle;
		boolean off_exp  = off instanceof ItemExpBottle;
		boolean main_cry = main instanceof ItemEndCrystal;
		boolean off_cry  = off instanceof ItemEndCrystal;

		if (main_exp | off_exp && exp.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (main_cry | off_cry && crystal.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (!(main_exp | off_exp | main_cry | off_cry) && fast_place.get_value(true)) {
			mc.rightClickDelayTimer = 0;
		}

		if (fast_break.get_value(true)) {
			mc.playerController.blockHitDelay = 0;
		}
	}
}