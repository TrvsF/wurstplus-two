//package me.travis.wurstplus.wurstplusmod.hacks.render;
//
//import com.google.common.util.concurrent.FutureCallback;
//import com.mojang.authlib.GameProfile;
//import com.mojang.realmsclient.gui.ChatFormatting;
//import joptsimple.internal.Strings;
//import me.travis.travis.draw.TravisRenderHelp;
//import me.travis.wurstplus.Wurstplus;
//import me.travis.wurstplus.wurstplusmod.util.WurstplusMessage;
//import me.travis.wurstplus.wurstplusmod.hacks.WurstplusModule;
//import me.travis.wurstplus.wurstplusmod.events.WurstplusEventPacket;
//import me.travis.wurstplus.wurstplusmod.events.WurstplusEventRender;
//import me.travis.wurstplus.wurstplusmod.guiscreen.settings.WurstplusSetting;
//import me.travis.wurstplus.wurstplusmod.hacks.WurstplusCategory;
//import me.travis.wurstplus.wurstplusmod.util.*;
//import me.travis.wurstplus.wurstplusmod.util.forgehax.PlayerInfo;
//import me.travis.wurstplus.wurstplusmod.util.forgehax.PlayerInfoHelper;
//import me.zero.alpine.fork.listener.EventHandler;
//import me.zero.alpine.fork.listener.Listener;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.server.SPacketPlayerListItem;
//import net.minecraft.network.play.server.SPacketPlayerListItem.Action;
//import net.minecraft.util.math.Vec3d;
//
//import javax.annotation.Nullable;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class WurstplusLogSpots extends WurstplusModule {
//
//    public WurstplusLogSpots() {
//        super(WurstplusCategory.WURSTPLUS_RENDER);
//
//        // Info.
//        this.name        = "Log Spots";
//        this.tag         = "LogSpots";
//        this.description = "ez log";
//
//        // Release or launch the module.
//        release("Wurst+2 - Module - Wurst+2");
//    }
//
//    WurstplusSetting range = create("Range", "LogSpotsRange", 256, 1, 512);
//
//    private List<WurstplusLogInfo> logged_spots = new ArrayList<>();
//    private final List<EntityPlayer> last_tick_entities = new ArrayList<>();
//    private boolean halt = false;
//
//    private String last_logged = "";
//
//    int r;
//    int g;
//    int b;
//
//    @EventHandler
//    private Listener<WurstplusEventPacket.ReceivePacket> listener = new Listener<>(event -> {
//        if (event.get_packet() instanceof SPacketPlayerListItem) {
//
//            final SPacketPlayerListItem packet = (SPacketPlayerListItem) event.get_packet();
//
//            if(!Action.ADD_PLAYER.equals(packet.getAction()) && !Action.REMOVE_PLAYER.equals(packet.getAction())) return;
//
//            packet
//                    .getEntries()
//                    .stream()
//                    .filter(Objects::nonNull)
//                    .filter(
//                            data ->
//                                    !Strings.isNullOrEmpty(data.getProfile().getName())
//                                            || data.getProfile().getId() != null)
//                    .forEach(
//                            data -> {
//                                final String name = data.getProfile().getName();
//                                final UUID id = data.getProfile().getId();
//                                final AtomicInteger retries = new AtomicInteger(2);
//                                PlayerInfoHelper.registerWithCallback(
//                                        id,
//                                        name,
//                                        new FutureCallback<PlayerInfo>() {
//                                            @Override
//                                            public void onSuccess(@Nullable PlayerInfo result) {
//                                                event_manager(packet.getAction(), result, data.getProfile());
//                                            }
//
//                                            @Override
//                                            public void onFailure(Throwable t) {
//                                                if (retries.getAndDecrement() > 0) {
//                                                    PlayerInfoHelper.registerWithCallback(data.getProfile().getId(), name, this);
//                                                } else {
//                                                    t.printStackTrace();
//                                                    PlayerInfoHelper.generateOfflineWithCallback(name, this);
//                                                }
//                                            }
//                                        });
//                            });
//
//        }
//    });
//
//    @Override
//    public void update() {
//
//        if (halt) return;
//
//        if (mc.world == null) {
//            logged_spots.clear();
//            return;
//        }
//
//        if (!logged_spots.isEmpty()) {
//            // i fucking hate this
//            try {
//                logged_spots.removeIf(spot -> mc.player.getDistance(spot.get_entity()) >= range.get_value(1));
//            } catch (Exception ignored) {}
//        }
//
//        last_logged = "";
//
//        last_tick_entities.clear();
//        for (Entity e : mc.world.getLoadedEntityList()) {
//            if (e instanceof EntityPlayer) {
//                last_tick_entities.add((EntityPlayer) e);
//            }
//        } check_spots();
//
//    }
//
//    public void check_spots() {
//        final List<String> known_names = new ArrayList<>();
//        final List<WurstplusLogInfo> players = new ArrayList<>();
//        for (WurstplusLogInfo info : logged_spots) {
//            if (!known_names.contains(info.get_name())) {
//                players.add(info);
//                known_names.add(info.get_name());
//            }
//        }
//        logged_spots = players;
//    }
//
//    private void event_manager(SPacketPlayerListItem.Action action, PlayerInfo info, GameProfile profile) {
//
//        if (info == null) return;
//        if (info.getName().equals(last_logged)) return;
//        if (halt) return;
//
//        last_logged = info.getName();
//
//        if (action == Action.ADD_PLAYER) {
//
//            if (info.getName().equals(mc.player.getName())) return;
//
//            for (WurstplusLogInfo i : logged_spots) {
//                if (i.get_name().equals(info.getName())) {
//                    WurstplusMessage.send_client_message("my man " + ChatFormatting.GREEN + info.getName() + ChatFormatting.RESET + " has just come back!");
//                    logged_spots.remove(i);
//                }
//            }
//
//        } else if (action == Action.REMOVE_PLAYER) {
//
//            for (EntityPlayer player : last_tick_entities) {
//                if (info.getName().equals(player.getName())) {
//                    final EntityPlayer entity = mc.world.getPlayerEntityByUUID(info.getId());
//                    String date = new SimpleDateFormat("k:mm").format(new Date());
//                    logged_spots.add(new WurstplusLogInfo(entity, player.getName(), date, player.getHealth() + player.getAbsorptionAmount(), player.getHeldEquipment(), player.getArmorInventoryList().iterator(), player.getRenderBoundingBox()));
//                    String pos = "x" + Math.round(player.posX) + " y" + Math.round(player.posY) + " z" + Math.round(player.posZ);
//                    WurstplusMessage.send_client_message("mr. " + ChatFormatting.RED + info.getName() + ChatFormatting.RESET + " has just logged out at " + pos);
//                    return;
//                }
//            }
//
//        }
//
//    }
//
//    @Override
//    public void render(WurstplusEventRender event) {
//
//        try {
//            if (!logged_spots.isEmpty()) {
//                synchronized (logged_spots) {
//                    halt = true;
//                    for (WurstplusLogInfo logged_spot : logged_spots) {
//                        if (mc.player.getDistance(logged_spot.get_entity()) < 500) {
//
//                            TravisRenderHelp.prepare("lines");
//                            TravisRenderHelp.draw_cube_line(TravisRenderHelp.get_buffer_build(),
//                                    (float) logged_spot.get_bb().minX, (float) logged_spot.get_bb().minY, (float) logged_spot.get_bb().minZ,
//                                    0.6f, 2, 0.6f,
//                                    r, g, b, 225,
//                                    "all"
//                            );
//                            TravisRenderHelp.release();
//
//                            double x = this.interpolate(logged_spot.get_entity().lastTickPosX, logged_spot.get_entity().posX, event.get_partial_ticks()) - mc.getRenderManager().renderPosX;
//                            double y = this.interpolate(logged_spot.get_entity().lastTickPosY, logged_spot.get_entity().posY, event.get_partial_ticks()) - mc.getRenderManager().renderPosY;
//                            double z = this.interpolate(logged_spot.get_entity().lastTickPosZ, logged_spot.get_entity().posZ, event.get_partial_ticks()) - mc.getRenderManager().renderPosZ;
//
//                            renderNameTag(logged_spot, x, y, z, event.get_partial_ticks());
//                        }
//                    }
//                    halt = false;
//                }
//            }
//        } catch (Exception e) {
//            WurstplusMessage.send_client_error_message("Logout spots error: " + e);
//            logged_spots.clear();
//        }
//
//    }
//
//    @Override
//    protected void enable() {
//        r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
//        g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
//        b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
//        logged_spots.clear();
//        halt = false;
//    }
//
//    @Override
//    protected void disable() {
//        logged_spots.clear();
//    }
//
//    private void renderNameTag(final WurstplusLogInfo info, final double x, final double yi, final double z, final float delta) {
//        final double y = yi + 0.7;
//        final Entity camera = mc.getRenderViewEntity();
//        assert camera != null;
//        final double originalPositionX = camera.posX;
//        final double originalPositionY = camera.posY;
//        final double originalPositionZ = camera.posZ;
//        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
//        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
//        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
//        final String displayTag1 = info.get_name() + " XYZ: " + Math.round(info.get_entity().getPosition().getX()) + ", " + Math.round(info.get_entity().getPosition().getY()) + ", " + Math.round(info.get_entity().getPosition().getZ());
//        final String displayTag2 = Math.round(info.get_health()) + "hp";
//        StringBuilder displayTag3 = new StringBuilder("Items: ");
//        for (ItemStack stack : info.get_held_items()) {
//            displayTag3.append(stack.getDisplayName()).append(" ");
//        }
//        final double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
//        double scale = (0.0018 + 5 * (distance * 0.3)) / 1000.0;
//        if (distance <= 8.0) {
//            scale = 0.0245;
//        }
//        GlStateManager.pushMatrix();
//        RenderHelper.enableStandardItemLighting();
//        GlStateManager.enablePolygonOffset();
//        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
//        GlStateManager.disableLighting();
//        GlStateManager.translate((float)x, (float)y + 1.4f, (float)z);
//        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
//        GlStateManager.rotate(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
//        GlStateManager.scale(-scale, -scale, scale);
//        GlStateManager.disableDepth();
//        GlStateManager.enableBlend();
//        GlStateManager.enableBlend();
//        GlStateManager.disableBlend();
//        mc.fontRenderer.drawStringWithShadow(displayTag1, (float)(-mc.fontRenderer.getStringWidth(displayTag1) / 2), -12, toRGBA(r, g, b, 255));
//        mc.fontRenderer.drawStringWithShadow(displayTag2, (float)(-mc.fontRenderer.getStringWidth(displayTag2) / 2), 0, toRGBA(r, g, b, 255));
//        mc.fontRenderer.drawStringWithShadow(displayTag3.toString(), (float)(-mc.fontRenderer.getStringWidth(displayTag3.toString()) / 2), 12, toRGBA(r, g, b, 255));
//        camera.posX = originalPositionX;
//        camera.posY = originalPositionY;
//        camera.posZ = originalPositionZ;
//        GlStateManager.enableDepth();
//        GlStateManager.disableBlend();
//        GlStateManager.disablePolygonOffset();
//        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
//        GlStateManager.popMatrix();
//    }
//
//    private double interpolate(final double previous, final double current, final float delta) {
//        return previous + (current - previous) * delta;
//    }
//
//    public static int toRGBA(final int r, final int g, final int b, final int a) {
//        return (r << 16) + (g << 8) + b + (a << 24);
//    }
//
//}