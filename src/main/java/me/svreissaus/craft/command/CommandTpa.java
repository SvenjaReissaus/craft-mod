package me.svreissaus.craft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.svreissaus.craft.data._data.PlayerTeleport;
import me.svreissaus.craft.data._data.PlayerTeleportType;
import me.svreissaus.craft.utils.ChatMessage;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

import java.util.HashSet;
import java.util.Optional;

import static net.minecraft.server.command.CommandManager.argument;
import static me.svreissaus.craft.CraftMod.data;

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
            HashSet<PlayerTeleport> teleports = data.store.playerTeleports.get(requestedPlayer.getUuid());
            if (teleports != null) {
                Optional<PlayerTeleport> current = teleports.stream().filter(c -> c.player == player.getUuid())
                        .findFirst();
                if (current.isPresent())
                    teleports.remove(current.get());
            } else if (teleports == null) {
                teleports = new HashSet<PlayerTeleport>();
            }
            teleports.add(new PlayerTeleport(player.getUuid(), PlayerTeleportType.TPA));
            chatSP.send("item.craftmod.commands_tpa_sent" + requestedPlayer.getName().getString());
            chatRP.send("item.craftmod.commands_tpa_received" + player.getName().getString());
            chatRP.send("item.craftmod.commands_tpa_accept_hint");
        } else {
            chatRP.send("item.craftmod.comamnds_tpa_warnings_noself");
        }
        return 1;
    }
}
