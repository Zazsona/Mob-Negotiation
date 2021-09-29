package com.zazsona.mobnegotiation;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
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
    private int previousMobMaxFuseTicks = 30;
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
     * Stops the negotiation when the player or mob explodes.
     * Should never occur in theory, as creeper explosions are disabled, but better safe than sorry due to other plugins.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e)
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
        if (state == NegotiationState.INITIALISING)
        {
            // There is a one tick delay before negotiations start to finalise initialisation
            // This could be enough for a creeper to explode, thus we must prevent it.
            runNegotiationInitialisationActions();
        }
        else if (state == NegotiationState.STARTED)
        {
            runNogotiationStartedActions();
        }
        else if (state.isTerminating)
        {
            runNegotiationTerminationActions();
        }
    }

    /**
     * Prepares this listener for the {@link NegotiationProcess}'s initialisation state.
     * There is a one tick delay before negotiations start to finalise initialisation,
     * this could be enough for a creeper to explode, thus this method prevents it.
     */
    private void runNegotiationInitialisationActions()
    {
        Mob mob = negotiation.getMob();
        if (mob instanceof Creeper)
        {
            Creeper creeper = (Creeper) mob;
            int ticksPerDay = 20 * 60 * 60 * 24; // Arbitrary high amount
            previousMobMaxFuseTicks = creeper.getMaxFuseTicks();
            creeper.setMaxFuseTicks(ticksPerDay);
            creeper.setFuseTicks(0);
        }
    }

    /**
     * Prepares this listener for the {@link NegotiationProcess}'s started state.
     */
    private void runNogotiationStartedActions()
    {
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
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

    /**
     * Stops all nearby entities from targeting entities involved in this the negotiation
     */
    private void removeNearbyEntityTargets()
    {
        int maxEntityTargetingDistance = 40;
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        List<Entity> nearbyEntities = player.getNearbyEntities(maxEntityTargetingDistance, maxEntityTargetingDistance, maxEntityTargetingDistance);
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

    /**
     * Prepares this listener for the {@link NegotiationProcess}'s terminating state.
     */
    private void runNegotiationTerminationActions()
    {
        Player player = negotiation.getPlayer();
        Mob mob = negotiation.getMob();
        if (tickTask != null)
            tickTask.cancel();
        HandlerList.unregisterAll(this);
        negotiation.removeEventListener(this);

        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
        if (player != null)
        {
            player.setWalkSpeed(previousPlayerWalkSpeed);
            plugin.getServer().getScheduler().runTaskLater(plugin, () ->
            {
                if (player.isValid())     // Delay restoring vulnerability so players can get their surroundings.
                    player.setInvulnerable(previousPlayerInvulnerableState);
            }, PluginConfig.getNegotiationDmgGracePeriod());
        }
        if (mob != null && mob.isValid())
        {
            mob.setAware(previousMobAwareState);
            mob.setInvulnerable(previousMobInvulnerableState);
            if (mob instanceof Creeper)
            {
                Creeper creeper = (Creeper) mob;
                int fuseTime = previousMobMaxFuseTicks + PluginConfig.getNegotiationDmgGracePeriod();
                creeper.setMaxFuseTicks(fuseTime);
                creeper.setFuseTicks(0);
                plugin.getServer().getScheduler().runTaskLater(plugin, () ->
                {
                    if (creeper.isValid())       // As the grace period has ended, reset to previous fuse time.
                        creeper.setMaxFuseTicks(previousMobMaxFuseTicks);
                }, fuseTime);
            }
        }
    }
}
