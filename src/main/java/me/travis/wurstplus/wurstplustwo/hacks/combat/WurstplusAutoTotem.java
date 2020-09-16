package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class WurstplusAutoTotem extends WurstplusHack {

    public WurstplusAutoTotem() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name        = "Auto Totem";
        this.tag         = "AutoTotem";
        this.description = "put totem in offhand";
    }

    WurstplusSetting delay = create("Delay", "TotemDelay", false);

    private boolean switching = false;
    private int last_slot;

    @Override
    public void update() {

        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

            if (switching) {
                swap_items(last_slot, 2);
                return;
            }

            if (mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                swap_items(get_item_slot(), delay.get_value(true) ? 1 : 0);
            }

        }

    }

    private int get_item_slot() {
        if (Items.TOTEM_OF_UNDYING == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public void swap_items(int slot, int step) {
        if (slot == -1) return;
        if (step == 0) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }
        if (step == 1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = true;
            last_slot = slot;
        }
        if (step == 2) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = false;
        }

        mc.playerController.updateController();
    }

}
