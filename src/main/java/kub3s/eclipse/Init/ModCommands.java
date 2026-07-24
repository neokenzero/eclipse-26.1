package kub3s.eclipse.Init;

import kub3s.eclipse.command.FactionCommand;
import kub3s.eclipse.command.SanityCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            FactionCommand.register(dispatcher);

        });
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) ->
                        SanityCommand.register(dispatcher)
        );
    }
}
