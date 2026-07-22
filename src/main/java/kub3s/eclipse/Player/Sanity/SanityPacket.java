package kub3s.eclipse.Player.Sanity;

import net.minecraft.network.FriendlyByteBuf;

public class SanityPacket {
    public final int value;

    public SanityPacket(int value) {
        this.value = value;
    }

    public static void write(SanityPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.value);
    }

    public static SanityPacket read(FriendlyByteBuf buf) {
        return new SanityPacket(buf
                .readInt());
    }
}
