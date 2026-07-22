package kub3s.eclipse.mixin;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Init.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class FragilityMixin {

    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
    private float increaseIncomingDamage(float amount, ServerLevel level, DamageSource damageSource) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(ModEffects.FRAGILITY)) {
            MobEffectInstance effectInstance = entity.getEffect(ModEffects.FRAGILITY);
            if (effectInstance != null) {
                int amplifier = effectInstance.getAmplifier();
                float bonusPercent = 0.20f * (amplifier + 1);

                float finalDamage = amount * (1.0f + bonusPercent);

                float fractionalPart = finalDamage - (int) finalDamage;

                if (fractionalPart >= 0.7f) {
                    finalDamage = (float) Math.ceil(finalDamage);
                } else {
                    finalDamage = (float) Math.floor(finalDamage);
                }

                return finalDamage;
            }
        }

        return amount;
    }

}
