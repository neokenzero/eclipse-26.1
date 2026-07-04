package kub3s.eclipse.Init;

import kub3s.eclipse.Eclipse;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item ECLIPSE_REWARD = registerItem("eclipse_reward", properties -> new Item(properties
            .fireResistant()
    ));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name)))));
    }

    public static void registerModItems() {
        Eclipse.LOGGER.info("Registered Mod Items for " + Eclipse.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(ECLIPSE_REWARD);
        });
    }
}
