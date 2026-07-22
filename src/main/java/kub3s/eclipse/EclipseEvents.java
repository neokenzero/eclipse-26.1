package kub3s.eclipse;

import kub3s.eclipse.Player.Sanity.SanityManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;

public class EclipseEvents {

    public EclipseEvents() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((ServerTickEvents.EndTick)(server) -> {
            for(ServerPlayer player : server.getPlayerList().getPlayers()) {
                SanityManager.tick(player);
            }

        });
    }
}
