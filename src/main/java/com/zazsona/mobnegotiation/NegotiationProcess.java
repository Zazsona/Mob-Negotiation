package com.zazsona.mobnegotiation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NegotiationProcess
{
    private Player player;
    private Mob mob;
    private NegotiationState state;
    private ArrayList<NegotiationEvent> listeners;

    public NegotiationProcess(Player player, Mob mob)
    {
        this.player = player;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.listeners = new ArrayList<>();
    }

    /**
     * Gets the player receiving the negotiation
     * @return the negotiating player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Gets the mob offering the negotiation
     * @return the negotiating mob
     */
    public Mob getMob()
    {
        return mob;
    }

    /**
     * Gets the current state of negotiation
     * @return state of negotiation
     */
    public NegotiationState getState()
    {
        return state;
    }

    /**
     * Adds a listener to handle negotiation state change events
     * @param listener the listener to add
     * @return boolean on success
     */
    public boolean addEventListener(NegotiationEvent listener)
    {
        return listeners.add(listener);
    }

    /**
     * Removes a listener from handling negotiation state change events
     * @param listener the listener to remove
     * @return boolean on success
     */
    public boolean removeEventListener(NegotiationEvent listener)
    {
        return listeners.remove(listener);
    }

    /**
     * Begins the negotiation process, presenting the user with the UI.
     */
    public void start()
    {
        this.state = NegotiationState.STARTED;
        this.updateListeners();
        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "WAIT, WAIT!", null, 2, 20, 7);
        Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () ->
        {
            this.state = NegotiationState.FINISHED;
            this.updateListeners();
        }, 80);
    }

    /**
     * Notifies all subscribed listeners of a state update
     */
    private void updateListeners()
    {
        for (NegotiationEvent listener : listeners)
            listener.onNegotiationStateUpdate(this);
    }
}
