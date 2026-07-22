package kub3s.eclipse.Item.Backpack;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BackpackContainer implements Container {
    private final BackpackInventory inventory;

    public BackpackContainer(BackpackInventory inventory) {
        this.inventory = inventory;
    }

    public int getContainerSize() {
        return 9;
    }

    public boolean isEmpty() {
        for(ItemStack stack : this.inventory.getItems()) {
            if (!stack.isEmpty()) {
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
        for(int i = 0; i < 9; ++i) {
            this.inventory.set(i, ItemStack.EMPTY);
        }

    }
}
