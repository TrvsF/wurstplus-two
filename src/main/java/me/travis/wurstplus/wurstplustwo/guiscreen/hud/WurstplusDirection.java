package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.util.math.MathHelper;

public class WurstplusDirection extends WurstplusPinnable {
    
    public WurstplusDirection() {
        super("Direction", "Direction", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		final String direction = String.format("%s" + " " + ChatFormatting.GRAY + "%s", this.getFacing(), this.getTowards());

		create_line(direction, this.docking(1, direction), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(direction, "width") + 2);
		this.set_height(this.get(direction, "height") + 2);
    }
    
    private String getFacing()
    {
        switch (MathHelper.floor((double) (mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7)
        {
            case 0:
                return "South";
            case 1:
                return "South West";
            case 2:
                return "West";
            case 3:
                return "North West";
            case 4:
                return "North";
            case 5:
                return "North East";
            case 6:
                return "East";
            case 7:
                return "South East";
        }
        return "Invalid";
    }

    private String getTowards()
    {
        switch (MathHelper.floor((double) (mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7)
        {
            case 0:
                return "+Z";
            case 1:
                return "-X +Z";
            case 2:
                return "-X";
            case 3:
                return "-X -Z";
            case 4:
                return "-Z";
            case 5:
                return "+X -Z";
            case 6:
                return "+X";
            case 7:
                return "+X +Z";
        }
        return "Invalid";
    }

}