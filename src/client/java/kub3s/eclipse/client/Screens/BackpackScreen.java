package kub3s.eclipse.client.Screens;

import kub3s.eclipse.Item.Backpack.BackpackScreenHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BackpackScreen extends AbstractContainerScreen<BackpackScreenHandler> {
    public BackpackScreen(BackpackScreenHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
    }

    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractContents(graphics, mouseX, mouseY, delta);
        graphics.text(this.font, Component.literal("Рюкзак"), 8, 20, 16777215, false);
    }
}
