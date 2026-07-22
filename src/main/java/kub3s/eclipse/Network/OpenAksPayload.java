package kub3s.eclipse.Network;

import kub3s.eclipse.Eclipse;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenAksPayload() implements CustomPacketPayload {

    public static final Type<OpenAksPayload> TYPE =
            new Type<>(
                    Identifier.fromNamespaceAndPath(
                            Eclipse.MOD_ID,
                            "open_accessory"
                    )
            );


    public static final StreamCodec<RegistryFriendlyByteBuf, OpenAksPayload> CODEC =
            StreamCodec.unit(
                    new OpenAksPayload()
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {

        return TYPE;

    }

}