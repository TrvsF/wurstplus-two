package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.network.Packet;

// External.


public class WurstplusEventPacket extends WurstplusEventCancellable {
	private final Packet packet;

	public WurstplusEventPacket(Packet packet) {
		super();

		this.packet = packet;
	}

	public Packet get_packet() {
		return this.packet;
	}

	public static class ReceivePacket extends WurstplusEventPacket {
		public ReceivePacket(Packet packet) {
			super(packet);
		}
	}

	public static class SendPacket extends WurstplusEventPacket {
		public SendPacket(Packet packet) {
			super(packet);
		}
	}
}