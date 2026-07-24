package kub3s.eclipse.Init;

import kub3s.eclipse.Accsessory.System.AccessoryMenu;
import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Item.Backpack.BackpackScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType BACKPACK_MENU;

    public static MenuType<AccessoryMenu> ACCESSORY_MENU;

    public ModMenuTypes() {
    }

    public static void register() {
        Eclipse.LOGGER.info("Registering menus: eclipse");
    }

    static {
        BACKPACK_MENU = (MenuType) Registry.register(BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "backpack"),
                new MenuType(BackpackScreenHandler::new, FeatureFlags.DEFAULT_FLAGS));

        ACCESSORY_MENU = Registry.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(
                        Eclipse.MOD_ID,
                        "accessory"
                ),
                new MenuType<>(
                        AccessoryMenu::new,
                        net.minecraft.world.flag.FeatureFlags.VANILLA_SET
                )
        );
    }
}
