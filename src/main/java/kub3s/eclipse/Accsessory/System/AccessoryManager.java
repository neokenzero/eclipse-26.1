package kub3s.eclipse.Accsessory.System;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

public class AccessoryManager {

    public AccessoryManager() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((ServerTickEvents.EndTick)(server) -> {
            for(ServerPlayer player : server.getPlayerList().getPlayers()) {
                AccessoryInventory aks = (AccessoryInventory)player.getAttachedOrCreate(AccessoryAttachment.INVENTORY);
                boolean nightGo = false;

                /*for(ItemStack stack : aks.getItems()) {
                    if (stack.getItem() == ModNightGo.NIGHT_GO) {
                        nightGo = true;
                        break;
                    }
                }

                if (nightGo) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));
                }*/
            }

        });
    }
}