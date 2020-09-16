package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

import java.util.HashMap;
import java.util.Map;

public class WurstplusStopEXP extends WurstplusHack {
    
    public WurstplusStopEXP() {
        super(WurstplusCategory.WURSTPLUS_MISC);

		this.name        = "Stop EXP";
		this.tag         = "StopEXP";
		this.description = "jumpy has a good idea?? (nvm this is dumb)";
	}
	
	WurstplusSetting helmet_boot_percent = create("Helment Boots %", "StopEXHelmet", 80, 0, 100);
	WurstplusSetting chest_leggings_percent = create("Chest Leggins %", "StopEXChest", 100, 0, 100);

	private boolean should_cancel = false;

	@EventHandler
    private Listener<WurstplusEventPacket.SendPacket> packet_event = new Listener<>(event -> {
		if (event.get_packet() instanceof CPacketPlayerTryUseItem && should_cancel) {
			event.cancel();
		}
	});

	@Override
	public void update() {

		int counter = 0;

		for (Map.Entry<Integer, ItemStack> armor_slot : get_armor().entrySet()) {
			
			counter++;
			if (armor_slot.getValue().isEmpty()) continue;

			final ItemStack stack = armor_slot.getValue();

			double max_dam = stack.getMaxDamage();
			double dam_left = stack.getMaxDamage() - stack.getItemDamage();
			double percent = (dam_left / max_dam) * 100;

			if (counter == 1 || counter == 4) {
				if (percent >= helmet_boot_percent.get_value(1)) {
					if (is_holding_exp()) {
						should_cancel = true;
					} else {
						should_cancel = false;
					}
				} else {
					should_cancel = false;
				}
			} 
			
			if (counter == 2 || counter == 3) {
				if (percent >= chest_leggings_percent.get_value(1)) {
					if (is_holding_exp()) {
						should_cancel = true;
					} else {
						should_cancel = false;
					}
				} else {
					should_cancel = false;
				}
			} 

		}

	}

	private Map<Integer, ItemStack> get_armor() {
        return get_inv_slots(5, 8);
    }

    private Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> full_inv_slots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            full_inv_slots.put(current, mc.player.inventoryContainer.getInventory().get(current));
            current++;
        }
        return full_inv_slots;
	}
	
	public boolean is_holding_exp() {
		
		if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
			return true;
		}
		return false;

	}

}