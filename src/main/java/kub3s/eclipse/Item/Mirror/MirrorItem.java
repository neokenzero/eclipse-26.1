package kub3s.eclipse.Item.Mirror;

import kub3s.eclipse.Data.FactionSpawnData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class MirrorItem extends Item {

    public MirrorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        ServerLevel serverLevel = (ServerLevel) player.level();

        if (MirrorModeState.setSpawnMode) {

            BlockPos pos = serverPlayer.blockPosition();

            FactionSpawnData data = FactionSpawnData.get(serverLevel);
            data.setPos("svet", pos);

            serverPlayer.sendSystemMessage(
                    net.minecraft.network.chat.Component.literal("§aFaction spawn set!"),
                    true
            );

            return InteractionResult.SUCCESS;
        }


        BlockPos spawnPos = serverLevel.getRespawnData().pos();
        serverPlayer.teleportTo(
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5
        );

        serverLevel.playSound(
                null,
                serverPlayer.getX(),
                serverPlayer.getY(),
                serverPlayer.getZ(),
                SoundEvents.CHORUS_FRUIT_TELEPORT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        serverPlayer.getCooldowns().addCooldown(this.getDefaultInstance(), 40);

        return InteractionResult.SUCCESS;
    }
}