//
//package kub3s.eclipse.Accsessory.Item;
//
//import eclipsemod.eclipse.item.regenstone.ModRegenStone;
//import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
//import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//
//public class RegenStoneManager {
//
//    private static final long COOLDOWN_MS = 5 * 60 * 1000L; // 5 минут
//
//
//    public static void register() {
//
//
//        ServerLivingEntityEvents.AFTER_DAMAGE.register(
//                (entity, source, baseDamageTaken, damageTaken, blocked) -> {
//
//                    if (!(entity instanceof ServerPlayer victim))
//                        return;
//
//                    if (!(source.getEntity() instanceof Player attacker))
//                        return;
//
//                    if (attacker == victim)
//                        return;
//
//                    victim.setAttached(
//                            RegenStoneAttachment.LAST_HIT_TIME,
//                            System.currentTimeMillis()
//                    );
//
//                }
//        );
//
//
//
//        ServerTickEvents.END_SERVER_TICK.register(server -> {
//
//            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
//
//                AksInventory aks =
//                        player.getAttachedOrCreate(
//                                AksAttachment.INVENTORY
//                        );
//
//                boolean hasStone = false;
//
//                for (ItemStack stack : aks.getItems()) {
//
//                    if (stack.getItem() == ModRegenStone.REGEN_STONE) {
//                        hasStone = true;
//                        break;
//                    }
//
//                }
//
//                if (!hasStone)
//                    continue;
//
//                long lastHit =
//                        player.getAttachedOrCreate(
//                                RegenStoneAttachment.LAST_HIT_TIME
//                        );
//
//                long now = System.currentTimeMillis();
//
//                if (now - lastHit >= COOLDOWN_MS) {
//
//                    player.addEffect(
//                            new MobEffectInstance(
//                                    MobEffects.REGENERATION,
//                                    220,
//                                    0,
//                                    false,
//                                    false,
//                                    true
//                            )
//                    );
//
//                }
//
//            }
//
//        });
//
//    }
//
//}