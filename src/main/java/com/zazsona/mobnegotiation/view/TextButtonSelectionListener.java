package com.zazsona.mobnegotiation.view;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.controller.NegotiationController;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class TextButtonSelectionListener implements Listener
{
    private NegotiationController controller;

    public TextButtonSelectionListener(NegotiationController controller) {
        this.controller = controller;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChatAsync(AsyncPlayerChatEvent e)
    {
        String messageContent = e.getMessage();
        Player player = e.getPlayer();
        if (!Pattern.matches("[0-9]", messageContent))
            return;
        if (!controller.isPlayerNegotiating(player))
            return;

        NegotiationMenu menu = controller.getNegotiationMenuForPlayer(player);
        ArrayList<INegotiationView> menuChildren = menu.getChildren();
        int selectionIndex = (Integer.parseInt(messageContent) - 1);
        if (selectionIndex > menuChildren.size())
            return;

        String menuId = menu.getId();
        String selectedChildMenuId = menu.getChildren().get(selectionIndex).getId();
        // Commands must be executed on the main thread...
        Bukkit.getScheduler().callSyncMethod(MobNegotiationPlugin.getInstance(), () ->
                player.performCommand(String.format("%s %s %s", NegotiationViewInteractionExecutor.COMMAND_KEY, menuId, selectedChildMenuId))
        );

        // Cancel the message to prevent it getting broadcast
        e.setCancelled(true);
    }
}
