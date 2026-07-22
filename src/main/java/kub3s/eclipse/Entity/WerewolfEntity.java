package kub3s.eclipse.Entity;

import kub3s.eclipse.Entity.Goals.DashGoal;
import kub3s.eclipse.Init.ModEntityTypes;
import kub3s.eclipse.Init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WerewolfEntity extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public WerewolfEntity(EntityType<? extends Monster> type, Level level) {
        super(ModEntityTypes.WEREWOLF, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1.25));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(1, new DashGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 20)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    private boolean isSunBurnTick() {
        if (!this.level().isClientSide() && (Boolean)this.level().environmentAttributes().getValue(EnvironmentAttributes.MONSTERS_BURN, this.position())) {
            float br = this.getLightLevelDependentMagicValue();
            BlockPos roundedPos = BlockPos.containing(this.getX(), this.getEyeY(), this.getZ());
            boolean isInNonBurnableBlock = this.isInWaterOrRain() || this.isInPowderSnow || this.wasInPowderSnow;
            if (br > 0.5F && this.random.nextFloat() * 30.0F < (br - 0.4F) * 2.0F && !isInNonBurnableBlock && this.level().canSeeSky(roundedPos)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean attacked = super.doHurtTarget(level, target);

        if (attacked && target instanceof LivingEntity living && random.nextDouble() < 0.1) {
            living.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 100));
        }

        return attacked;
    }

    @Override
    public void aiStep() {
        if (this.isAlive() && this.isSunBurnTick() && !this.level().isClientSide()) {
            this.setRemainingFireTicks(160);
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            if (!this.walkAnimationState.isStarted()) {
                this.idleAnimationState.startIfStopped(this.tickCount);
            }

            if (this.walkAnimation.isMoving()) {
                this.idleAnimationState.stop();
                this.walkAnimationState.startIfStopped(this.tickCount);
            } else {
                this.walkAnimationState.stop();
            }

            this.attackAnimationState.animateWhen(this.swinging, swingTime);
        }
    }

    @Override
    public void swing(InteractionHand hand) {
        super.swing(hand);

        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)4);
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(status);
        }
    }

    /*@Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MobSounds.RAT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return MobSounds.RAT_HURT;
    }

    public void playAttackSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                MobSounds.RAT_ATTACK, SoundSource.HOSTILE, 0.5F, this.getVoicePitch());
    }*/

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }
}
