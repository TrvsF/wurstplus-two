package me.travis.wurstplus.wurstplustwo.guiscreen.hud;


import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;


public class WurstplusInventoryPreview extends WurstplusPinnable {
	public WurstplusInventoryPreview() {
		super("Inventory Preview", "InventoryPreview", 1, 0, 0);
	}

	@Override
	public void render() {
		if (mc.player != null) {
			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();

			create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);

			this.set_width(16 * 9);
			this.set_height(16 * 3);

			for (int i = 0; i < 27; i++) {
				ItemStack item_stack = mc.player.inventory.mainInventory.get(i + 9);

				int item_position_x = (int) this.get_x() + (i % 9) * 16;
				int item_position_y = (int) this.get_y() + (i / 9) * 16;

				mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, item_stack, item_position_x, item_position_y, null);
			}

			mc.getRenderItem().zLevel = - 5.0f;

			RenderHelper.disableStandardItemLighting();			
			
			GlStateManager.popMatrix();
		}
	}
}