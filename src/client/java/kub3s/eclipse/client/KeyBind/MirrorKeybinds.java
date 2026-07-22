package kub3s.eclipse.client.KeyBind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class MirrorKeybinds {

    public static KeyMapping MODE_KEY;
    public static boolean setSpawnMode = false;

    public static void register() {

        MODE_KEY = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.eclipse.mirror_mode",
                        GLFW.GLFW_KEY_H,
                        EclipseKeyCategories.CATEGORY
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            while (MODE_KEY.consumeClick()) {
                setSpawnMode = !setSpawnMode;

                client.player.sendSystemMessage(
                        Component.literal(
                                setSpawnMode
                                        ? "§aMode: SET SPAWN"
                                        : "§eMode: TELEPORT"
                        )
                );
            }
        });
    }
}