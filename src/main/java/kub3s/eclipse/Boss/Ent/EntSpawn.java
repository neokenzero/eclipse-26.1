package kub3s.eclipse.Boss.Ent;

import kub3s.eclipse.Init.ModEntityTypes;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class EntSpawn {
    private static final long TEN_MINUTES = 600000L;
    private static final Map<UUID, List<Long>> WOOD_BREAKS = new HashMap();

    public EntSpawn() {
    }

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((PlayerBlockBreakEvents.After) (world, player, pos, state, entity) -> {
            if (!world.isClientSide()) {
                if (player instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    if (isWood(state)) {
                        UUID uuid = player.getUUID();
                        long now = System.currentTimeMillis();
                        WOOD_BREAKS.putIfAbsent(uuid, new ArrayList());
                        List<Long> breaks = (List) WOOD_BREAKS.get(uuid);
                        breaks.removeIf((time) -> now - time > 600000L);
                        breaks.add(now);
                        if (breaks.size() >= 10 && player.getRandom().nextInt(100) == 0) {
                            spawnEnt(serverPlayer);
                            breaks.clear();
                        }

                    }
                }
            }
        });
    }

    private static boolean isWood(BlockState state) {
        return state.is(Blocks.OAK_LOG) || state.is(Blocks.SPRUCE_LOG) || state.is(Blocks.BIRCH_LOG) || state.is(Blocks.JUNGLE_LOG) || state.is(Blocks.ACACIA_LOG) || state.is(Blocks.DARK_OAK_LOG) || state.is(Blocks.MANGROVE_LOG) || state.is(Blocks.CHERRY_LOG);
    }

    private static void spawnEnt(ServerPlayer player) {
        /*if (AksUtils.hasAccessory(player, ModEntWard.ENT_WARD)) {
            player.sendSystemMessage(Component.literal("§2Оберег отогнал пробуждающегося Энта..."));
        } else {*/
        ServerLevel level = player.level();
        EntEntity ent = (EntEntity) ModEntityTypes.ENT.create(level, EntitySpawnReason.EVENT);
        if (ent != null) {
            BlockPos pos = player.blockPosition().offset(player.getRandom().nextInt(-10, 11), 0, player.getRandom().nextInt(-10, 11));
            ent.setPos((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
            level.addFreshEntity(ent);
            player.sendSystemMessage(Component.literal("§2Древний Энт пробудился..."));
        }
    }
}