package me.svreissaus.craft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.svreissaus.craft.data._data.PlayerTeleport;
import me.svreissaus.craft.utils.ChatMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;
import static me.svreissaus.craft.CraftMod.data;

import java.util.Comparator;
import java.util.HashSet;

public final class CommandTpaccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tpaccept").executes(CommandTpaccept::execute));
        dispatcher.register(literal("tpayes").executes(CommandTpaccept::execute));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);

        if (!data.store.playerTeleports.containsKey(player.getUuid())) {
            chat.send("item.craftmod.commands_tpaccept_warnings_empty");
            return 1;
        }
        HashSet<PlayerTeleport> requests = data.store.playerTeleports.get(player.getUuid());
        if (requests.isEmpty()) {
            chat.send("item.craftmod.commands_tpaccept_warnings_empty");
            return 1;
        }
        PlayerTeleport current = requests.stream().sorted(new Comparator<PlayerTeleport>() {
            @Override
            public int compare(PlayerTeleport o1, PlayerTeleport o2) {
                return o1.date.compareTo(o2.date);
            }
        }).findFirst().get();

        ServerPlayerEntity target = context.getSource().getMinecraftServer().getPlayerManager()
                .getPlayer(current.player);
        if (target == null) {
            chat.send("item.craftmod.commands_tpaccept_warnings_offline");
            return 1;
        }

        player.teleport(target.getServerWorld(), target.getPos().x, target.getPos().y, target.getPos().z, target.yaw,
                target.pitch);
        chat.send("item.craftmod.commands_tpaccept_tpa_done", target.getDisplayName());
        return 1;
    }
}
