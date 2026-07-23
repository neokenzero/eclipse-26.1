package kub3s.eclipse.Item.Backpack;

import kub3s.eclipse.Init.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackMenu extends AbstractContainerMenu {

    private final BackpackInventory backpackInventory;

    public BackpackMenu(int syncId, Inventory playerInventory, BackpackInventory backpackInventory) {
        super(ModMenuTypes.BACKPACK_MENU, syncId);

        this.backpackInventory = backpackInventory;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(new BackpackContainer(backpackInventory), i, 8 + i * 18, 20));
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();

            result = stack.copy();

            if (index < 9) {
                if (!moveItemStackTo(stack, 9, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

            } else {
                if (!moveItemStackTo(stack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }

            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;

    }
}
