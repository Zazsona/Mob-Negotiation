package com.zazsona.mobnegotiation.view2.lib.world.entity.state.invincible;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;
import com.zazsona.mobnegotiation.view2.lib.world.entity.state.IEntityState;

import java.util.List;

public class UnassailableEntityState extends InvincibleEntityState implements IEntityState, Listener {

    public UnassailableEntityState(Plugin plugin, Entity entity) {
        super(plugin, entity);
    }

    /**
     * Renders this state onto the entity
     */
    public void render() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        removeNearbyEntityTargets();
        super.render();
    }

    /**
     * Removes this state from the entity
     */
    public void destroy() {
        HandlerList.unregisterAll(this);
        super.destroy();
    }

    /**
     * Stops mobs targeting the entity
     * @param e the target event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobTargetEntity(EntityTargetEvent e)
    {
        if (e.getTarget() == getEntity())
            e.setCancelled(true);
    }

    /**
     * Stops all nearby entities from targeting the entity
     */
    private void removeNearbyEntityTargets()
    {
        Entity entity = getEntity();
        int maxEntityTargetingDistance = 40;
        List<Entity> nearbyEntities = entity.getNearbyEntities(maxEntityTargetingDistance, maxEntityTargetingDistance, maxEntityTargetingDistance);
        for (Entity nearbyEntity : nearbyEntities)
        {
            if (nearbyEntity instanceof Creature)
            {
                Creature nearbyCreature = (Creature) nearbyEntity;
                if (nearbyCreature.getTarget() == entity)
                    nearbyCreature.setTarget(null);
            }
        }
    }
}
