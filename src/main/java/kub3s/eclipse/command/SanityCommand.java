package kub3s.eclipse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import kub3s.eclipse.Player.Sanity.SanityAttachment;
import kub3s.eclipse.Player.Sanity.SanityData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

public class SanityCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("sanity")
                        .requires(source ->
                                source.permissions().hasPermission(
                                        new Permission.HasCommandLevel(PermissionLevel.ADMINS)
                                )
                        )

                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {

                                                    ServerPlayer player =
                                                            EntityArgument.getPlayer(ctx, "player");

                                                    int amount =
                                                            IntegerArgumentType.getInteger(ctx, "amount");

                                                    SanityData sanity =
                                                            player.getAttachedOrCreate(
                                                                    SanityAttachment.SANITY
                                                            );

                                                    player.setAttached(
                                                            SanityAttachment.SANITY,
                                                            sanity.add(amount)
                                                    );

                                                    ctx.getSource().sendSuccess(
                                                            () -> Component.literal(
                                                                    "Рассудок игрока " +
                                                                            player.getName().getString() +
                                                                            " увеличен на " +
                                                                            amount
                                                            ),
                                                            true
                                                    );

                                                    return 1;
                                                }))))


                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> {

                                                    ServerPlayer player =
                                                            EntityArgument.getPlayer(ctx, "player");

                                                    int amount =
                                                            IntegerArgumentType.getInteger(ctx, "amount");

                                                    SanityData sanity =
                                                            player.getAttachedOrCreate(
                                                                    SanityAttachment.SANITY
                                                            );

                                                    player.setAttached(
                                                            SanityAttachment.SANITY,
                                                            sanity.remove(amount)
                                                    );

                                                    ctx.getSource().sendSuccess(
                                                            () -> Component.literal(
                                                                    "Рассудок игрока " +
                                                                            player.getName().getString() +
                                                                            " уменьшен на " +
                                                                            amount
                                                            ),
                                                            true
                                                    );

                                                    return 1;
                                                }))))

                        .then(Commands.literal("set")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, 100))
                                                .executes(ctx -> {

                                                    ServerPlayer player =
                                                            EntityArgument.getPlayer(ctx, "player");

                                                    int amount =
                                                            IntegerArgumentType.getInteger(ctx, "amount");


                                                    SanityData sanity =
                                                            player.getAttachedOrCreate(
                                                                    SanityAttachment.SANITY
                                                            );


                                                    player.setAttached(
                                                            SanityAttachment.SANITY,
                                                            sanity.set(amount)
                                                    );


                                                    ctx.getSource().sendSuccess(
                                                            () -> Component.literal(
                                                                    "Рассудок игрока " +
                                                                            player.getName().getString() +
                                                                            " установлен на " +
                                                                            amount
                                                            ),
                                                            true
                                                    );


                                                    return 1;
                                                }))))
                        .then(Commands.literal("get")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(ctx -> {

                                            ServerPlayer player =
                                                    EntityArgument.getPlayer(ctx, "player");

                                            SanityData sanity =
                                                    player.getAttachedOrCreate(
                                                            SanityAttachment.SANITY
                                                    );

                                            ctx.getSource().sendSuccess(
                                                    () -> Component.literal(
                                                            "Sanity: " + sanity.getSanity()
                                                    ),
                                                    false
                                            );

                                            return 1;
                                        })
                                )
                        )
        );

    }

}