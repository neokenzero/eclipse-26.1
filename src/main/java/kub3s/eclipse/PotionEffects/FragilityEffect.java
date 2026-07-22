package kub3s.eclipse.PotionEffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.Random;

public class FragilityEffect extends MobEffect {

    Random random = new Random();

    public FragilityEffect() {
        super(MobEffectCategory.HARMFUL, 0x4a0075);
    }
}