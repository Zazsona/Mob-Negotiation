package com.zazsona.mobnegotiation.entitystate;

import com.zazsona.mobnegotiation.NegotiationProcess;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class EntityInvincibilityListener extends EntityListener implements Listener
{
    public EntityInvincibilityListener(Plugin plugin, Entity entity)
    {
        super(plugin, entity);
    }

    @Override
    public void start()
    {
        if (!isActive())
        {
            super.start();
            removeNearbyEntityTargets();
        }
    }

    /**
     * Prevents the entity taking damage
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e)
    {
        Entity eventEntity = e.getEntity();
        if (eventEntity == entity)
            e.setCancelled(true);
    }

    /**
     * Stops mobs targeting the entity
     * @param e the target event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobTargetEntity(EntityTargetEvent e)
    {
        Entity eventEntity = e.getEntity();
        if (eventEntity == entity)
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

    /**
     * Stops all nearby entities from targeting the entity
     */
    private void removeNearbyEntityTargets()
    {
        int maxEntityTargetingDistance = 40;
        List<Entity> nearbyEntities = entity.getNearbyEntities(maxEntityTargetingDistance, maxEntityTargetingDistance, maxEntityTargetingDistance);
        for (Entity entity : nearbyEntities)
        {
            if (entity instanceof Creature)
            {
                Creature creature = (Creature) entity;
                if (creature.getTarget() == entity)
                    creature.setTarget(null);
            }
        }
    }
}
