package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WurstplusAutoEz extends WurstplusHack {
    
    public WurstplusAutoEz() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Auto Ez";
        this.tag = "AutoEz";
        this.description = "you just got nae nae'd by wurst+... 2";
    }

    int delay_count = 0;

    WurstplusSetting discord = create("Discord", "EzDiscord", false);
    WurstplusSetting custom = create("Custom", "EzCustom", false);

    private static final ConcurrentHashMap targeted_players = new ConcurrentHashMap();

    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<>(event -> {

        if (mc.player == null) return;

        if (event.get_packet() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) event.get_packet();
            if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK)) {
                Entity target_entity = cPacketUseEntity.getEntityFromWorld(mc.world);
                if (target_entity instanceof EntityPlayer) {
                    add_target(target_entity.getName());
                }
            }
        }

    });

    @EventHandler
    private Listener<LivingDeathEvent> living_death_listener = new Listener<>(event -> {

        if (mc.player == null) return;

        EntityLivingBase e = event.getEntityLiving();
        if (e == null) return;

        if (e instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e;

            if (player.getHealth() <= 0) {
                if (targeted_players.containsKey(player.getName())) {
                    announce(player.getName());
                }
            }
        }

    });

    @Override
    public void update() {

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0) {
                    if (targeted_players.containsKey(player.getName())) {
                        announce(player.getName());
                    }
                }
            }
        }

        targeted_players.forEach((name, timeout) -> {
            if ((int)timeout <= 0) {
                targeted_players.remove(name);
            } else {
                targeted_players.put(name, (int)timeout - 1);
            }

        });

        delay_count++;

    }

    public void announce(String name) {
        if (delay_count < 150) {
            return;
        }
        delay_count = 0;
        targeted_players.remove(name);
        String message = "";
        if (custom.get_value(true)) {
            message += WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "");
        } else {
            message += "you just got nae nae'd by wurst+2";
        }
        if (discord.get_value(true)) {
            message += " - discord.gg/wurst";
        }
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

    public static void add_target(String name) {
        if (!Objects.equals(name, mc.player.getName())) {
            targeted_players.put(name, 20);
        }
    }

}