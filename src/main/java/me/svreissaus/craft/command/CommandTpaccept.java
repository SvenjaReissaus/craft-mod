package me.svreissaus.craft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.svreissaus.craft.utils.ChatMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;
import static me.svreissaus.craft.CraftMod.data;

import java.util.UUID;

public final class CommandTpaccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tpaccept").executes(CommandTpaccept::execute));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (data.store.tpateleports.containsKey(player.getUuid())) {
            UUID request = data.store.tpateleports.get(player.getUuid());
            ServerPlayerEntity target = context.getSource().getMinecraftServer().getPlayerManager().getPlayer(request);
            if (target == null) {
                chat.send("This player is now offline.");
                return 1;
            }

            player.teleport(target.getServerWorld(), target.getPos().x, target.getPos().y, target.getPos().z,
                    target.yaw, target.pitch);
            chat.send("Teleported to " + target.getDisplayName());

            data.store.tpateleports.remove(player.getUuid());
        }

        return 1;
    }
}
