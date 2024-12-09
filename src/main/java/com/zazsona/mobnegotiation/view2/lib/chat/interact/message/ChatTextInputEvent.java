package com.zazsona.mobnegotiation.view2.lib.chat.interact.message;

import org.bukkit.entity.Player;

public class ChatTextInputEvent {

    private Player player;

    private String text;

    public ChatTextInputEvent(Player player, String text) {
        this.player = player;
        this.text = text;
    }

    /**
     * Gets the player sending input
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the input text
     * @return
     */
    public String getText() {
        return text;
    }
}
