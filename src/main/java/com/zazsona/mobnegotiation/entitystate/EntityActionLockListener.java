package com.zazsona.mobnegotiation.entitystate;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class EntityActionLockListener extends EntityListener
{
    private Location entityLocation;
    private BukkitTask tickMovementTask;

    private Location playerLocation;
    private float playerWalkSpeed;
    private boolean playerInvisibilityState;
    private boolean mobAwareState;
    private int creeperMaxFuseTicks;

    public EntityActionLockListener(Plugin plugin, Entity entity)
    {
        super(plugin, entity);
        this.entityLocation = entity.getLocation();
    }

    /**
     * Sets the location this entity is locked to.
     * @param location the location to lock to
     */
    public void setLockedLocation(Location location)
    {
        this.entityLocation = location;
    }

    /**
     * Applies this listener, preventing the associated entity from performing actions
     */
    @Override
    public void start()
    {
        if (!isActive())
        {
            super.start();
            this.tickMovementTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onNonPlayerEntityMove, 1, 1);

            if (entity instanceof Creature)
                ((Creature) entity).setTarget(null);
            if (entity instanceof Player)
            {
                Player player = (Player) entity;
                playerLocation = player.getLocation();
                playerWalkSpeed = player.getWalkSpeed();
                playerInvisibilityState = player.isInvisible();
                player.setWalkSpeed(0);
            }
            if (entity instanceof Mob)
            {
                Mob mob = (Mob) entity;
                mobAwareState = mob.isAware();
                mob.setAware(false);
            }
            if (entity instanceof Creeper)
            {
                Creeper creeper = (Creeper) entity;
                int ticksPerDay = 20 * 60 * 60 * 24; // Arbitrary high amount
                creeperMaxFuseTicks = creeper.getMaxFuseTicks();
                creeper.setMaxFuseTicks(ticksPerDay);
                creeper.setFuseTicks(0);
            }
        }

    }

    /**
     * Stops this listener allowing the associated entity to perform actions
     */
    public void stop()
    {
        if (isActive())
        {
            super.stop();
            if (tickMovementTask != null)
                tickMovementTask.cancel();

            if (entity instanceof Player)
            {
                Player player = (Player) entity;
                player.setWalkSpeed(playerWalkSpeed);
                player.setInvisible(playerInvisibilityState);
                player.teleport(playerLocation);
            }
            if (entity instanceof Mob)
                ((Mob) entity).setAware(mobAwareState);
            if (entity instanceof Creeper)
            {
                Creeper creeper = (Creeper) entity;
                creeper.setMaxFuseTicks(creeperMaxFuseTicks);
                creeper.setFuseTicks(0);
            }
        }
    }

    /**
     * Stops the entity from moving if it's a player
     * @param e the movement event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        if (player == entity)
        {
            Location newPosition = new Location(entityLocation.getWorld(),
                                                entityLocation.getX(),
                                                entityLocation.getY(),    // Maintain original position, but enable head movement
                                                entityLocation.getZ(),
                                                e.getTo().getYaw(),
                                                e.getTo().getPitch());
            e.setTo(newPosition);
        }
    }

    /**
     * Runs every server tick to prevent entity movement
     */
    public void onNonPlayerEntityMove()
    {
        if (!(entity instanceof Player) && !entity.getLocation().equals(entityLocation))
            entity.teleport(entityLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
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

    /**
     * Stops players interacting with objects
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if (e.getPlayer() == entity)
        {
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    /**
     * Stops players interacting with food
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerConsume(PlayerItemConsumeEvent e)
    {
        if (e.getPlayer() == entity)
            e.setCancelled(true);
    }

    /**
     * Stops entities interacting with inventory
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClickInventory(InventoryClickEvent e)
    {
        if (e.getWhoClicked() == entity)
            e.setCancelled(true);
    }

    // TODO: Stop on server stop
}
