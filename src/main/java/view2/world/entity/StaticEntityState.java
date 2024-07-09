package view2.world.entity;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

// TODO: Wrap into a "NegotiationEntityState" class to manage both the player & mob, with any negotiation specific customisations

public class StaticEntityState {

    protected Plugin plugin;
    private Entity entity;
    private Location targetEntityLocation;
    private BukkitTask tickMovementTask;


    public StaticEntityState(Plugin plugin, Entity entity) {
        this.plugin = plugin;
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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

        this.tickMovementTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onEntityMove, 1, 1);
    }

    /**
     * Removes this state from the entity
     */
    public void destroy() {
        if (tickMovementTask != null)
            tickMovementTask.cancel();
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
