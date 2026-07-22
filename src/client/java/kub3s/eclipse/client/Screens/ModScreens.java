package kub3s.eclipse.client.Screens;

import kub3s.eclipse.Init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens {

    public ModScreens() {
    }

    public static void register() {
        MenuScreens.register(ModMenuTypes.ACCESSORY_MENU, AccessoryScreen::new);
        MenuScreens.register(ModMenuTypes.BACKPACK_MENU, BackpackScreen::new);
    }
}
