package kub3s.eclipse.client.Entity.Rat;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Entity.RatEntity;
import kub3s.eclipse.client.Entity.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class RatRenderer extends MobRenderer<RatEntity, LivingEntityRenderState, RatModel> {

    private static final Identifier RAT_TEXTURE = Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "textures/entity/rat.png");

    public RatRenderer(EntityRendererProvider.Context context) {
        super(context, new RatModel(context.bakeLayer(ModModelLayers.RAT)), 0.3F);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return RAT_TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}