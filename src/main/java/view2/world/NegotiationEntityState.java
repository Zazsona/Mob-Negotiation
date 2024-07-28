package view2.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import view2.lib.world.entity.state.IEntityState;
import view2.lib.world.entity.state.RenderListenableEntityState;
import view2.lib.world.entity.state.freeze.StaticEntityState;
import view2.lib.world.entity.state.freeze.StaticEntityStateFactory;
import view2.lib.world.entity.state.invincible.UnassailableEntityState;
import view2.lib.world.entity.state.invincible.UnassailableEntityStateFactory;

public class NegotiationEntityState extends RenderListenableEntityState implements IEntityState, Listener {

    private Plugin plugin;
    private Entity entity;

    private StaticEntityStateFactory staticStateFactory;
    private StaticEntityState staticState;
    private Location targetLocation;
    private UnassailableEntityStateFactory unassailableEntityStateFactory;
    private UnassailableEntityState unassailableState;

    public NegotiationEntityState(Plugin plugin, Entity entity)
    {
        this.plugin = plugin;
        this.entity = entity;
        this.staticStateFactory = new StaticEntityStateFactory();
        this.unassailableEntityStateFactory = new UnassailableEntityStateFactory();
    }

    public NegotiationEntityState(Plugin plugin, Entity entity, Location targetLocation)
    {
        this(plugin, entity);
        setLocation(targetLocation);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isRendered()
    {
        return staticState.isRendered() && unassailableState.isRendered();
    }

    /**
     * Gets the Location the entity is locked to
     * @return location the lock location
     */
    public Location getLocation()
    {
        return this.targetLocation;
    }

    /**
     * Sets the location to lock the entity to.
     * @param location the location to lock to
     */
    public void setLocation(Location location)
    {
        this.targetLocation = location;
        if (this.staticState != null)
            this.staticState.setLocation(location);
    }

    @Override
    public void render() {
        if (this.isRendered())
            return;

        if (staticState.isRendered())
            staticState.destroy();
        if (unassailableState.isRendered())
            unassailableState.destroy();

        staticState = staticStateFactory.create(plugin, entity);
        staticState.setLocation(targetLocation);
        unassailableState = unassailableEntityStateFactory.create(plugin, entity);

        Bukkit.getPluginManager().registerEvents(this, plugin);
        staticState.render();
        unassailableState.render();
        super.render();
    }

    @Override
    public void destroy() {
        staticState.destroy();
        unassailableState.destroy();
        HandlerList.unregisterAll(this);
        super.destroy();
    }

    /**
     * Fires an event when the entity leaves.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        if (e.getPlayer() == entity)
            this.destroy();
    }

    /**
     * Fires an event when the entity dies.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e)
    {
        if (e.getEntity() == entity)
            this.destroy();
    }

    /**
     * Fires an event when the entity explodes.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e)
    {
        if (e.getEntity() == entity)
            this.destroy();
    }
}
