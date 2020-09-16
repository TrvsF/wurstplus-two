package me.travis.wurstplus.wurstplustwo.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;


public class WurstplusVisualRange extends WurstplusHack {

	private List<String> people;

	public WurstplusVisualRange() {
		super(WurstplusCategory.WURSTPLUS_CHAT);

		this.name        = "Visual Range";
		this.tag         = "VisualRange";
		this.description = "bc using ur eyes is overrated";
	}

	@Override
	public void enable() {
		people = new ArrayList<>();
	}

	@Override
	public void update() {
		if (mc.world == null | mc.player == null) return;

		List<String> peoplenew = new ArrayList<>();
		List<EntityPlayer> playerEntities = mc.world.playerEntities;

		for (Entity e : playerEntities) {
			if (e.getName().equals(mc.player.getName())) continue;
			peoplenew.add(e.getName());
		}

		if (peoplenew.size() > 0) {
			for (String name : peoplenew) {
				if (!people.contains(name)) {
					if (WurstplusFriendUtil.isFriend(name)) {
						WurstplusMessageUtil.send_client_message("I see an epic dude called " + ChatFormatting.RESET + ChatFormatting.GREEN + name + ChatFormatting.RESET + " :D");
					} else {
						WurstplusMessageUtil.send_client_message("I see a dude called " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + ". Yuk");
					}
					people.add(name);
				}
			}
		}

	}
}
