package kub3s.eclipse.Init;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Item.FireStaff;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class ModItems {


    // Material
    public static final Item ECLIPSE_REWARD = registerItem("eclipse_reward", properties -> new Item(properties
            .fireResistant()));
    public static final Item SUN_REWARD = registerItem("sun_reward", properties -> new Item(properties
            .fireResistant()));
    public static final Item SUN_SHARD = registerItem("sun_shard", properties -> new Item(properties
            .fireResistant()));
    public static final Item LUNAR_REWARD = registerItem("lunar_reward", properties -> new Item(properties
            .fireResistant()));
    public static final Item ASH = registerItem("ash", Item::new);
    public static final Item ASH_BOUND_SOUL = registerItem("ash_bound_soul", Item::new);
    public static final Item BLOOD_FLASK = registerItem("blood_flask", Item::new);
    public static final Item FLASK = registerItem("flask", Item::new);
    public static final Item BOUND_SOUL = registerItem("bound_soul", Item::new);
    public static final Item CORE_OF_FLIGHT = registerItem("core_of_flight", Item::new);
    public static final Item DAWN_REMAINS = registerItem("dawn_remains", Item::new);
    public static final Item IRON_RING = registerItem("iron_ring", Item::new);
    public static final Item GOLD_RING = registerItem("gold_ring", Item::new);
    public static final Item DIAMOND_RING = registerItem("diamond_ring", Item::new);
    public static final Item IRON_AMULET = registerItem("iron_amulet", Item::new);
    public static final Item GOLD_AMULET = registerItem("gold_amulet", Item::new);
    public static final Item DIAMOND_AMULET = registerItem("diamond_amulet", Item::new);
    public static final Item LENS = registerItem("lens", Item::new);
    public static final Item ECTOPLASM = registerItem("ectoplasm", Item::new);
    public static final Item GLITCH_SHARD = registerItem("glitch_shard", Item::new);
    public static final Item GLITCH_SOUL = registerItem("glitch_soul", Item::new);
    public static final Item NATURE_SOUL = registerItem("nature_soul", Item::new);
    public static final Item FLIGHT_SOUL = registerItem("flight_soul", Item::new);
    public static final Item REBIRTH_ELEMENT = registerItem("rebirth_element", Item::new);
    public static final Item STEEL = registerItem("steel", Item::new);
    public static final Item OBSIDIAN_STEEL = registerItem("obsidian_steel", Item::new);

    // Usable
    public static final Item MIRROR = registerItem("mirror", properties -> new Item(properties
            .stacksTo(1)
            .fireResistant()));
    //public static final Item BACKPACK = registerItem("backpack", properties -> new Backpack(properties
    //        .stacksTo(1)));

    public static final Item COIN = registerItem("coin", Item::new);

    // Instrument

    // Weapon
    public static final Item FIRE_STAFF = registerItem("fire_staff", properties -> new FireStaff(properties
            .stacksTo(1)
            .fireResistant()));

    // Armor

    // Food
    public static final Item MANGO = registerItem("mango", properties -> new Item(properties
            .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build())));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name)))));
    }

    // TRADABLE
    public static final Item ACTIVE_ECTOPLASM = registerItem("active_ecliplasm", Item::new);

    // CREATIVE TABS

    public static final ResourceKey<CreativeModeTab> MOD_ITEMS = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "mod_items_tab")
    );
    public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.ECLIPSE_REWARD)) //TODO: потом заменить на чё нить нормальное
            .title(Component.translatable("creativeTab.eclipse"))
            .displayItems((params, output) -> {
               output.accept(COIN);
               output.accept(ACTIVE_ECTOPLASM);
            })
            .build();

    public static void registerModItems() {
        Eclipse.LOGGER.info("Registered Mod Items for " + Eclipse.MOD_ID);

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ITEMS, CUSTOM_CREATIVE_TAB);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(ECLIPSE_REWARD);
            output.accept(LUNAR_REWARD);
            output.accept(SUN_REWARD);
            output.accept(SUN_SHARD);
            output.accept(ASH);
            output.accept(ASH_BOUND_SOUL);
            output.accept(BOUND_SOUL);
            output.accept(FLASK);
            output.accept(BLOOD_FLASK);
            output.accept(CORE_OF_FLIGHT);
            output.accept(DAWN_REMAINS);
            output.accept(IRON_RING);
            output.accept(GOLD_RING);
            output.accept(DIAMOND_RING);
            output.accept(IRON_AMULET);
            output.accept(GOLD_AMULET);
            output.accept(DIAMOND_AMULET);
            output.accept(LENS);
            output.accept(ECTOPLASM);
            output.accept(GLITCH_SHARD);
            output.accept(GLITCH_SOUL);
            output.accept(NATURE_SOUL);
            output.accept(FLIGHT_SOUL);
            output.accept(REBIRTH_ELEMENT);
            output.accept(STEEL);
            output.accept(OBSIDIAN_STEEL);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> {
            output.accept(MANGO);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> {
            output.accept(FIRE_STAFF);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            //output.accept(BACKPACK);
        });
    }
}
