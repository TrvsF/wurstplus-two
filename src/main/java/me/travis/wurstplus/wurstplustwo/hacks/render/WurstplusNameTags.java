package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderName;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDamageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

public class WurstplusNameTags extends WurstplusHack {

    public WurstplusNameTags() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Name Tags";
        this.tag = "NameTags";
        this.description = "MORE DETAILED NAMESSSSS";
    }

    WurstplusSetting show_armor = create("Armor", "NametagArmor", true);
    WurstplusSetting show_health = create("Health", "NametagHealth", true);
    WurstplusSetting show_ping = create("Ping", "NametagPing", true);
    WurstplusSetting show_totems = create("Totem Count", "NametagTotems", true);
    WurstplusSetting show_invis = create("Invis", "NametagInvis", true);
    WurstplusSetting reverse = create("Armour Reverse", "NametagReverse", true);
    WurstplusSetting simplify = create("Simplify", "NametagSimp", false);
    WurstplusSetting m_scale = create("Scale", "NametagScale", 4, 1, 15);
    WurstplusSetting range = create("Range", "NametagRange", 75, 1, 250);

    WurstplusSetting r = create("R", "NametagR", 255, 0, 255);
    WurstplusSetting g = create("G", "NametagG", 255, 0, 255);
    WurstplusSetting b = create("B", "NametagB", 255, 0, 255);
    WurstplusSetting a = create("A", "NametagA", 0.7f, 0f, 1f);
    WurstplusSetting rainbow_mode = create("Rainbow", "NametagRainbow", false);
    WurstplusSetting sat = create("Saturation", "NametagSatiation", 0.8, 0, 1);
    WurstplusSetting brightness = create("Brightness", "NametagBrightness", 0.8, 0, 1);

    @EventHandler
    private final Listener<WurstplusEventRenderName> on_render_name = new Listener<>(event -> {
            event.cancel();
    });

    @Override
    public void render(WurstplusEventRender event) {
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player != null && !player.equals(mc.player) && player.isEntityAlive() && (!player.isInvisible() || show_invis.get_value(true)) && mc.player.getDistance(player) < range.get_value(1)) {
                final double x = this.interpolate(player.lastTickPosX, player.posX, event.get_partial_ticks()) - mc.getRenderManager().renderPosX;
                final double y = this.interpolate(player.lastTickPosY, player.posY, event.get_partial_ticks()) - mc.getRenderManager().renderPosY;
                final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.get_partial_ticks()) - mc.getRenderManager().renderPosZ;
                this.renderNameTag(player, x, y, z, event.get_partial_ticks());
            }
        }
    }

    @Override
    public void update() {
        if (rainbow_mode.get_value(true)) {
            cycle_rainbow();
        }
    }

    public void cycle_rainbow() {

        float[] tick_color = {
                (System.currentTimeMillis() % (360 * 32)) / (360f * 32)
        };

        int color_rgb_o = Color.HSBtoRGB(tick_color[0], sat.get_value(1), brightness.get_value(1));

        r.set_value((color_rgb_o >> 16) & 0xFF);
        g.set_value((color_rgb_o >> 8) & 0xFF);
        b.set_value(color_rgb_o & 0xFF);

    }

    private void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.isSneaking() ? 0.5 : 0.7);
        final Entity camera = mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
        final int width = mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + m_scale.get_value(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        boolean is_friend = WurstplusFriendUtil.isFriend(player.getName());
        boolean is_enemy = WurstplusEnemyUtil.isEnemy(player.getName());
        int red = r.get_value(1);
        int green = g.get_value(1);
        int blue = b.get_value(1);
        if (is_friend) {
            red = 157;
            green = 99;
            blue = 255;
        }
        if (is_enemy) {
            red = 255;
            green = 40;
            blue = 7;
        }
        WurstplusRenderUtil.drawRect((float)(-width - 2)-1, (float)(-(mc.fontRenderer.FONT_HEIGHT + 1))-1, width + 3f, 2.5f, red, green, blue, (float) a.get_value(1));
        WurstplusRenderUtil.drawRect((float)(-width - 2), (float)(-(mc.fontRenderer.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, 1426063360);
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor)) {
            renderMainHand.stackSize = 1;
        }
        if (!renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
            final String stackName = renderMainHand.getDisplayName();
            final int stackNameWidth = mc.fontRenderer.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            mc.fontRenderer.drawStringWithShadow(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 18.0f), -1);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
        if (show_armor.get_value(true)) {
            GlStateManager.pushMatrix();
            int xOffset = -8;
            for (final ItemStack stack : player.inventory.armorInventory) {
                if (stack != null) {
                    xOffset -= 8;
                }
            }
            xOffset -= 8;
            final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
            if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof ItemTool || renderOffhand.getItem() instanceof ItemArmor)) {
                renderOffhand.stackSize = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (reverse.get_value(true)) {
                for (final ItemStack stack2 : player.inventory.armorInventory) {
                    if (stack2 != null) {
                        final ItemStack armourStack = stack2.copy();
                        if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                            armourStack.stackSize = 1;
                        }
                        this.renderItemStack(armourStack, xOffset);
                        xOffset += 16;
                    }
                }
            } else {
                for (int i = player.inventory.armorInventory.size(); i > 0; i--) {
                    final ItemStack stack2 = player.inventory.armorInventory.get(i - 1);
                    final ItemStack armourStack = stack2.copy();
                    if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                        armourStack.stackSize = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            }

            this.renderItemStack(renderMainHand, xOffset);
            GlStateManager.popMatrix();
        }
        mc.fontRenderer.drawStringWithShadow(displayTag, (float)(-width), (float)(-(mc.fontRenderer.FONT_HEIGHT - 1)), this.getDisplayColour(player));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }

    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -29);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, -29);
        mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }

    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -37;
        final NBTTagList enchants = stack.getEnchantmentTagList();
        if (enchants.tagCount() > 2 && simplify.get_value(true)) {
            mc.fontRenderer.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        } else {
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final short level = enchants.getCompoundTagAt(index).getShort("lvl");
                final Enchantment enc = Enchantment.getEnchantmentByID(id);
                if (enc != null) {
                    String encName = enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()) : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                    encName += level;
                    mc.fontRenderer.drawStringWithShadow(encName, (float)(x * 2), (float)enchantmentY, -1);
                    enchantmentY -= 8;
                }
            }
        }
        if (WurstplusDamageUtil.hasDurability(stack)) {
            final int percent = WurstplusDamageUtil.getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = section_sign() + "a";
            }
            else if (percent >= 25) {
                color = section_sign() + "e";
            }
            else {
                color = section_sign() + "c";
            }
            mc.fontRenderer.drawStringWithShadow(color + percent + "%", (float)(x * 2), (enchantmentY < -62) ? (float)enchantmentY : -62 , -1);
        }
    }

    private float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (final ItemStack stack : player.inventory.armorInventory) {
            float encY = 0.0f;
            if (stack != null) {
                final NBTTagList enchants = stack.getEnchantmentTagList();
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final Enchantment enc = Enchantment.getEnchantmentByID(id);
                    if (enc != null) {
                        encY += 8.0f;
                        arm = true;
                    }
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                final short id2 = enchants2.getCompoundTagAt(index2).getShort("id");
                final Enchantment enc2 = Enchantment.getEnchantmentByID(id2);
                if (enc2 != null) {
                    encY2 += 8.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand = player.getHeldItemOffhand().copy();
        if (renderOffHand.hasEffect()) {
            float encY = 0.0f;
            final NBTTagList enchants = renderOffHand.getEnchantmentTagList();
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final Enchantment enc = Enchantment.getEnchantmentByID(id);
                if (enc != null) {
                    encY += 8.0f;
                    arm = true;
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (arm ? 0 : 20) + enchantmentY;
    }

    private String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!show_health.get_value(true)) {
            return name;
        }
        final float health = player.getHealth() + player.getAbsorptionAmount();
        if (health <= 0) {
            return name + " - DEAD";
        }
        String color;
        if (health > 25.0f) {
            color = section_sign() + "5";
        }
        else if (health > 20.0f) {
            color = section_sign() + "a";
        }
        else if (health > 15.0f) {
            color = section_sign() + "2";
        }
        else if (health > 10.0f) {
            color = section_sign() + "6";
        }
        else if (health > 5.0f) {
            color = section_sign() + "c";
        }
        else {
            color = section_sign() + "4";
        }
        String pingStr = "";
        if (show_ping.get_value(true)) {
            try {
                final int responseTime = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                if (responseTime > 150) {
                    pingStr += section_sign() + "4";
                } else if (responseTime > 100) {
                    pingStr += section_sign() + "6";
                } else {
                    pingStr += section_sign() + "2";
                }
                pingStr += responseTime + "ms ";
            }
            catch (Exception ignore) {}
        }
        String popStr = " ";
        if (show_totems.get_value(true)) {
            try {
                popStr += (WurstplusTotempop.totem_pop_counter.get(player.getName()) == null ? section_sign() + "70" : section_sign() + "c -" + WurstplusTotempop.totem_pop_counter.get(player.getName()));
            } catch (Exception ignore) {}
        }
        if (Math.floor(health) == health) {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)Math.floor(health)) : "dead");
        }
        else {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)health) : "dead");
        }
        return pingStr + section_sign() + "r" + name + section_sign() + "r" + popStr;
    }

    private int getDisplayColour(final EntityPlayer player) {
        int colour = -5592406;
        if (WurstplusFriendUtil.isFriend(player.getName())) {
            return -11157267;
        }
        if (WurstplusEnemyUtil.isEnemy(player.getName())) {
            return -49632;
        }
        return colour;
    }

    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }

    public String section_sign() {
        return "\u00A7";
    }

}