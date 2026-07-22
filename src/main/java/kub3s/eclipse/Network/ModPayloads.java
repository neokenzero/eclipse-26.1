package kub3s.eclipse.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloads {


    public static void register() {


        PayloadTypeRegistry.serverboundPlay()
                .register(
                        OpenAksPayload.TYPE,
                        OpenAksPayload.CODEC
                );


    }

}