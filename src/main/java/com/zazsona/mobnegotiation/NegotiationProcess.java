package com.zazsona.mobnegotiation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.logging.Level;

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
        this.state = NegotiationState.INITIALISING;
        this.updateListeners();

        Location playerLocation = player.getLocation();
        Location mobLocation = mob.getLocation();
        Vector gradientDirection = playerLocation.toVector().subtract(mobLocation.toVector());
        mobLocation.setDirection(gradientDirection);
        mobLocation.setPitch(50); // Sad expression

        positionEntityAtLocation(player, playerLocation);
        positionEntityAtLocation(mob, mobLocation);
        mob.setTarget(null);

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
     * @param location the approximate location the entity should be
     * @throws InvalidParameterException no valid position could be found for the entity
     */
    private void positionEntityAtLocation(Entity entity, Location location)
    {
        World world = entity.getWorld();
        int entityY = location.getBlockY();
        for (int yIndex = entityY; yIndex >= player.getWorld().getMinHeight(); yIndex--)
        {
            Block block = world.getBlockAt(location.getBlockX(), yIndex, location.getBlockZ());
            if (block != null && block.getType().isSolid())
            {
                Location teleportLocation = new Location(world, location.getX(), (yIndex + 1), location.getZ(), location.getYaw(), location.getPitch());
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
        {
            try
            {
                listeners.get(i).onNegotiationStateUpdate(this);
            }
            catch (Exception e)
            {
                MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while handling negotiation listener:", e);
            }

        }

    }
}
