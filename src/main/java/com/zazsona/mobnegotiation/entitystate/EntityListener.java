package com.zazsona.mobnegotiation.entitystate;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class EntityListener implements Listener
{
    protected Plugin plugin;
    protected Entity entity;
    protected boolean active;

    public EntityListener(Plugin plugin, Entity entity)
    {
        this.plugin = plugin;
        this.entity = entity;
        this.active = false;
    }

    /**
     * Gets the associated entity
     * @return the entity
     */
    public Entity getEntity()
    {
        return entity;
    }

    /**
     * Gets if this listener is currently active
     * @return true if active
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Applies this listener
     */
    public void start()
    {
        if (!isActive())
        {
            MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            this.active = true;
        }

    }

    /**
     * Stops applying this listener
     */
    public void stop()
    {
        if (isActive())
        {
            HandlerList.unregisterAll(this);
            this.active = false;
        }
    }
}
