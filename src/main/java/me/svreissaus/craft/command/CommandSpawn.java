package me.svreissaus.craft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.svreissaus.craft.utils.ChatMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;

public final class CommandSpawn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("spawn").executes(CommandSpawn::execute));
    }

    public static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getWorld();
        BlockPos spawn = world.getSpawnPos();
        ServerPlayerEntity player = context.getSource().getPlayer();
        ChatMessage chat = new ChatMessage(player);
        chat.send("item.craftmod.command_spawn");
        player.teleport(world, spawn.getX(), spawn.getY(), spawn.getZ(), player.yaw, player.pitch);
        return 1;
    }
}
