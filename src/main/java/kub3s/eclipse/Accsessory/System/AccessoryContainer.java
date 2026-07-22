package kub3s.eclipse.Accsessory.System;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AccessoryContainer implements Container {
    private final AccessoryInventory inventory;

    public AccessoryContainer(AccessoryInventory inventory) {
        this.inventory = inventory;
    }

    public int getContainerSize() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        for(int i = 0; i < this.inventory.size(); ++i) {
            if (!this.inventory.get(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = this.inventory.get(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack result = stack.split(amount);
            this.setChanged();
            return result;
        }
    }

    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.inventory.get(slot);
        this.inventory.set(slot, ItemStack.EMPTY);
        return stack;
    }

    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player player) {
        return true;
    }

    public void clearContent() {
        for(int i = 0; i < this.inventory.size(); ++i) {
            this.inventory.set(i, ItemStack.EMPTY);
        }

    }
}
