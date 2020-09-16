package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class WurstplusArmorPreview extends WurstplusPinnable {
	public WurstplusArmorPreview() {
		super("Armor Preview", "ArmorPreview", 1, 0, 0);
	}

	private final RenderItem itemRender = mc.getRenderItem();

	@Override
	public void render() {

		if (mc.player != null) {
			if (is_on_gui()) {
				create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
			}
		}

		GlStateManager.enableTexture2D();

		final ScaledResolution resolution = new ScaledResolution(mc);
		final int i = resolution.getScaledWidth() / 2;
		final int y = resolution.getScaledHeight() - 55 - (mc.player.isInWater() ? 10 : 0);
		int iteration = 0;

		for (final ItemStack is : mc.player.inventory.armorInventory) {
			++iteration;
				if (is.isEmpty()) {
					continue;
				}

			final int x = i - 90 + (9 - iteration) * 20 + 2;
				GlStateManager.enableDepth();

					itemRender.zLevel = 200.0f;
					itemRender.renderItemAndEffectIntoGUI(is, x, y);
					itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
					itemRender.zLevel = 0.0f;

				GlStateManager.enableTexture2D();
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();

			final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";

					mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 16777215);

			final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
			final float red = 1.0f - green;
			final int dmg = 100 - (int)(red * 100.0f);

			if (dmg >= 100) {
				mc.fontRenderer.drawStringWithShadow(dmg + "", (float)(x + 8 - mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
			} else if (dmg <= 0) {
				mc.fontRenderer.drawStringWithShadow(0 + "%", (float)(x + 8 - mc.fontRenderer.getStringWidth(0 + "") / 2), (float)(y - 11), toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
			} else {
				mc.fontRenderer.drawStringWithShadow(dmg + "%", (float)(x + 8 - mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
			}
		}

		GlStateManager.enableDepth();
		GlStateManager.disableLighting();

		this.set_width(50);
		this.set_height(25);
	}

	public static int toHex(int r, int g, int b){ return  (0xff << 24) | ((r&0xff) << 16) | ((g&0xff) << 8) | (b&0xff); }

}

