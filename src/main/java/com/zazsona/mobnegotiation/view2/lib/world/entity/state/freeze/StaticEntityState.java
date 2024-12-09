package com.zazsona.mobnegotiation.view2.lib.world.entity.state.freeze;

import com.zazsona.mobnegotiation.view2.lib.RenderListenable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import com.zazsona.mobnegotiation.view2.lib.world.entity.state.IEntityState;

public class StaticEntityState extends RenderListenable implements IEntityState, Listener {

    protected Plugin plugin;
    private boolean isRendered;
    private Entity entity;
    private Location targetEntityLocation;
    private BukkitTask tickMovementTask;


    public StaticEntityState(Plugin plugin, Entity entity) {
        super();
        this.isRendered = false;
        this.plugin = plugin;
        this.entity = entity;
    }

    public StaticEntityState(Plugin plugin, Entity entity, Location targetLocation) {
        super();
        this.isRendered = false;
        this.plugin = plugin;
        this.entity = entity;
        setLocation(targetLocation);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isRendered()
    {
        return isRendered;
    }

    /**
     * Gets the Location the entity is locked to
     * @return location the lock location
     */
    public Location getLocation()
    {
        return targetEntityLocation;
    }

    /**
     * Sets the location to lock the entity to.
     * @param location the location to lock to
     */
    public void setLocation(Location location)
    {
        this.targetEntityLocation = location;
    }

    /**
     * Renders this state onto the entity
     */
    public void render() {
        if (targetEntityLocation == null)
            targetEntityLocation = entity.getLocation();

        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.tickMovementTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onEntityMove, 1, 1);
        this.isRendered = true;
        super.render();
    }

    /**
     * Removes this state from the entity
     */
    public void destroy() {
        if (tickMovementTask != null)
            tickMovementTask.cancel();

        HandlerList.unregisterAll(this);
        this.isRendered = false;
        super.destroy();
    }

    /**
     * Runs every server tick to prevent entity movement
     */
    public void onEntityMove()
    {
        if (!entity.getLocation().equals(targetEntityLocation))
            entity.teleport(targetEntityLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * Prevents the entity dealing damage
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e)
    {
        Entity damager = e.getDamager();
        if (damager == entity)
            e.setCancelled(true);
    }
}
