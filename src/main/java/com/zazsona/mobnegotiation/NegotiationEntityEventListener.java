package com.zazsona.mobnegotiation;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class NegotiationEntityEventListener implements Listener, NegotiationEventListener
{
    private NegotiationProcess negotiation;

    public NegotiationEntityEventListener(NegotiationProcess negotiation)
    {
        this.negotiation = negotiation;
        this.negotiation.addEventListener(this);
    }

    /**
     * Prevents the player and mob associated with the {@link NegotiationProcess} taking damage
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        Entity entity = e.getEntity();
        if (entity == negotiation.getPlayer() || entity == negotiation.getMob())
            e.setCancelled(true);
    }

    /**
     * Stops mobs targeting the negotiating player
     * @param e the target event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobTargetEntity(EntityTargetEvent e)
    {
        Entity entity = e.getEntity();
        if (entity == negotiation.getPlayer() || entity == negotiation.getMob())
            e.setCancelled(true);
    }

    /**
     * Stops the negotiating player from moving
     * @param e the movement event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        Location originalPosition = e.getFrom();
        if (player == negotiation.getPlayer())
            e.setTo(originalPosition);
    }

    /**
     * Tracks the state of negotiation to register and unregister state enforcement events
     * @param negotiation the updated negotiation
     */
    @Override
    public void onNegotiationStateUpdate(NegotiationProcess negotiation)
    {
        NegotiationState state = negotiation.getState();
        if (state == NegotiationState.STARTED)
        {
            MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            removeEntityTargetsOnPlayer(negotiation.getPlayer());
        }
        else if (state.isTerminating)
        {
            HandlerList.unregisterAll(this);
            negotiation.removeEventListener(this);
        }
    }

    /**
     * Stops all nearby entities from targeting the supplied player
     * @param player the player to untarget
     */
    private void removeEntityTargetsOnPlayer(Player player)
    {
        int entityTargetingDistance = 40;
        List<Entity> nearbyEntities = player.getNearbyEntities(entityTargetingDistance, entityTargetingDistance, entityTargetingDistance);
        for (Entity entity : nearbyEntities)
        {
            if (entity instanceof Creature)
            {
                Creature creature = (Creature) entity;
                if (creature.getTarget() == player)
                    creature.setTarget(null);
            }
        }
    }
}
