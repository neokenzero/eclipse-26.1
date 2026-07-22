package kub3s.eclipse.Player.Sanity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class SanityData {
    public static final int MAX_SANITY = 100;

    public static final Codec<SanityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("sanity").forGetter(SanityData::getSanity)
    ).apply(instance, SanityData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SanityData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SanityData::getSanity,
            SanityData::new
    );

    private final int sanity;

    public SanityData() {
        this(100);
    }

    public SanityData(int sanity) {
        this.sanity = Math.clamp(sanity, 0, 100);
    }

    public int getSanity() {
        return this.sanity;
    }

    public SanityData add(int amount) {
        return new SanityData(this.sanity + amount);
    }

    public SanityData remove(int amount) {
        return new SanityData(this.sanity - amount);
    }

    public SanityData set(int value) {
        return new SanityData(value);
    }
}
