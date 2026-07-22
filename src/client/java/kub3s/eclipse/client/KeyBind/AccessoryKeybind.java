package kub3s.eclipse.client.KeyBind;

import kub3s.eclipse.Network.ModPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class AccessoryKeybind {

    public static KeyMapping AKS_KEY;

    public static void register() {

        AKS_KEY = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.eclipse.accessory",
                        GLFW.GLFW_KEY_G,
                        EclipseKeyCategories.CATEGORY
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (AKS_KEY.consumeClick()) {

                ModPackets.sendOpenAks();

            }

        });
    }
}