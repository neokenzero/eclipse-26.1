package kub3s.eclipse.client.Entity.Werewolf;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Entity.WerewolfEntity;
import kub3s.eclipse.client.Entity.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class WerewolfRenderer extends MobRenderer<WerewolfEntity, WerewolfRenderState, WerewolfModel> {
    private static final Identifier WEREWOLF_TEXTURE = Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "textures/entity/werewolf.png");

    public WerewolfRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfModel(context.bakeLayer(ModModelLayers.WEREWOLF)), 0.4F);
    }

    @Override
    public WerewolfRenderState createRenderState() {
        return new WerewolfRenderState();
    }

    @Override
    public void extractRenderState(WerewolfEntity entity, WerewolfRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkAnimationState.copyFrom(entity.walkAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
    }


    @Override
    public Identifier getTextureLocation(WerewolfRenderState state) {
        return WEREWOLF_TEXTURE;
    }
}