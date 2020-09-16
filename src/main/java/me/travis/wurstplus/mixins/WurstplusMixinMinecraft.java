package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGUIScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// External.


@Mixin(value = Minecraft.class)
public class WurstplusMixinMinecraft {
	@Inject(method = "displayGuiScreen", at = @At("HEAD"))
	private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
		WurstplusEventGUIScreen guiscreen = new WurstplusEventGUIScreen(guiScreenIn);

		WurstplusEventBus.EVENT_BUS.post(guiscreen);
	}

	@Inject(method = "shutdown", at = @At("HEAD"))
	private void shutdown(CallbackInfo info) {
		Wurstplus.get_config_manager().save_settings();
	}

}