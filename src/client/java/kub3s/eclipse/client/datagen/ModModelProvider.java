package kub3s.eclipse.client.datagen;

import kub3s.eclipse.Init.ModItems;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;


public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(
                ModItems.ECLIPSE_REWARD,
                ModelTemplates.FLAT_ITEM);

        //itemModelGenerators.generateFlatItem(
                //ModItems.BACKPACK,
                //ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(
                ModItems.MANGO,
                ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(
                ModItems.MIRROR,
                ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

}
