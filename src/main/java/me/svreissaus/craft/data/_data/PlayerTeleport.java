package me.svreissaus.craft.data._data;

import java.util.Date;
import java.util.UUID;

public final class PlayerTeleport {
    public PlayerTeleport() {
    }

    public PlayerTeleport(UUID player, PlayerTeleportType type) {
        this.player = player;
        this.type = type;
    }

    public UUID player;
    public Date date = new Date();
    public PlayerTeleportType type;
}
