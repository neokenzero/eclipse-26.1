package kub3s.eclipse.Accsessory.System;

import kub3s.eclipse.Init.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AccessoryMenu extends AbstractContainerMenu {
    private final AccessoryContainer aksContainer;

    public AccessoryMenu(int id, Inventory playerInventory) {
        super(ModMenuTypes.ACCESSORY_MENU, id);
        AccessoryInventory aks = (AccessoryInventory)playerInventory.player.getAttachedOrCreate(AccessoryAttachment.INVENTORY);
        this.aksContainer = new AccessoryContainer(aks);

        for(int i = 0; i < aks.size(); ++i) {
            this.addSlot(new AccessorySlot(this.aksContainer, i, 8 + i * 18, 20));
        }

        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 60 + row * 18));
            }
        }

        for(int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 118));
        }

    }

    public boolean stillValid(Player player) {
        return true;
    }

    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
