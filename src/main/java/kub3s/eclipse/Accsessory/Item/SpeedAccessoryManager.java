//package kub3s.eclipse.Accsessory.Item;
//
//import eclipsemod.eclipse.item.naturering.ModNatureRing;
//import eclipsemod.eclipse.item.sandboots.ModSandBoots;
//import eclipsemod.eclipse.item.speedboots.ModSpeedBoots;
//import eclipsemod.eclipse.item.stoneamulet.ModStoneAmulet;
//import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.resources.Identifier;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.entity.ai.attributes.AttributeInstance;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//
//public class SpeedAccessoryManager {
//
//    private static final Identifier SPEED_MODIFIER_ID =
//            Identifier.fromNamespaceAndPath("eclipse", "speed_accessory_speed");
//
//    private static final double ACCESSORY_BONUS = 0.1;
//    private static final double SPEED_BOOTS_BONUS = 0.15;
//
//    private static final int CHECK_RADIUS = 3;
//    private static final int CHECK_INTERVAL = 10;
//    private static final int PARTICLE_INTERVAL = 4;
//
//    private static final Map<UUID, Integer> COOLDOWNS = new HashMap<>();
//
//    private static final Map<UUID, Boolean> NATURE_STATE = new HashMap<>();
//    private static final Map<UUID, Boolean> SAND_STATE = new HashMap<>();
//    private static final Map<UUID, Boolean> STONE_STATE = new HashMap<>();
//
//    private static final Map<UUID, Integer> PARTICLE_COOLDOWNS = new HashMap<>();
//
//
//    public static void register() {
//
//        ServerTickEvents.END_SERVER_TICK.register(server -> {
//
//            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
//
//                UUID uuid = player.getUUID();
//
//                AksInventory aks =
//                        player.getAttachedOrCreate(
//                                AksAttachment.INVENTORY
//                        );
//
//                boolean hasNatureRing = false;
//                boolean hasSandBoots = false;
//                boolean hasStoneAmulet = false;
//                boolean hasSpeedBoots = false;
//
//                for (ItemStack stack : aks.getItems()) {
//
//                    if (stack.getItem() == ModNatureRing.NATURE_RING) {
//                        hasNatureRing = true;
//                    } else if (stack.getItem() == ModSandBoots.SAND_BOOTS) {
//                        hasSandBoots = true;
//                    } else if (stack.getItem() == ModStoneAmulet.STONE_AMULET) {
//                        hasStoneAmulet = true;
//                    } else if (stack.getItem() == ModSpeedBoots.SPEED_BOOTS) {
//                        hasSpeedBoots = true;
//                    }
//
//                }
//
//                AttributeInstance speedAttribute =
//                        player.getAttribute(Attributes.MOVEMENT_SPEED);
//
//                if (speedAttribute == null)
//                    continue;
//
//                if (!hasNatureRing && !hasSandBoots && !hasStoneAmulet && !hasSpeedBoots) {
//
//                    if (speedAttribute.getModifier(SPEED_MODIFIER_ID) != null) {
//                        speedAttribute.removeModifier(SPEED_MODIFIER_ID);
//                    }
//
//                    NATURE_STATE.put(uuid, false);
//                    SAND_STATE.put(uuid, false);
//                    STONE_STATE.put(uuid, false);
//
//                    continue;
//
//                }
//
//
//                int cooldown = COOLDOWNS.getOrDefault(uuid, 0);
//
//                boolean nearNature;
//                boolean nearSand;
//                boolean nearStone;
//
//                if (cooldown <= 0) {
//
//                    nearNature = isNearNature(player);
//                    nearSand = isNearSand(player);
//                    nearStone = isNearStone(player);
//
//                    NATURE_STATE.put(uuid, nearNature);
//                    SAND_STATE.put(uuid, nearSand);
//                    STONE_STATE.put(uuid, nearStone);
//
//                    COOLDOWNS.put(uuid, CHECK_INTERVAL);
//
//                } else {
//
//                    nearNature = NATURE_STATE.getOrDefault(uuid, false);
//                    nearSand = SAND_STATE.getOrDefault(uuid, false);
//                    nearStone = STONE_STATE.getOrDefault(uuid, false);
//
//                    COOLDOWNS.put(uuid, cooldown - 1);
//
//                }
//
//
//                double bonus = 0.0;
//
//                // Приоритет: speed_boots перекрывает остальные (только один эффект разом).
//                if (hasSpeedBoots && (nearNature || nearSand || nearStone)) {
//
//                    bonus = SPEED_BOOTS_BONUS;
//
//                } else if (hasNatureRing && nearNature) {
//
//                    bonus = ACCESSORY_BONUS;
//
//                } else if (hasSandBoots && nearSand) {
//
//                    bonus = ACCESSORY_BONUS;
//
//                } else if (hasStoneAmulet && nearStone) {
//
//                    bonus = ACCESSORY_BONUS;
//
//                }
//
//
//                boolean hasModifier =
//                        speedAttribute.getModifier(SPEED_MODIFIER_ID) != null;
//
//                if (bonus > 0.0) {
//
//                    if (hasModifier) {
//                        speedAttribute.removeModifier(SPEED_MODIFIER_ID);
//                    }
//
//                    speedAttribute.addTransientModifier(
//                            new AttributeModifier(
//                                    SPEED_MODIFIER_ID,
//                                    bonus,
//                                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
//                            )
//                    );
//
//                    tickParticles(player, uuid);
//
//                } else {
//
//                    if (hasModifier) {
//                        speedAttribute.removeModifier(SPEED_MODIFIER_ID);
//                    }
//
//                    PARTICLE_COOLDOWNS.remove(uuid);
//
//                }
//
//            }
//
//        });
//
//    }
//
//
//    private static void tickParticles(ServerPlayer player, UUID uuid) {
//
//        int particleCooldown = PARTICLE_COOLDOWNS.getOrDefault(uuid, 0);
//
//        if (particleCooldown > 0) {
//            PARTICLE_COOLDOWNS.put(uuid, particleCooldown - 1);
//            return;
//        }
//
//        PARTICLE_COOLDOWNS.put(uuid, PARTICLE_INTERVAL);
//
//        ServerLevel level = player.level();
//        RandomSource random = player.getRandom();
//
//        double x = player.getX();
//        double y = player.getY();
//        double z = player.getZ();
//        double width = player.getBbWidth();
//        double height = player.getBbHeight();
//
//        for (int i = 0; i < 3; i++) {
//
//            double ox = (random.nextDouble() - 0.5) * (width + 0.3);
//            double oy = random.nextDouble() * height;
//            double oz = (random.nextDouble() - 0.5) * (width + 0.3);
//
//            level.sendParticles(
//                    ParticleTypes.HAPPY_VILLAGER,
//                    x + ox,
//                    y + oy,
//                    z + oz,
//                    1,
//                    0.0, 0.02, 0.0,
//                    0.0
//            );
//
//        }
//
//    }
//
//
//    private static boolean isNearNature(ServerPlayer player) {
//
//        BlockPos center = player.blockPosition();
//
//        for (BlockPos pos : BlockPos.betweenClosed(
//                center.offset(-CHECK_RADIUS, -1, -CHECK_RADIUS),
//                center.offset(CHECK_RADIUS, 1, CHECK_RADIUS)
//        )) {
//
//            BlockState state = player.level().getBlockState(pos);
//
//            if (isNatureBlock(state)) {
//                return true;
//            }
//
//        }
//
//        return false;
//
//    }
//
//
//    private static boolean isNearSand(ServerPlayer player) {
//
//        BlockPos center = player.blockPosition();
//
//        for (BlockPos pos : BlockPos.betweenClosed(
//                center.offset(-CHECK_RADIUS, -1, -CHECK_RADIUS),
//                center.offset(CHECK_RADIUS, 1, CHECK_RADIUS)
//        )) {
//
//            BlockState state = player.level().getBlockState(pos);
//
//            if (isSandBlock(state)) {
//                return true;
//            }
//
//        }
//
//        return false;
//
//    }
//
//
//    private static boolean isNearStone(ServerPlayer player) {
//
//        BlockPos center = player.blockPosition();
//
//        for (BlockPos pos : BlockPos.betweenClosed(
//                center.offset(-CHECK_RADIUS, -1, -CHECK_RADIUS),
//                center.offset(CHECK_RADIUS, 1, CHECK_RADIUS)
//        )) {
//
//            BlockState state = player.level().getBlockState(pos);
//
//            if (isStoneBlock(state)) {
//                return true;
//            }
//
//        }
//
//        return false;
//
//    }
//
//
//    private static boolean isNatureBlock(BlockState state) {
//
//        if (state.is(BlockTags.LEAVES))
//            return true;
//
//        if (state.is(Blocks.MOSS_BLOCK) || state.is(Blocks.MOSS_CARPET))
//            return true;
//
//        if (state.is(Blocks.SHORT_GRASS)
//                || state.is(Blocks.TALL_GRASS)
//                || state.is(Blocks.FERN)
//                || state.is(Blocks.LARGE_FERN)
//                || state.is(Blocks.GRASS_BLOCK))
//            return true;
//
//        return false;
//
//    }
//
//
//    private static boolean isSandBlock(BlockState state) {
//
//        return state.is(Blocks.SAND)
//                || state.is(Blocks.RED_SAND)
//                || state.is(Blocks.SANDSTONE)
//                || state.is(Blocks.RED_SANDSTONE);
//
//    }
//
//
//    private static boolean isStoneBlock(BlockState state) {
//
//        return state.is(Blocks.COBBLESTONE)
//                || state.is(Blocks.MOSSY_COBBLESTONE)
//                || state.is(Blocks.STONE)
//                || state.is(Blocks.BLACKSTONE);
//
//    }
//
//}
