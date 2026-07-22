package kub3s.eclipse.Item.Backpack;

import kub3s.eclipse.Init.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackScreenHandler extends AbstractContainerMenu {
    private final BackpackContainer container;

    public BackpackScreenHandler(int syncId, Inventory inventory) {
        this(syncId, inventory, new BackpackContainer((BackpackInventory)inventory.player.getAttachedOrCreate(BackpackAttachment.BACKPACK)));
    }

    public BackpackScreenHandler(int syncId, Inventory inventory, BackpackContainer container) {
        super(ModMenuTypes.BACKPACK_MENU, syncId);
        this.container = container;

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(container, i, 8 + i * 18, 18));
        }

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 50 + y * 18));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 108));
        }

    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < 9) {
                this.moveItemStackTo(stack, 9, this.slots.size(), true);
            } else {
                this.moveItemStackTo(stack, 0, 9, false);
            }
        }

        return result;
    }

    public boolean stillValid(Player player) {
        return true;
    }
}
