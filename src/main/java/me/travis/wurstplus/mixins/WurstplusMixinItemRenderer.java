package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class WurstplusMixinItemRenderer {

    private boolean injection;

    public WurstplusMixinItemRenderer() {
        this.injection = true;
    }

    @Shadow
    public abstract void renderItemInFirstPerson(final AbstractClientPlayer p0, final float p1, final float p2, final EnumHand p3, final float p4, final ItemStack p5, final float p6);

    @Inject(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemInFirstPersonHook(final AbstractClientPlayer player, final float p_187457_2_, final float p_187457_3_, final EnumHand hand, final float p_187457_5_, final ItemStack stack, final float p_187457_7_, final CallbackInfo info) {
        if (this.injection) {
            info.cancel();
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            this.injection = false;
            if (hand == EnumHand.MAIN_HAND) {
                if (Wurstplus.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active()) {
                    xOffset = Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1);
                    yOffset = Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainY").get_value(1);
                }
            }
            else if (Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffset").get_value(true) &&
            Wurstplus.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active()) {
                xOffset = Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffsetX").get_value(1);
                yOffset = Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVOffsetY").get_value(1);
            }
            this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
            this.injection = true;
        }
    }

    @Redirect(method = { "renderArmFirstPerson" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0))
    public void translateHook(final float x, final float y, final float z) {
        GlStateManager.translate(x + (Wurstplus.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active() ? Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1) : 0.0f),
                y + (Wurstplus.get_hack_manager().get_module_with_tag("CustomViewmodel").is_active() ? Wurstplus.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVMainX").get_value(1) : 0.0f), z);
    }

}
