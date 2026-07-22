package kub3s.eclipse.Init;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.PotionEffects.BleedingEffect;
import kub3s.eclipse.PotionEffects.FragilityEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {

    public static final Holder<MobEffect> BLEEDING =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "bleeding"), new BleedingEffect());
    public static final Holder<MobEffect> FRAGILITY =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
                    Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "fragility"), new FragilityEffect());

    public static void register() {}
}