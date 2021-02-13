package me.svreissaus.craft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.svreissaus.craft.CraftMod;
import me.svreissaus.craft.utils.ChatMessage;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public final class CommandTpa {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("tpa").then(argument("target", EntityArgumentType.player()).executes(CommandTpa::execute)));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ServerPlayerEntity requestedPlayer = EntityArgumentType.getPlayer(context, "target");
        ChatMessage chatSP = new ChatMessage(player);
        ChatMessage chatRP = new ChatMessage(requestedPlayer);

        if (requestedPlayer.getUuid() != player.getUuid()) {
            CraftMod.data.store.tpateleports.put(requestedPlayer.getUuid(), player.getUuid());
            chatSP.send("Request sent to " + requestedPlayer.getName().getString());
            chatRP.send("Teleport request from " + player.getName().getString());
            chatRP.send("Use /tpaccept or /tpdeny");
        } else {
            chatRP.send("You can't request yourself!");
        }
        return 1;
    }
}
