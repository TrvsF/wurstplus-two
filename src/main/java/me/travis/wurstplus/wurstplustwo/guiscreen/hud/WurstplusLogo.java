package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusLogo extends WurstplusPinnable {
    
    public WurstplusLogo() {
        super("Logo", "Logo", 1, 0, 0);
    }

    ResourceLocation r = new ResourceLocation("custom/wurst.png");

    @Override
	public void render() {

		GL11.glPushMatrix();
        GL11.glTranslatef(this.get_x(), this.get_y(), 0.0F);
        WurstplusTextureHelper.drawTexture(r, this.get_x(), this.get_y(), 100, 25);
        GL11.glPopMatrix();

		this.set_width(100);
		this.set_height(25);
	}


}