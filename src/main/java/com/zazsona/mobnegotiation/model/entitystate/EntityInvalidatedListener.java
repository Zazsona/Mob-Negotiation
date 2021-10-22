package com.zazsona.mobnegotiation.model.entitystate;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens for when an entity becomes "invalid" (i.e, no longer available)
 */
public class EntityInvalidatedListener extends EntityListener implements Listener
{
    private final List<EntityInvalidatedEventListener> listeners;

    public EntityInvalidatedListener(Plugin plugin, Entity entity)
    {
        super(plugin, entity);
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds a listener that is fired when an entity becomes invalid.
     * @param listener the listener to add
     */
    public void addListener(EntityInvalidatedEventListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes a listener
     * @param listener the listener to remove
     */
    public void removeListener(EntityInvalidatedEventListener listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * Runs all listeners associated with this object to inform of entity invalidation.
     */
    private void runListeners()
    {
        for (int i = listeners.size() - 1; i > -1; i--)
            listeners.get(i).onEntityInvalidated(entity);
    }

    /**
     * Fires an event when the plugin is disabled
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent e)
    {
        if (e.getPlugin() == MobNegotiationPlugin.getInstance())
            runListeners();
    }

    /**
     * Fires an event when the entity leaves.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        if (e.getPlayer() == entity)
            runListeners();
    }

    /**
     * Fires an event when the entity dies.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e)
    {
        if (e.getEntity() == entity)
            runListeners();
    }

    /**
     * Fires an event when the entity explodes.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e)
    {
        if (e.getEntity() == entity)
            runListeners();
    }
}
