package com.zazsona.mobnegotiation;

import com.google.gson.Gson;
import com.zazsona.mobnegotiation.NegotiationPromptItem.NegotiationPromptItemType;
import com.zazsona.mobnegotiation.command.NegotiationSelectionListener;
import com.zazsona.mobnegotiation.command.NegotiationResponseCommand;
import com.zazsona.mobnegotiation.entitystate.EntityActionLockListener;
import com.zazsona.mobnegotiation.entitystate.EntityInvalidatedListener;
import com.zazsona.mobnegotiation.entitystate.EntityInvincibilityListener;
import com.zazsona.mobnegotiation.script.NegotiationScript;
import com.zazsona.mobnegotiation.script.NegotiationScriptLoader;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class NegotiationProcess implements NegotiationSelectionListener
{
    private String negotiationId;
    private Player player;
    private NegotiationResponseCommand responseCommand;
    private Mob mob;
    private NegotiationState state;
    private Random rand;
    private ArrayList<NegotiationEventListener> listeners;

    private EntityActionLockListener playerActionLockListener;
    private EntityInvincibilityListener playerInvincibilityListener;
    private EntityInvalidatedListener playerInvalidatedListener;
    private EntityActionLockListener mobActionLockListener;
    private EntityInvincibilityListener mobInvincibilityListener;
    private EntityInvalidatedListener mobInvalidatedListener;

    private NegotiationPrompt currentPrompt;
    private NegotiationScript script;
    private String mobChatTag;

    public NegotiationProcess(Player player, Mob mob, NegotiationResponseCommand responseCommand)
    {
        this.negotiationId = UUID.randomUUID().toString();
        this.player = player;
        this.responseCommand = responseCommand;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.rand = new Random();
        this.listeners = new ArrayList<>();

        String mobName = (mob.getCustomName() == null) ? mob.getName() : mob.getCustomName();
        this.mobChatTag = String.format("<%s>", mobName);
    }

    /**
     * Gets the unique ID for this negotiation instance
     * @return the id
     */
    public String getNegotiationId()
    {
        return negotiationId;
    }

    /**
     * Gets the current {@link NegotiationPrompt}
     * @return the prompt, or null if none has been created
     */
    public NegotiationPrompt getCurrentPrompt()
    {
        return currentPrompt;
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
     * @throws InvalidParameterException Negotiating entity is unable to negotiate
     */
    public void start()
    {
        try
        {
            this.state = NegotiationState.INITIALISING;
            this.updateListeners();
            initialiseEntities();
            beginNegotiation();
        }
        catch (IOException e)
        {
            MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while initialising negotiation:", e);
            stop(NegotiationState.FINISHED_ERROR_INITIALISATION_FAILURE);
        }
    }

    /**
     * Prepares the entities for negotiation, setting their positions and starting listeners
     */
    private void initialiseEntities() throws IOException
    {

        // Positioning
        Location playerLocationTarget = player.getLocation();
        Location mobLocationTarget = mob.getLocation();
        Vector gradientDirection = playerLocationTarget.toVector().subtract(mobLocationTarget.toVector());
        mobLocationTarget.setDirection(gradientDirection);
        mobLocationTarget.setPitch(50); // Sad expression
        positionEntityAtNegotiationLocation(player, playerLocationTarget);
        positionEntityAtNegotiationLocation(mob, mobLocationTarget);

        // State Maintenance
        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
        playerActionLockListener = new EntityActionLockListener(plugin, player);
        playerInvincibilityListener = new EntityInvincibilityListener(plugin, player);
        playerInvalidatedListener = new EntityInvalidatedListener(plugin, player);
        playerInvalidatedListener.addListener(entity -> stop(NegotiationState.FINISHED_ERROR_ENTITY_LOST));
        playerActionLockListener.start();
        playerInvincibilityListener.start();
        playerInvalidatedListener.start();

        mobActionLockListener = new EntityActionLockListener(plugin, mob);
        mobInvincibilityListener = new EntityInvincibilityListener(plugin, mob);
        mobInvalidatedListener = new EntityInvalidatedListener(plugin, mob);
        mobInvalidatedListener.addListener(entity -> stop(NegotiationState.FINISHED_ERROR_ENTITY_LOST));
        mobActionLockListener.start();
        mobInvincibilityListener.start();
        mobInvalidatedListener.start();

        // UI
        script = NegotiationScriptLoader.loadScript(mob.getType());
        responseCommand.addListener(this);
    }

    /**
     * Teleports the entity to a valid position within their current X/Y co-ordinates for negotiation.
     * @param entity the entity to position
     * @param location the approximate location the entity should be
     * @throws InvalidParameterException no valid position could be found for the entity
     */
    private void positionEntityAtNegotiationLocation(Entity entity, Location location)
    {
        World world = entity.getWorld();
        int entityY = location.getBlockY();
        for (int yIndex = entityY; yIndex >= entity.getWorld().getMinHeight(); yIndex--)
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
     * Presents the negotiation UI to the user and marks the negotiation state as "STARTED"
     */
    private void beginNegotiation()
    {
        MobNegotiationPlugin.getInstance().getLogger().info(String.format("%s started negotiation with %s.", player.getName(), mob.getName()));
        this.state = NegotiationState.STARTED;
        this.updateListeners();

        List<String> alertMessages = PluginConfig.getNegotiationAlertMessages();
        String alertMessage = alertMessages.get(rand.nextInt(alertMessages.size()));
        String alertFormat = "" + ChatColor.RED + ChatColor.BOLD;
        String formattedAlertMessage = alertFormat + alertMessage;
        player.sendTitle(formattedAlertMessage, null, 2, 30, 7);
        player.sendMessage(String.format("%s %s", mobChatTag, formattedAlertMessage));
        player.sendMessage(String.format("%s %s", mobChatTag, script.getGreetingMessage().getUpbeat()));

        this.currentPrompt = new NegotiationPrompt();
        this.currentPrompt
                .addItem(new NegotiationPromptItem("Lend me your power.", NegotiationPromptItemType.SPEECH))
                .addItem(new NegotiationPromptItem("I want items.", NegotiationPromptItemType.SPEECH))
                .addItem(new NegotiationPromptItem("All Out Attack", NegotiationPromptItemType.ATTACK))
                .addItem(new NegotiationPromptItem("Return to battle", NegotiationPromptItemType.CANCEL));

        player.spigot().sendMessage(ChatMessageType.CHAT, this.currentPrompt.getFormattedComponent(negotiationId));
        //Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () -> stop(NegotiationState.FINISHED_CANCEL), 80);
    }

    /**
     * Forces negotiations to immediately stop with the "FINISHED_CANCEL" state.
     */
    public void stop()
    {
        stop(NegotiationState.FINISHED_CANCEL);
    }

    /**
     * Forces negotiations to immediately stop and reports the provided state
     * @param terminatingState the state to inform as the termination reason. This should only be states where isTerminating() is true.
     */
    private void stop(NegotiationState terminatingState)
    {
        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();

        playerInvalidatedListener.stop();
        playerActionLockListener.stop();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> playerInvincibilityListener.stop(), PluginConfig.getNegotiationDmgGracePeriod());

        mobInvalidatedListener.stop();
        mobActionLockListener.stop();
        mobInvincibilityListener.stop();
        responseCommand.removeListener(this);

        this.state = terminatingState;
        this.updateListeners();
        plugin.getLogger().info(String.format("%s completed negotiation with %s.", player.getName(), mob.getName()));
    }

    private void attack()
    {
        mobInvincibilityListener.stop();
        Random r = new Random();
        final Location originalPlayerLoc = player.getLocation();
        final int ticksInterval = 8; // Do not set this too high, or the anti-cheat fly checker will throw a fit.
        final int minParticles = 3;
        final int maxParticles = 4;
        final int particleCount = r.nextInt((maxParticles - minParticles) + 1) + minParticles;
        final double offsetMaxHorizRange = 2.0f;
        final double offsetMinHorizRange = 0.5f;
        final double offsetMaxVertRange = mob.getHeight() * 1.5f;
        final double offsetMinVertRange = 0.25f;
        for (int i = 0; i < particleCount; i++)
        {
            Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () ->
            {

                World world = mob.getWorld();
                Location mobLocation = mob.getLocation();
                double xOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (r.nextBoolean() ? 1 : -1); // Generate a random number in the range, then randomise negative flip.
                double yOffset = ((r.nextFloat() * (offsetMaxVertRange - offsetMinVertRange)) + offsetMinVertRange); // No negatives to prevent underground values
                double zOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (r.nextBoolean() ? 1 : -1); // Generate a random number in the range, then randomise negative flip.
                Location location = new Location(mobLocation.getWorld(), mobLocation.getX() + xOffset, mobLocation.getY() + yOffset, mobLocation.getZ() + zOffset);
                world.spawnParticle(Particle.EXPLOSION_LARGE, location, 1);
                world.playEffect(location, Effect.BLAZE_SHOOT, null);

                Location playerLocation = location.clone();
                playerLocation.setY(playerLocation.getY() - (player.getHeight() / 2.0f));
                Vector gradientDirection = mobLocation.toVector().subtract(playerLocation.toVector());
                playerLocation.setDirection(gradientDirection);
                playerActionLockListener.setLockedLocation(playerLocation);
                player.teleport(playerLocation);
            }, i * ticksInterval);
        }

        Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () ->
        {
            World world = mob.getWorld();
            Location mobLocation = mob.getLocation();
            double yOffset = (mob.getHeight() / 2.0f);
            Location location = new Location(mobLocation.getWorld(), mobLocation.getX(), mobLocation.getY() + yOffset, mobLocation.getZ());

            mob.damage(mob.getHealth());
            world.spawnParticle(Particle.EXPLOSION_LARGE, location, 1);
            world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

            Vector gradientDirection = mobLocation.toVector().subtract(originalPlayerLoc.toVector());
            originalPlayerLoc.setDirection(gradientDirection);
            playerActionLockListener.setLockedLocation(originalPlayerLoc);
            player.teleport(originalPlayerLoc);
        }, particleCount * ticksInterval);
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

    @Override
    public void onNegotiationItemSelected(NegotiationProcess negotiation, NegotiationPrompt prompt, NegotiationPromptItem item)
    {
        if (negotiation == this && prompt == currentPrompt && currentPrompt.hasItem(item))
        {
            switch (item.getItemType())
            {
                case SPEECH -> stop(NegotiationState.FINISHED_POWER);
                case ATTACK -> attack();
                case CANCEL -> stop(NegotiationState.FINISHED_CANCEL);
            }
        }
    }
}
