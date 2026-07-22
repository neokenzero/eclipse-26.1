package kub3s.eclipse.Init;

import net.minecraft.world.entity.MobCategory;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

public class ModBiomeModifiers {

    public static void register() {
        BiomeModifications.addSpawn(
                BiomeSelectors.all(),
                MobCategory.MONSTER,
                ModEntityTypes.RAT,
                10,
                1,
                4
        );
    }
}
