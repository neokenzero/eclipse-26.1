package kub3s.eclipse.Data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.resources.Identifier;
import java.util.HashMap;
import java.util.Map;

public class FactionSpawnData extends SavedData {

    private final Map<String, BlockPos> factionSpawns = new HashMap<>();

    public FactionSpawnData() {}

    public FactionSpawnData(Map<String, BlockPos> factionSpawns) {
        this.factionSpawns.putAll(factionSpawns);
    }

    public static final Codec<FactionSpawnData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.unboundedMap(
                                    Codec.STRING,
                                    BlockPos.CODEC
                            ).fieldOf("faction_spawns")
                            .forGetter(d -> d.factionSpawns)
            ).apply(instance, FactionSpawnData::new));


    public static final SavedDataType<FactionSpawnData> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath("eclipse", "faction_spawn_data"),
                    FactionSpawnData::new,
                    CODEC,
                    null
            );

    public BlockPos getPos(String faction) {
        return factionSpawns.get(faction.toLowerCase());
    }

    public void setPos(String faction, BlockPos pos) {
        factionSpawns.put(faction.toLowerCase(), pos);
        setDirty();
    }

    public static FactionSpawnData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}