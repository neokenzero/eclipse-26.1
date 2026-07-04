package kub3s.eclipse.Entity;

import kub3s.eclipse.Entity.Goals.HitAndRunGoal;
import kub3s.eclipse.Init.MobSounds;
import kub3s.eclipse.Init.ModEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RatEntity extends PathfinderMob {

    public RatEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(ModEntityTypes.RAT, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1.25));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Cat.class, 10, 1.5, 1.5));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(2, new HitAndRunGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Nullable
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
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }
}
