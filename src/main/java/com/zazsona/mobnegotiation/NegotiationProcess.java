package com.zazsona.mobnegotiation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class NegotiationProcess
{
    private Player player;
    private Mob mob;
    private NegotiationState state;
    private ArrayList<NegotiationEventListener> listeners;

    public NegotiationProcess(Player player, Mob mob)
    {
        this.player = player;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.listeners = new ArrayList<>();
    }

    /**
     * Gets the player receiving the negotiation
     * @return the negotiating player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Gets the mob offering the negotiation
     * @return the negotiating mob
     */
    public Mob getMob()
    {
        return mob;
    }

    /**
     * Gets the current state of negotiation
     * @return state of negotiation
     */
    public NegotiationState getState()
    {
        return state;
    }

    /**
     * Adds a listener to handle negotiation state change events
     * @param listener the listener to add
     * @return boolean on success
     */
    public boolean addEventListener(NegotiationEventListener listener)
    {
        return listeners.add(listener);
    }

    /**
     * Removes a listener from handling negotiation state change events
     * @param listener the listener to remove
     * @return boolean on success
     */
    public boolean removeEventListener(NegotiationEventListener listener)
    {
        return listeners.remove(listener);
    }

    /**
     * Initialises the negotiation process and, if successful, presents the UI to the player.
     * There is a one tick delay before the UI is displayed and the listener is fired to ensure initialisation values are applied.
     * @throws InvalidParameterException Negotiating entity is unable to negotiate
     */
    public void start()
    {
        positionEntity(player);
        positionEntity(mob);
        mob.setTarget(null); // Disabling this allows us to override the head position of the entity
        mob.setRotation(mob.getLocation().getYaw(), 45); // Sad expression

        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();   // Wait a tick to allow initialisations to apply
        plugin.getServer().getScheduler().runTaskLater(plugin, this::beginNegotiation, 1);
    }

    /**
     * Presents the negotiation UI to the user and marks the negotiation state as "STARTED"
     */
    private void beginNegotiation()
    {
        this.state = NegotiationState.STARTED;
        this.updateListeners();

        String alertFormat = "" + ChatColor.RED + ChatColor.BOLD;
        player.sendTitle(alertFormat + "WAIT, WAIT!", null, 2, 20, 7);

        Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), this::stop, 80);
    }

    /**
     * Teleports the entity to a valid position within their current X/Y co-ordinates for negotiation.
     * @param entity the entity to position
     * @throws InvalidParameterException no valid position could be found for the entity
     */
    private void positionEntity(Entity entity)
    {
        World world = entity.getWorld();
        Location entityLocation = entity.getLocation();
        int playerY = entityLocation.getBlockY();
        for (int yIndex = playerY; yIndex >= player.getWorld().getMinHeight(); yIndex--)
        {
            Block block = world.getBlockAt(entityLocation.getBlockX(), yIndex, entityLocation.getBlockZ());
            if (block != null && block.getType().isSolid())
            {
                Location teleportLocation = new Location(world, entityLocation.getX(), (yIndex + 1), entityLocation.getZ(), entityLocation.getYaw(), entityLocation.getPitch());
                entity.teleport(teleportLocation);
                return;
            }
        }
        throw new InvalidParameterException(String.format("Entity %s is not in a valid negotiating position (%s)", entity.getName(), entity.getLocation()));
    }

    /**
     * Forces negotiations to immediately stop.
     */
    public void stop()
    {
        this.state = NegotiationState.FINISHED;
        this.updateListeners();
    }

    /**
     * Notifies all subscribed listeners of a state update
     */
    private void updateListeners()
    {
        for (int i = listeners.size() - 1; i >= 0; i--)
            listeners.get(i).onNegotiationStateUpdate(this);
    }
}
