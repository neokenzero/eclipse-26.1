package kub3s.eclipse.Player.Sanity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LightLayer;

public class SanityManager {
    public static void tick(ServerPlayer player) {
        if (player.tickCount % 6000 != 0)
            return;
        SanityData sanity = (SanityData)player.getAttachedOrCreate(SanityAttachment.SANITY);
        int decrease = calculateDecrease(player);
        player.setAttached(SanityAttachment.SANITY, sanity

                .remove(decrease));
    }

    private static int calculateDecrease(ServerPlayer player) {
        int value = 1;
        int skyLight = player.level().getBrightness(LightLayer.SKY, player

                .blockPosition());
        if (skyLight < 4)
            value += 2;
        if (!player.level().canSeeSky(player.blockPosition()))
            value++;
        return value;
    }
}
