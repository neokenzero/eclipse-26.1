package kub3s.eclipse.client.Screens;

import kub3s.eclipse.Accsessory.System.AccessoryMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AccessoryScreen extends AbstractContainerScreen<AccessoryMenu> {

    public AccessoryScreen(
            AccessoryMenu menu,
            Inventory inventory,
            Component title
    ) {

        super(menu, inventory, title, 176, 166);

    }


    @Override
    public void extractContents(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {

        super.extractContents(graphics, mouseX, mouseY, delta);


        graphics.text(
                this.font,
                Component.translatable("gui.eclipse.accessory"),
                8,
                20,
                0xffffff,
                false
        );

    }
}
