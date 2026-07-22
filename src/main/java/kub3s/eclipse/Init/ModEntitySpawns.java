package kub3s.eclipse.Init;

import kub3s.eclipse.Boss.Ent.EntSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntitySpawns {

    public static void register() {
        EntSpawn.register();

        SpawnPlacements.register(
                ModEntityTypes.RAT,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModEntitySpawns::checkRatSpawnRules
        );
    }

    // Rat

    private static boolean checkRatSpawnRules(
            net.minecraft.world.entity.EntityType<? extends Mob> type,
            LevelAccessor level,
            EntitySpawnReason spawnReason,
            BlockPos pos,
            RandomSource random
    ) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        if (blockLight >= 9) {
            return false;
        }

        if (level.canSeeSky(pos)) {
            return false;
        }

        return Mob.checkMobSpawnRules(type, level, spawnReason, pos, random);
    }
}
