package kub3s.eclipse.client.Entity.Rat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class RatModel extends EntityModel<LivingEntityRenderState> {

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;

    public RatModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-2.0F, -3.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7).addBox(-3.0F, -2.0F, 2.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-2.0F, -2.0F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        partDefinition.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(12, 7).addBox(-1.0F, -1.0F, -6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 10.0F));

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 2).addBox(-4.0F, -3.0F, 4.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(1.0F, -3.0F, 4.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 10).addBox(-2.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
    }
}