package me.travis.wurstplus.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// External.


@Mixin(value = NetworkManager.class)
public class WurstplusMixinNetworkManager {
	// Receive packet.
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	private void receive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
		WurstplusEventPacket event_packet = new WurstplusEventPacket.ReceivePacket(packet);

		WurstplusEventBus.EVENT_BUS.post(event_packet);

		if (event_packet.isCancelled()) {
			callback.cancel();
		}
	}

	// Send packet.
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
	private void send(Packet<?> packet, CallbackInfo callback) {
		WurstplusEventPacket event_packet = new WurstplusEventPacket.SendPacket(packet);

		WurstplusEventBus.EVENT_BUS.post(event_packet);

		if (event_packet.isCancelled()) {
			callback.cancel();
		}
	}

	// Exception packet.
	@Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
	private void exception(ChannelHandlerContext exc, Throwable exc_, CallbackInfo callback) {
		if (exc_ instanceof Exception) {
			callback.cancel();
		}
	}
}