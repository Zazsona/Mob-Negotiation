package view2.lib.world.entity.state.invincible;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import view2.lib.world.entity.state.IEntityState;
import view2.lib.world.entity.state.RenderListenableEntityState;

import java.util.List;

public class InvincibleEntityState extends RenderListenableEntityState implements IEntityState, Listener {

    protected Plugin plugin;
    private boolean isRendered;
    private Entity entity;


    public InvincibleEntityState(Plugin plugin, Entity entity) {
        this.isRendered = false;
        this.plugin = plugin;
        this.entity = entity;
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
     * Renders this state onto the entity
     */
    public void render() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.isRendered = true;
        super.render();
    }

    /**
     * Removes this state from the entity
     */
    public void destroy() {
        HandlerList.unregisterAll(this);
        this.isRendered = false;
        super.render();
    }

    /**
     * Prevents the entity taking damage
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e)
    {
        if (e.getEntity() == entity)
            e.setCancelled(true);
    }

    /**
     * Stops any external nearby explosions having an effect.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e)
    {
        Location entityLocation = entity.getLocation();
        Block entityBlock = entityLocation.getWorld().getBlockAt(entityLocation.getBlockX(), entityLocation.getBlockY() - 1, entityLocation.getBlockZ());
        List<Block> blocksInExplosion = e.blockList();
        if (blocksInExplosion.contains(entityBlock))
            e.setCancelled(true);
    }
}
