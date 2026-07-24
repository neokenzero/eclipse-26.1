package kub3s.eclipse.Accsessory.Item;

import kub3s.eclipse.Accsessory.System.AccessoryAttachment;
import kub3s.eclipse.Accsessory.System.AccessoryInventory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static kub3s.eclipse.Init.ModItems.GOD_ACCESSORY;

public class GodAccessoryManager {


    private static final int EFFECT_DURATION = 220;

    private static final int PARTICLE_INTERVAL = 2;

    private static final Map<UUID, Integer> PARTICLE_COOLDOWNS = new HashMap<>();
    private static final Map<UUID, Boolean> PARTICLE_TOGGLE = new HashMap<>();


    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                UUID uuid = player.getUUID();

                AccessoryInventory aks =
                        player.getAttachedOrCreate(
                                AccessoryAttachment.INVENTORY
                        );

                boolean hasGodAccessory = false;

                for (ItemStack stack : aks.getItems()) {

                    if (stack.getItem() == GOD_ACCESSORY) {
                        hasGodAccessory = true;
                        break;
                    }

                }

                if (!hasGodAccessory) {
                    PARTICLE_COOLDOWNS.remove(uuid);
                    PARTICLE_TOGGLE.remove(uuid);
                    continue;
                }

                applyEffects(player);
                tickParticles(player, uuid);

            }

        });

    }


    private static void applyEffects(ServerPlayer player) {

        // Сила II
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.STRENGTH,
                        EFFECT_DURATION,
                        1,
                        false,
                        false,
                        true
                )
        );

        // Скорость II
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.SPEED,
                        EFFECT_DURATION,
                        1,
                        false,
                        false,
                        true
                )
        );

        // Регенерация II
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.REGENERATION,
                        EFFECT_DURATION,
                        1,
                        false,
                        false,
                        true
                )
        );

        // Сопротивление II
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.RESISTANCE,
                        EFFECT_DURATION,
                        1,
                        false,
                        false,
                        true
                )
        );

        // Невидимость
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.INVISIBILITY,
                        EFFECT_DURATION,
                        0,
                        false,
                        false,
                        true
                )
        );

    }


    private static void tickParticles(ServerPlayer player, UUID uuid) {

        int cooldown = PARTICLE_COOLDOWNS.getOrDefault(uuid, 0);

        if (cooldown > 0) {
            PARTICLE_COOLDOWNS.put(uuid, cooldown - 1);
            return;
        }

        PARTICLE_COOLDOWNS.put(uuid, PARTICLE_INTERVAL);

        boolean white = PARTICLE_TOGGLE.getOrDefault(uuid, false);
        PARTICLE_TOGGLE.put(uuid, !white);

        ServerLevel level = player.level();
        RandomSource random = player.getRandom();

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        double width = player.getBbWidth();
        double height = player.getBbHeight();

        int color = white ? 0xFFFFFF : 0x000000;

        DustParticleOptions dust =
                new DustParticleOptions(color, 1.2f);

        for (int i = 0; i < 4; i++) {

            double ox = (random.nextDouble() - 0.5) * (width + 0.4);
            double oy = random.nextDouble() * height;
            double oz = (random.nextDouble() - 0.5) * (width + 0.4);

            level.sendParticles(
                    dust,
                    x + ox,
                    y + oy,
                    z + oz,
                    1,
                    0.0, 0.02, 0.0,
                    0.0
            );

        }

    }

}
