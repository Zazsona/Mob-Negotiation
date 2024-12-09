package com.zazsona.mobnegotiation.view2.lib.world.entity.state.freeze;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class StaticPlayerState extends StaticEntityState {

    // Prior state storage
    private Location prevPlayerLocation;
    private float prevPlayerWalkSpeed;
    private boolean prevPlayerInvisibilityState;
    private boolean allowHeadMovement;

    public StaticPlayerState(Plugin plugin, Player player) {
        super(plugin, player);
    }

    public StaticPlayerState(Plugin plugin, Player player, Location targetLocation) {
        super(plugin, player, targetLocation);
    }

    public boolean isAllowHeadMovement()
    {
        return allowHeadMovement;
    }

    public void setAllowHeadMovement(boolean allowHeadMovement)
    {
        this.allowHeadMovement = allowHeadMovement;
    }

    @Override
    public void render() {
        render(false);
    }

    public void render(boolean allowHeadMovement)
    {
        super.render();

        Entity entity = getEntity();
        if (entity instanceof Player)
        {
            Player player = (Player) entity;
            this.prevPlayerLocation = player.getLocation();
            this.prevPlayerWalkSpeed = player.getWalkSpeed();
            this.prevPlayerInvisibilityState = player.isInvisible();
            this.allowHeadMovement = allowHeadMovement;
            player.setWalkSpeed(0);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        Entity entity = getEntity();
        if (entity instanceof Player)
        {
            Player player = (Player) entity;
            player.setWalkSpeed(prevPlayerWalkSpeed);
            player.setInvisible(prevPlayerInvisibilityState);
            player.teleport(prevPlayerLocation);
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
        if (player == getEntity())
        {
            Location targetLocation = getLocation();
            if (allowHeadMovement) {
                targetLocation = new Location(
                        targetLocation.getWorld(),
                        targetLocation.getX(),
                        targetLocation.getY(),    // Maintain original position, but enable head movement
                        targetLocation.getZ(),
                        e.getTo().getYaw(),
                        e.getTo().getPitch()
                );
            }
            e.setTo(targetLocation);
        }
    }

    @Override
    public void onEntityMove()
    {
        // Do nothing; we use onPlayerMove instead.
    }

    /**
     * Stops players interacting with objects
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if (e.getPlayer() == getEntity())
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
        if (e.getPlayer() == getEntity())
            e.setCancelled(true);
    }

    /**
     * Stops entities interacting with inventory
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClickInventory(InventoryClickEvent e)
    {
        if (e.getWhoClicked() == getEntity())
            e.setCancelled(true);
    }
}
