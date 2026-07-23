package kub3s.eclipse.Accsessory.Item;

import kub3s.eclipse.Accsessory.System.AccessoryAttachment;
import kub3s.eclipse.Accsessory.System.AccessoryInventory;
import kub3s.eclipse.Init.ModItems;
import kub3s.eclipse.Player.Sanity.SanityAttachment;
import kub3s.eclipse.Player.Sanity.SanityData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CalmingAmuletManager {

    private static final double MOVE_THRESHOLD_SQ = 0.0025;

    private static final int STILL_TICKS_REQUIRED = 20 * 60 * 5;
    private static final int HEAL_INTERVAL_TICKS = 20 * 30;
    private static final int HEAL_AMOUNT = 5;

    private static final Map<UUID, Vec3> LAST_POS = new HashMap<>();
    private static final Map<UUID, Integer> STILL_TICKS = new HashMap<>();
    private static final Map<UUID, Integer> HEAL_COOLDOWN = new HashMap<>();

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                UUID uuid = player.getUUID();

                AccessoryInventory aks =
                        player.getAttachedOrCreate(
                                AccessoryAttachment.INVENTORY
                        );

                boolean hasAmulet = false;

                for (ItemStack stack : aks.getItems()) {

                    if (stack.getItem() == ModItems.CALMING_AMULET) {
                        hasAmulet = true;
                        break;
                    }

                }

                if (!hasAmulet) {
                    LAST_POS.remove(uuid);
                    STILL_TICKS.remove(uuid);
                    HEAL_COOLDOWN.remove(uuid);
                    continue;

                }

                Vec3 currentPos = player.position();
                Vec3 lastPos = LAST_POS.get(uuid);

                boolean moved =
                        lastPos == null
                                || lastPos.distanceToSqr(currentPos) > MOVE_THRESHOLD_SQ;

                if (moved) {
                    LAST_POS.put(uuid, currentPos);
                    STILL_TICKS.put(uuid, 0);
                    HEAL_COOLDOWN.put(uuid, 0);
                    continue;

                }

                int stillTicks =
                        STILL_TICKS.getOrDefault(uuid, 0) + 1;

                STILL_TICKS.put(uuid, stillTicks);

                if (stillTicks < STILL_TICKS_REQUIRED) {
                    continue;
                }

                int healCooldown =
                        HEAL_COOLDOWN.getOrDefault(uuid, 0);

                if (healCooldown > 0) {
                    HEAL_COOLDOWN.put(uuid, healCooldown - 1);
                    continue;
                }

                HEAL_COOLDOWN.put(uuid, HEAL_INTERVAL_TICKS);

                SanityData sanity =
                        player.getAttachedOrCreate(
                                SanityAttachment.SANITY
                        );

                player.setAttached(
                        SanityAttachment.SANITY,
                        sanity.add(HEAL_AMOUNT)
                );
            }
        });

    }

}
