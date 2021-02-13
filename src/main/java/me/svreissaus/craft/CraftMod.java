package me.svreissaus.craft;

import me.svreissaus.craft.command.CommandTpa;
import me.svreissaus.craft.command.CommandTpaccept;
import me.svreissaus.craft.data.CraftStore;
import me.svreissaus.craft.data.config.Configuration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

public class CraftMod implements ModInitializer {
    public static Configuration config;
    public static CraftStore data;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        config = new Configuration(FabricLoader.getInstance().getConfigDirectory());
        data = new CraftStore(FabricLoader.getInstance().getConfigDirectory());

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            CommandTpa.register(dispatcher);
            CommandTpaccept.register(dispatcher);
        });

        System.out.println("CraftMod has been initialized.");
    }
}
