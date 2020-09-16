package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WurstplusAutoArmour extends WurstplusHack {

    public WurstplusAutoArmour() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);

        this.name        = "Auto Armour";
        this.tag         = "AutoArmour";
        this.description = "WATCH UR BOOTS";
    }

    WurstplusSetting delay = create("Delay", "AADelay", 2, 0, 5);
    WurstplusSetting smart_mode = create("Smart Mode", "AASmartMode", true);
    WurstplusSetting put_back = create("Equip Armour", "AAEquipArmour", true);
    WurstplusSetting player_range = create("Player Range", "AAPlayerRange", 8, 0, 20);
    WurstplusSetting crystal_range = create("Crystal Range", "AACrystalRange", 13, 0, 20);
    WurstplusSetting boot_percent = create("Boot Percent", "AATBootPercent", 80, 0, 100);
    WurstplusSetting chest_percent = create("Chest Percent", "AATChestPercent", 80, 0, 100);

    private int delay_count;

    @Override
    protected void enable() {
        delay_count = 0;
    }

    @Override
    public void update() {

        if (mc.player.ticksExisted % 2 == 0) return;

        boolean flag = false;

        if (delay_count < delay.get_value(0)) {
            delay_count++;
            return;
        }
        delay_count = 0;

        if (smart_mode.get_value(true)) {
            if (!(is_crystal_in_range(crystal_range.get_value(1)) || is_player_in_range(player_range.get_value(1)))) flag = true;
        }

        if (flag) {
            if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                take_off();
            }
            return;
        }

        if (!put_back.get_value(true)) return;

        if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof InventoryEffectRenderer)) return;

        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];

        // initialize with currently equipped armor
        for(int armorType = 0; armorType < 4; armorType++)
        {
            ItemStack oldArmor = mc.player.inventory.armorItemInSlot(armorType);

            if(oldArmor.getItem() instanceof ItemArmor)
                bestArmorValues[armorType] =
                        ((ItemArmor)oldArmor.getItem()).damageReduceAmount;

            bestArmorSlots[armorType] = -1;
        }

        // search inventory for better armor
        for(int slot = 0; slot < 36; slot++)
        {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);

            if (stack.getCount() > 1)
                continue;

            if(!(stack.getItem() instanceof ItemArmor))
                continue;

            ItemArmor armor = (ItemArmor)stack.getItem();
            int armorType = armor.armorType.ordinal() - 2;

            if (armorType == 2 && mc.player.inventory.armorItemInSlot(armorType).getItem().equals(Items.ELYTRA)) continue;

            int armorValue = armor.damageReduceAmount;

            if(armorValue > bestArmorValues[armorType])
            {
                bestArmorSlots[armorType] = slot;
                bestArmorValues[armorType] = armorValue;
            }
        }

        // equip better armor
        for(int armorType = 0; armorType < 4; armorType++)
        {
            // check if better armor was found
            int slot = bestArmorSlots[armorType];
            if(slot == -1)
                continue;

            // check if armor can be swapped
            // needs 1 free slot where it can put the old armor
            ItemStack oldArmor = mc.player.inventory.armorItemInSlot(armorType);
            if(oldArmor != ItemStack.EMPTY || mc.player.inventory.getFirstEmptyStack() != -1)
            {
                // hotbar fix
                if(slot < 9)
                    slot += 36;

                // swap armor
                mc.playerController.windowClick(0, 8 - armorType, 0,
                        ClickType.QUICK_MOVE, mc.player);
                mc.playerController.windowClick(0, slot, 0,
                        ClickType.QUICK_MOVE, mc.player);

                break;
            }
        }

    }

    public boolean is_player_in_range(int range) {
        for (Entity player : mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player == mc.player) continue;
            if (mc.player.getDistance(player) < range) {
                return true;
            }
        }
        return false;
    }

    public boolean is_crystal_in_range(int range) {
        for (Entity c : mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList())) {
            if (mc.player.getDistance(c) < range) {
                return true;
            }
        }
        return false;
    }

    public void take_off() {
        if (!is_space()) return;

        for (final Map.Entry<Integer, ItemStack> armorSlot : get_armour().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (is_healed(stack)) {
                mc.playerController.windowClick(0, armorSlot.getKey(), 0, ClickType.QUICK_MOVE, mc.player);
                return;
            }
        }

    }

    public boolean is_space() {
        for (final Map.Entry<Integer, ItemStack> invSlot : get_inv().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.getItem() == Items.AIR) {
                return true;
            }
        }
        return false;
    }

    private static Map<Integer, ItemStack> get_inv() {
        return get_inv_slots(9, 44);
    }

    private static Map<Integer, ItemStack> get_armour() {
        return get_inv_slots(5, 8);
    }

    private static Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<>();
        while (current <= last) {
            fullInventorySlots.put(current, mc.player.inventoryContainer.getInventory().get(current));
            current++;
        }
        return fullInventorySlots;
    }

    public boolean is_healed(ItemStack item) {
        if (item.getItem() == Items.DIAMOND_BOOTS || item.getItem() == Items.DIAMOND_HELMET) {
            double max_dam = item.getMaxDamage();
            double dam_left = item.getMaxDamage() - item.getItemDamage();
            double percent = (dam_left / max_dam) * 100;
            return percent >= boot_percent.get_value(1);
        } else {
            double max_dam = item.getMaxDamage();
            double dam_left = item.getMaxDamage() - item.getItemDamage();
            double percent = (dam_left / max_dam) * 100;
            return percent >= chest_percent.get_value(1);
        }
    }

}
