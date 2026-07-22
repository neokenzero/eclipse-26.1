package kub3s.eclipse.PotionEffects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class BleedingEffect extends MobEffect {

    Random random = new Random();

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0x790C0C);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (level.getRandom().nextInt(80) == 0) {
            entity.hurt(level.damageSources().magic(), 2.0F + amplifier);
        }

        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}