package kub3s.eclipse.Item.Mirror;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class MirrorPacket {

    public static BlockPos pos;

    public static void write(FriendlyByteBuf buf, BlockPos pos) {
        buf.writeBlockPos(pos);
    }

    public static BlockPos read(FriendlyByteBuf buf) {
        return buf.readBlockPos();
    }

    public static void handle(ServerPlayer player, BlockPos pos) {

    }
}