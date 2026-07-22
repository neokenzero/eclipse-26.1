package kub3s.eclipse.client.datagen;

import kub3s.eclipse.Init.ModEntityTypes;
import kub3s.eclipse.Init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class EntityLootTableProvider extends FabricEntityLootSubProvider {

    public EntityLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        this.add(ModEntityTypes.RAT, LootTable.lootTable()
                .withPool(LootPool.lootPool() // One pool
                        .setRolls(ConstantValue.exactly(2.0f)) // That has two rolls
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)) // With an entry that has diamond(s)
                        .add(LootItem.lootTableItem(Items.RABBIT_HIDE)) // With an entry that has a plain diamond sword
                ));
    }
}
