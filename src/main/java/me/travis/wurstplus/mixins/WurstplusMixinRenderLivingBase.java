package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderLivingBase.class)
public abstract class WurstplusMixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {

    protected WurstplusMixinRenderLivingBase(final RenderManager renderManagerIn, final ModelBase modelBaseIn, final float shadowSizeIn) {
        super(renderManagerIn);
    }

    @Redirect(method = { "renderModel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModelHook(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (Wurstplus.get_hack_manager().get_module_with_tag("Chams").is_active()) {
            final WurstplusEventRenderEntityModel event = new WurstplusEventRenderEntityModel(0, modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Wurstplus.get_hack_manager().get_module_with_tag("Chams").on_render_model(event);
            if (event.isCancelled()) {
                return;
            }
        }
        modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }


}

