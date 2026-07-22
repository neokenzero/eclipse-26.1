package kub3s.eclipse.client.Entity.Werewolf;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WerewolfModel extends EntityModel<WerewolfRenderState> {

    public final ModelPart leg1;
    public final ModelPart leg2;
    public final ModelPart lowbody;
    public final ModelPart bodyhair;
    public final ModelPart body;
    public final ModelPart hands;
    public final ModelPart lowjaw;
    public final ModelPart jaw;
    public final ModelPart head;
    public final ModelPart face;
    public final ModelPart ears;
    public final ModelPart tail;
    public final ModelPart nose;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation attackAnimation;

    public WerewolfModel(ModelPart root) {
        super(root);
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.lowbody = root.getChild("lowbody");
        this.bodyhair = root.getChild("bodyhair");
        this.body = root.getChild("body");
        this.hands = root.getChild("hands");
        this.lowjaw = root.getChild("lowjaw");
        this.jaw = root.getChild("jaw");
        this.head = root.getChild("head");
        this.face = this.head.getChild("face");
        this.ears = root.getChild("ears");
        this.tail = root.getChild("tail");
        this.nose = root.getChild("nose");

        this.idleAnimation = WerewolfAnim.IDLE.bake(root);
        this.walkAnimation = WerewolfAnim.WALK.bake(root);
        this.attackAnimation = WerewolfAnim.ATTACK.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        CubeDeformation dilation = CubeDeformation.NONE;

        PartDefinition leg1 = modelPartData.addOrReplaceChild("leg1", CubeListBuilder.create()
                        .texOffs(20, 27).addBox(-1.5F, 8.0F, -2.0F, 3.0F, 2.0F, 5.0F, dilation)
                        .texOffs(34, 52).addBox(1.5F, 8.0F, 0.0F, 1.0F, 2.0F, 3.0F, dilation)
                        .texOffs(8, 59).addBox(0.5F, 9.0F, -3.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(58, 57).addBox(-1.5F, 9.0F, -3.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(12, 59).addBox(1.5F, 9.0F, -1.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(44, 17).addBox(-1.5F, 4.0F, 0.0F, 3.0F, 4.0F, 3.0F, dilation)
                        .texOffs(30, 45).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 3.0F, dilation),
                PartPose.offset(2.5F, 14.0F, -2.0F));

        PartDefinition leg2 = modelPartData.addOrReplaceChild("leg2", CubeListBuilder.create()
                        .texOffs(28, 17).addBox(-1.75F, 8.0F, -3.0F, 3.0F, 2.0F, 5.0F, dilation)
                        .texOffs(42, 52).addBox(-2.75F, 8.0F, -1.0F, 1.0F, 2.0F, 3.0F, dilation)
                        .texOffs(20, 53).addBox(0.25F, 9.0F, -4.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(58, 55).addBox(-1.75F, 9.0F, -4.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(26, 59).addBox(-2.75F, 9.0F, -2.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(0, 44).addBox(-1.75F, 4.0F, -1.0F, 3.0F, 4.0F, 3.0F, dilation)
                        .texOffs(42, 45).addBox(-1.75F, 0.0F, -2.0F, 3.0F, 4.0F, 3.0F, dilation),
                PartPose.offset(-2.25F, 14.0F, -1.0F));

        PartDefinition lowbody = modelPartData.addOrReplaceChild("lowbody", CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-5.0F, -13.0F, -3.0F, 10.0F, 3.0F, 4.0F, dilation)
                        .texOffs(46, 31).addBox(-3.0F, -13.0F, 1.0F, 6.0F, 3.0F, 1.0F, dilation)
                        .texOffs(46, 41).addBox(-3.0F, -10.0F, 0.0F, 6.0F, 1.0F, 1.0F, dilation)
                        .texOffs(50, 27).addBox(-1.0F, -10.0F, -3.0F, 2.0F, 1.0F, 3.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bodyhair = modelPartData.addOrReplaceChild("bodyhair", CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 4.0F, 1.0F, dilation)
                        .texOffs(33, 61).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 2.0F, 1.0F, dilation)
                        .texOffs(32, 5).addBox(-3.0F, -10.0F, -3.0F, 6.0F, 2.0F, 1.0F, dilation)
                        .texOffs(10, 53).addBox(-2.0F, -6.0F, -3.0F, 4.0F, 1.0F, 1.0F, dilation)
                        .texOffs(44, 57).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 1.0F, 1.0F, dilation)
                        .texOffs(46, 39).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 1.0F, 1.0F, dilation)
                        .texOffs(38, 57).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 1.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 11.0F, -2.0F));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 8).addBox(-4.0F, -21.0F, -3.0F, 8.0F, 8.0F, 4.0F, dilation)
                        .texOffs(0, 0).addBox(-5.0F, -23.0F, -4.0F, 10.0F, 2.0F, 6.0F, dilation)
                        .texOffs(36, 31).addBox(-2.0F, -18.0F, 1.0F, 4.0F, 2.0F, 1.0F, dilation)
                        .texOffs(24, 46).addBox(-1.0F, -16.0F, 1.0F, 2.0F, 2.0F, 1.0F, dilation)
                        .texOffs(46, 12).addBox(-3.0F, -21.0F, 1.0F, 6.0F, 3.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hands = modelPartData.addOrReplaceChild("hands", CubeListBuilder.create()
                        .texOffs(46, 5).addBox(5.0F, -18.0F, -2.0F, 3.0F, 4.0F, 3.0F, dilation)
                        .texOffs(36, 24).addBox(5.0F, -16.0F, -6.0F, 3.0F, 3.0F, 4.0F, dilation)
                        .texOffs(38, 59).addBox(7.0F, -14.0F, -7.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(50, 59).addBox(-8.0F, -14.0F, -7.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(46, 59).addBox(-6.0F, -14.0F, -7.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(42, 59).addBox(5.0F, -14.0F, -7.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(0, 37).addBox(-8.0F, -16.0F, -6.0F, 3.0F, 3.0F, 4.0F, dilation)
                        .texOffs(12, 46).addBox(-8.0F, -18.0F, -2.0F, 3.0F, 4.0F, 3.0F, dilation)
                        .texOffs(32, 34).addBox(-7.0F, -21.0F, -3.0F, 3.0F, 3.0F, 4.0F, dilation)
                        .texOffs(18, 34).addBox(4.0F, -21.0F, -3.0F, 3.0F, 3.0F, 4.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lowjaw = modelPartData.addOrReplaceChild("lowjaw", CubeListBuilder.create()
                        .texOffs(0, 27).addBox(-3.0F, -25.0F, -6.0F, 6.0F, 2.0F, 4.0F, dilation)
                        .texOffs(28, 24).addBox(-1.0F, -25.0F, -9.0F, 2.0F, 1.0F, 2.0F, dilation)
                        .texOffs(50, 2).addBox(-2.0F, -24.0F, -8.0F, 4.0F, 1.0F, 2.0F, dilation)
                        .texOffs(54, 45).addBox(-2.0F, -25.0F, -7.0F, 4.0F, 1.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition jaw = modelPartData.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(0, 33).addBox(-3.0F, -27.0F, -6.0F, 6.0F, 1.0F, 3.0F, dilation)
                        .texOffs(24, 52).addBox(-1.0F, -28.0F, -6.0F, 2.0F, 1.0F, 3.0F, dilation)
                        .texOffs(50, 24).addBox(-2.0F, -27.0F, -9.0F, 4.0F, 1.0F, 2.0F, dilation)
                        .texOffs(50, 0).addBox(-3.0F, -27.0F, -7.0F, 6.0F, 1.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(30, 41).addBox(-3.0F, -25.0F, -2.0F, 6.0F, 2.0F, 2.0F, dilation)
                        .texOffs(24, 8).addBox(-3.0F, -29.0F, -3.0F, 6.0F, 4.0F, 5.0F, dilation)
                        .texOffs(14, 41).addBox(-2.0F, -30.0F, -3.0F, 4.0F, 1.0F, 4.0F, dilation)
                        .texOffs(46, 43).addBox(-3.0F, -30.0F, -4.0F, 6.0F, 1.0F, 1.0F, dilation)
                        .texOffs(24, 49).addBox(-1.0F, -29.0F, -4.0F, 2.0F, 1.0F, 1.0F, dilation)
                        .texOffs(50, 52).addBox(-2.0F, -25.0F, 0.0F, 4.0F, 2.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create()
                        .texOffs(55, 48).addBox(-3.0F, -29.0F, -4.0F, 2.0F, 2.0F, 1.0F, dilation)
                        .texOffs(24, 17).addBox(-3.0F, -26.0F, -8.0F, 1.0F, 2.0F, 1.0F, dilation)
                        .texOffs(14, 37).addBox(2.0F, -26.0F, -8.0F, 1.0F, 2.0F, 1.0F, dilation)
                        .texOffs(46, 35).addBox(-2.0F, -26.0F, -6.0F, 4.0F, 1.0F, 3.0F, dilation)
                        .texOffs(11, 56).addBox(1.0F, -29.0F, -4.0F, 2.0F, 2.0F, 1.0F, dilation),
                PartPose.ZERO);

        PartDefinition ears = modelPartData.addOrReplaceChild("ears", CubeListBuilder.create()
                        .texOffs(50, 55).addBox(-6.0F, -6.0F, 5.0F, 2.0F, 2.0F, 2.0F, dilation)
                        .texOffs(26, 56).addBox(-7.0F, -8.0F, 6.0F, 2.0F, 2.0F, 1.0F, dilation)
                        .texOffs(32, 57).addBox(1.0F, -8.0F, 6.0F, 2.0F, 2.0F, 1.0F, dilation)
                        .texOffs(58, 5).addBox(-7.0F, -8.0F, 5.0F, 2.0F, 1.0F, 1.0F, dilation)
                        .texOffs(58, 7).addBox(1.0F, -8.0F, 5.0F, 2.0F, 1.0F, 1.0F, dilation)
                        .texOffs(58, 59).addBox(1.0F, -9.0F, 5.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(0, 60).addBox(-6.0F, -9.0F, 5.0F, 1.0F, 1.0F, 1.0F, dilation)
                        .texOffs(0, 56).addBox(0.0F, -6.0F, 5.0F, 2.0F, 2.0F, 2.0F, dilation),
                PartPose.offset(2.0F, -1.0F, -7.0F));

        PartDefinition tail = modelPartData.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(56, 16).addBox(-1.0F, -12.0F, 2.0F, 2.0F, 2.0F, 2.0F, dilation)
                        .texOffs(58, 9).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 1.0F, 1.0F, dilation)
                        .texOffs(0, 51).addBox(-1.0F, -10.0F, 3.0F, 3.0F, 3.0F, 2.0F, dilation)
                        .texOffs(18, 56).addBox(0.0F, -7.0F, 4.0F, 2.0F, 2.0F, 2.0F, dilation)
                        .texOffs(56, 20).addBox(0.0F, -5.0F, 3.0F, 2.0F, 2.0F, 2.0F, dilation)
                        .texOffs(54, 59).addBox(0.0F, -3.0F, 3.0F, 1.0F, 1.0F, 1.0F, dilation),
                PartPose.offset(0.0F, 22.0F, 0.0F));


        PartDefinition nose = modelPartData.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(16, 60).addBox(-1.0F, -28.0F, -10.0F, 2.0F, 2.0F, 2.0F, dilation),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(WerewolfRenderState state) {
        super.setupAnim(state);

        this.root.getAllParts().forEach(ModelPart::resetPose);

        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);

        this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks);

        float limbSwingAmplitude = state.walkAnimationSpeed;
        float limbSwingAnimationProgress = state.walkAnimationPos;
        this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
        this.leg1.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
    }
}
