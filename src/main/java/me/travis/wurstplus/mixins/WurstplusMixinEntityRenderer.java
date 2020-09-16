package me.travis.wurstplus.mixins;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSetupFog;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// External.

@Mixin(value = EntityRenderer.class)
public class WurstplusMixinEntityRenderer {
    
    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFog(int startCoords, float partialTicks, CallbackInfo p_Info)
    {
        WurstplusEventSetupFog event = new WurstplusEventSetupFog(startCoords, partialTicks);
        WurstplusEventBus.EVENT_BUS.post(event);
        
        if (event.isCancelled()) {
			return;
        }
        
    }

}