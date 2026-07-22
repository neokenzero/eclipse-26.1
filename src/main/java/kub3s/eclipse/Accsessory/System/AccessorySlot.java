package kub3s.eclipse.Accsessory.System;

import kub3s.eclipse.Accsessory.Item.AccessoryItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AccessorySlot extends Slot {

    public AccessorySlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof AccessoryItem;
    }

    public boolean mayPickup(Player player) {
        return true;
    }
}
