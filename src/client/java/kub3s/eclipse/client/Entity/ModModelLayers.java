package kub3s.eclipse.client.Entity;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Init.ModEntityTypes;
import kub3s.eclipse.client.Entity.Rat.RatModel;
import kub3s.eclipse.client.Entity.Rat.RatRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayers {

    public static final ModelLayerLocation RAT = createMain("rat");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath((Eclipse.MOD_ID), name), "main");
    }

    public static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(ModModelLayers.RAT, RatModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntityTypes.RAT, RatRenderer::new);
    }
}
