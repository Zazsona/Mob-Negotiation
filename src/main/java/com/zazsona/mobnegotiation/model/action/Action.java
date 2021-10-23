package com.zazsona.mobnegotiation.model.action;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Action implements IAction
{
    protected Player player;
    protected Mob mob;
    protected ArrayList<IActionListener> listeners;

    public Action(Player player, Mob mob)
    {
        this.player = player;
        this.mob = mob;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(IActionListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public boolean removeListener(IActionListener listener)
    {
        return listeners.remove(listener);
    }

    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    public Mob getMob()
    {
        return mob;
    }

    /**
     * Runs all listeners for alerting when the action starts.
     */
    protected void runOnStartListeners()
    {
        for (int i = listeners.size() - 1; i > -1; i--)
            listeners.get(i).onActionStart(this);
    }

    /**
     * Runs all listeners for alerting when the action ends.
     */
    protected void runOnCompleteListeners()
    {
        for (int i = listeners.size() - 1; i > -1; i--)
            listeners.get(i).onActionComplete(this);
    }
}
