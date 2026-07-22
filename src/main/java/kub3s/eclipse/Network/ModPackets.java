package kub3s.eclipse.Network;

import kub3s.eclipse.Eclipse;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class ModPackets {

    public static final CustomPacketPayload.Type<OpenAksPayload> OPEN_ACCESSORY =
            new CustomPacketPayload.Type<>(
                    Identifier.fromNamespaceAndPath(
                            Eclipse.MOD_ID,
                            "open_accessory"
                    )
            );

    public static void sendOpenAks(){
        ClientPlayNetworking.send(
                new OpenAksPayload()
        );

    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                OPEN_ACCESSORY,
                (payload, context) -> {

                    context.player().level().getServer().execute(() -> {
                        OpenAccessory.open(
                                context.player()
                        );

                    });

                }
        );

    }


}