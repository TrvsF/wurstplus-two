package me.travis.wurstplus.wurstplustwo.hacks.combat;


import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntity;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;


public class WurstplusVelocity extends WurstplusHack {
	public WurstplusVelocity() {
		super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "Velocity";
		this.tag         = "Velocity";
		this.description = "No kockback";
	}

	@EventHandler
	private Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener<>(event -> {
		if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
			if (event.get_packet() instanceof SPacketEntityVelocity) {
				SPacketEntityVelocity knockback = (SPacketEntityVelocity) event.get_packet();

				if (knockback.getEntityID() == mc.player.getEntityId()) {
					event.cancel();

					knockback.motionX *= 0.0f;
					knockback.motionY *= 0.0f;
					knockback.motionZ *= 0.0f;
				}
			} else if (event.get_packet() instanceof SPacketExplosion) {
				event.cancel();

				SPacketExplosion knockback = (SPacketExplosion) event.get_packet();

				knockback.motionX *= 0.0f;
				knockback.motionY *= 0.0f;
				knockback.motionZ *= 0.0f;
			}
		}
	});

	@EventHandler
	private Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener<>(event -> {
		if (event.get_entity() == mc.player) {
			event.cancel();

		}
	});
}