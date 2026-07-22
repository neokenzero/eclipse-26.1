package kub3s.eclipse.Network;

import kub3s.eclipse.Accsessory.System.AccessoryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;


public class OpenAccessory {

    public static void open(ServerPlayer player) {

        player.openMenu(
                new SimpleMenuProvider(

                        (id, inventory, p) ->
                                new AccessoryMenu(
                                        id,
                                        inventory
                                ),

                        Component.translatable(
                                "gui.eclipse.accessory"
                        )

                )
        );


    }

}