package com.zazsona.mobnegotiation;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.HashSet;
import java.util.List;

public class MobEventListener implements Listener
{
    private HashSet<Player> negotiatingPlayers;
    private NegotiationEvent negotiationEvent;

    public MobEventListener()
    {
        this.negotiatingPlayers = new HashSet<>();
        this.negotiationEvent = this::handleNegotiationUpdate;
    }

    /**
     * Detects when a mob is hit to check if negotiations should begin,
     * and prevents players taking damage while negotiating
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Mob)
        {
            Player player = (Player) e.getDamager();
            Mob mob = (Mob) e.getEntity();
            NegotiationProcess negotiationProcess = new NegotiationProcess(player, mob);
            negotiationProcess.addEventListener(negotiationEvent);
            negotiationProcess.start();
        }
        else if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            if (negotiatingPlayers.contains(player))
                e.setCancelled(true);
        }
    }

    /**
     * Stops mobs targeting a negotiating player
     * @param e the target event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobTargetEntity(EntityTargetEvent e)
    {
        if (e.getTarget() instanceof Player)
        {
            Player player = (Player) e.getTarget();
            if (negotiatingPlayers.contains(player))
                e.setCancelled(true);
        }
    }

    /**
     * Tracks the state of players currently negotiating to grant them invincibility.
     * @param negotiation the updated negotiation
     */
    private void handleNegotiationUpdate(NegotiationProcess negotiation)
    {
        NegotiationState state = negotiation.getState();
        if (state == NegotiationState.STARTED)
        {
            Player player = negotiation.getPlayer();
            this.negotiatingPlayers.add(player);
            removeEntityTargetsOnPlayer(player);
        }
        else if (state.isTerminating)
            this.negotiatingPlayers.remove(negotiation.getPlayer());
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
