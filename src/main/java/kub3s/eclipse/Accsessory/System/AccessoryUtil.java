package kub3s.eclipse.Accsessory.System;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoryUtil {

    public AccessoryUtil() {
    }

    public static boolean hasAccessory(Player player, Item item) {
        AccessoryInventory aks = (AccessoryInventory)player.getAttached(AccessoryAttachment.INVENTORY);
        if (aks == null) {
            return false;
        } else {
            for(ItemStack stack : aks.getItems()) {
                if (stack.is(item)) {
                    return true;
                }
            }

            return false;
        }
    }
}
