package com.zazsona.mobnegotiation;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class NegotiationEntityEventListener implements Listener, NegotiationEventListener
{
    private NegotiationProcess negotiation;

    private Location playerLocation;
    private Location mobLocation;
    private float previousPlayerWalkSpeed = 0.2f;
    private boolean previousMobAwareState = true;
    private boolean previousPlayerInvulnerableState = false;
    private boolean previousMobInvulnerableState = false;
    private BukkitTask tickTask;

    public NegotiationEntityEventListener(NegotiationProcess negotiation)
    {
        this.negotiation = negotiation;
        this.negotiation.addEventListener(this);
    }

    /*
     * Prevent Damage
     */

    /**
     * Prevents the player and mob associated with the {@link NegotiationProcess} taking or dealing damage
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        if ((entity == player || entity == mob) || (damager == player || damager == mob))
            e.setCancelled(true);
    }

    /**
     * Stops mobs targeting the negotiating entities
     * @param e the target event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobTargetEntity(EntityTargetEvent e)
    {
        Entity entity = e.getEntity();
        if (entity == negotiation.getPlayer() || entity == negotiation.getMob())
            e.setCancelled(true);
    }

    /*
    * Prevent Movement
    */

    /**
     * Stops the negotiating player from moving
     * @param e the movement event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e)
    {
        if (playerLocation == null)
            return;
        Player player = e.getPlayer();
        if (player == negotiation.getPlayer())
        {
            Location newPosition = new Location(playerLocation.getWorld(),
                                                playerLocation.getX(),
                                                playerLocation.getY(),    // Maintain original position, but enable head movement
                                                playerLocation.getZ(),
                                                e.getTo().getYaw(),
                                                e.getTo().getPitch());
            e.setTo(newPosition);
        }
    }

    /**
     * Runs every server tick to handle entity states
     */
    public void onTick()
    {
        Mob mob = negotiation.getMob();
        if (mobLocation != null && !mob.getLocation().equals(mobLocation))
            mob.teleport(mobLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * Stops players interacting with objects while negotiating
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if (e.getPlayer() == negotiation.getPlayer())
        {
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    /**
     * Stops players interacting with objects while negotiating
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerConsume(PlayerItemConsumeEvent e)
    {
        if (e.getPlayer() == negotiation.getPlayer())
            e.setCancelled(true);
    }

    /**
     * Stops players interacting with objects while negotiating
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerUseInventory(InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player)
        {
            Player clicker = (Player) e.getWhoClicked();
            if (clicker == negotiation.getPlayer())
                e.setCancelled(true);
        }

    }

    /*
    * Terminating events
    */

    /**
     * Stops the negotiation when the player leaves.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        if (e.getPlayer() == negotiation.getPlayer())
            negotiation.stop();
    }

    /**
     * Stops the negotiation when the player or mob dies.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e)
    {
        Entity entity = e.getEntity();
        if (entity == negotiation.getPlayer() || entity == negotiation.getMob())
            negotiation.stop();
    }

    /**
     * Tracks the state of negotiation to register and unregister state enforcement events
     * @param negotiation the updated negotiation
     */
    @Override
    public void onNegotiationStateUpdate(NegotiationProcess negotiation)
    {
        NegotiationState state = negotiation.getState();
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        if (state == NegotiationState.STARTED)
        {
            playerLocation = player.getLocation();
            mobLocation = mob.getLocation();
            previousPlayerWalkSpeed = player.getWalkSpeed();
            previousMobAwareState = mob.isAware();
            previousPlayerInvulnerableState = player.isInvulnerable();
            previousMobInvulnerableState = mob.isInvulnerable();
            player.setWalkSpeed(0);
            mob.setAware(false);
            player.setInvulnerable(true);
            mob.setInvulnerable(true);
            removeNearbyEntityTargets();

            MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            tickTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onTick, 1, 1);
        }
        else if (state.isTerminating)
        {
            if (tickTask != null)
                tickTask.cancel();
            HandlerList.unregisterAll(this);
            negotiation.removeEventListener(this);
            player.setWalkSpeed(previousPlayerWalkSpeed);
            mob.setAware(previousMobAwareState);
            player.setInvulnerable(previousPlayerInvulnerableState);
            mob.setInvulnerable(previousMobInvulnerableState);
        }
    }

    /**
     * Stops all nearby entities from targeting entities involved in this the negotiation
     */
    private void removeNearbyEntityTargets()
    {
        int entityTargetingDistance = 40;
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        List<Entity> nearbyEntities = player.getNearbyEntities(entityTargetingDistance, entityTargetingDistance, entityTargetingDistance);
        for (Entity entity : nearbyEntities)
        {
            if (entity instanceof Creature)
            {
                Creature creature = (Creature) entity;
                if (creature.getTarget() == player || creature.getTarget() == mob)
                    creature.setTarget(null);
            }
        }
    }
}
