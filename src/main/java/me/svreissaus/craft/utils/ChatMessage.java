package me.svreissaus.craft.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;

public final class ChatMessage {

    public final PlayerEntity player;
    public boolean actionBar = false;

    public ChatMessage(PlayerEntity player) {
        this.player = player;
    }

    public ChatMessage withActionBar() {
        this.actionBar = true;
        return this;
    }

    public ChatMessage withoutActionBar() {
        this.actionBar = false;
        return this;
    }

    public void send(String message) {
        this.player.sendMessage(new TranslatableText(message), this.actionBar);
    }
}