package com.zazsona.mobnegotiation.view2.lib.chat.interact.message;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatTextInputEventEmitter implements IChatTextInputEventEmittable, Listener {

    private final ArrayList<IChatTextInputListener> textInputListeners;

    public ChatTextInputEventEmitter() {
        this.textInputListeners = new ArrayList<>();
    }

    @Override
    public void addListener(IChatTextInputListener listener) {
        this.textInputListeners.add(listener);
    }

    @Override
    public boolean removeListener(IChatTextInputListener listener) {
        return this.textInputListeners.remove(listener);
    }

    @Override
    public int clearListeners() {
        int listenerCount = textInputListeners.size();
        textInputListeners.clear();
        return listenerCount;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChatAsync(AsyncPlayerChatEvent e)
    {
        String messageContent = e.getMessage();
        Player player = e.getPlayer();

        ChatTextInputEvent event = new ChatTextInputEvent(player, messageContent);
        for (IChatTextInputListener listener : textInputListeners) {
            boolean isConsumed = listener.onTextInput(event);
            if (isConsumed) {
                // Cancel the message to prevent it getting broadcast
                e.setCancelled(true);
                return;
            }
        }
    }
}
