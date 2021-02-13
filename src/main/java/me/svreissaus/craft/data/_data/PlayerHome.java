package me.svreissaus.craft.data._data;

import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerHome {
    public PlayerHome() {
    }

    public PlayerHome(ServerPlayerEntity player) {
        this.x = player.getPos().x;
        this.y = player.getPos().y;
        this.z = player.getPos().z;
        this.yaw = player.yaw;
        this.pitch = player.pitch;
    }

    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
}
