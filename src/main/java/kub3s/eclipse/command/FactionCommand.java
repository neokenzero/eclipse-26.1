package kub3s.eclipse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import kub3s.eclipse.Data.FactionSpawnData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FactionCommand {

    private static final List<String> FACTIONS = List.of(
            "straniki",
            "svet",
            "smert",
            "lynal"
    );

    private FactionCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("faction")
                        .requires(source ->
                                source.permissions().hasPermission(
                                        new Permission.HasCommandLevel(PermissionLevel.ADMINS)
                                )
                        )
                        .then(
                                Commands.literal("spawn")
                                        .then(
                                                Commands.literal("set")
                                                        .then(
                                                                Commands.argument("faction", StringArgumentType.word())
                                                                        .suggests(FactionCommand::suggestFactions)

                                                                        .then(
                                                                                Commands.argument("pos", BlockPosArgument.blockPos())
                                                                                        .executes(ctx -> setSpawn(
                                                                                                ctx,
                                                                                                StringArgumentType.getString(ctx, "faction"),
                                                                                                BlockPosArgument.getLoadedBlockPos(ctx, "pos")
                                                                                        ))
                                                                        )

                                                                        .executes(ctx -> setSpawn(
                                                                                ctx,
                                                                                StringArgumentType.getString(ctx, "faction"),
                                                                                BlockPos.containing(ctx.getSource().getPosition())
                                                                        ))
                                                        )
                                        )
                        )
        );
    }

    private static CompletableFuture<Suggestions> suggestFactions(
            CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder
    ) {

        String remaining = builder.getRemaining().toLowerCase();

        for (String faction : FACTIONS) {
            if (faction.startsWith(remaining)) {
                builder.suggest(faction);
            }
        }

        return builder.buildFuture();
    }

    private static int setSpawn(
            CommandContext<CommandSourceStack> context,
            String faction,
            BlockPos pos
    ) throws CommandSyntaxException {

        CommandSourceStack source = context.getSource();

        faction = faction.toLowerCase();

        if (!FACTIONS.contains(faction)) {
            source.sendFailure(Component.literal(
                    "§cНеизвестная фракция: " + faction
            ));
            return 0;
        }

        ServerLevel level = source.getLevel();

        FactionSpawnData data = FactionSpawnData.get(level);
        data.setPos(faction, pos);

        String finalFaction = faction;
        source.sendSuccess(
                () -> Component.literal(String.format(
                        "§aСпавн фракции §b%s §aустановлен: %d %d %d",
                        finalFaction,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ()
                )),
                true
        );

        return 1;
    }
}