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

    /**
     * Executes the all out attack
     */
    private void attack()
    {
        final Location originalPlayerLoc = player.getLocation();
        final boolean originalPlayerVisibility = player.isInvisible();
        final int ticksInterval = 8; // Do not set this too high, or the anti-cheat fly checker will throw a fit.
        //final int minSlashes = 3;
        //final int maxSlashes = 3;
        final int slashes = 3;//r.nextInt((maxSlashes - minSlashes) + 1) + minSlashes;
        final double offsetMaxHorizRange = 2.0f;
        final double offsetMinHorizRange = 0.75f;
        final double offsetMaxVertRange = mob.getHeight() * 1.5f;
        final double offsetMinVertRange = 0.25f;
        player.setInvisible(true);
        for (int i = 0; i < slashes; i++)
            Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () -> runAttackSlash(ticksInterval, offsetMaxHorizRange, offsetMinHorizRange, offsetMaxVertRange, offsetMinVertRange), i * ticksInterval);

        Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), () -> runFinalAttackSlash(originalPlayerLoc, originalPlayerVisibility), slashes * ticksInterval);
    }

    /**
     * Performs a "slash" animation for the All Out Attack
     * @param tickTime the time in which to animate the slash
     * @param offsetMaxHorizRange the maximum horizontal range of the slash from the mob
     * @param offsetMinHorizRange the minimum horizontal range of the slash from the mob
     * @param offsetMaxVertRange the maximum vertical range of the slash from the mob
     * @param offsetMinVertRange the minimum vertical range of the slash from the mob
     */
    private void runAttackSlash(int tickTime, double offsetMaxHorizRange, double offsetMinHorizRange, double offsetMaxVertRange, double offsetMinVertRange)
    {
        Random r = new Random();
        World world = mob.getWorld();
        Location mobLocation = mob.getLocation();
        Location mobMidpoint = mobLocation.clone();
        mobMidpoint.setY(mobMidpoint.getY() + (mob.getHeight() / 2.0f));

        Location playerLocation = player.getLocation();
        Location playerMidpoint = playerLocation.clone();
        playerMidpoint.setY(playerMidpoint.getY() + (player.getHeight() / 2.0f));

        float mobYaw = mobLocation.getYaw();
        float directionRadius = 45;
        boolean negativeX = playerLocation.getX() > mobLocation.getX();
        boolean negativeZ = playerLocation.getZ() > mobLocation.getZ();
        if ((mobYaw < 90 + directionRadius && mobYaw > 90 - directionRadius) || (mobYaw < 270 + directionRadius && mobYaw > 270 - directionRadius))
            negativeX = r.nextBoolean();
        else                                // Make sure we always have an opposing side for better visuals, but still random for variety
            negativeZ = r.nextBoolean();
        double xOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (negativeX ? -1 : 1); // Generate a random number in the range, then randomise negative flip.
        double yOffset = ((r.nextFloat() * (offsetMaxVertRange - offsetMinVertRange)) + offsetMinVertRange); // No negatives to prevent underground values
        double zOffset = ((r.nextFloat() * (offsetMaxHorizRange - offsetMinHorizRange)) + offsetMinHorizRange) * (negativeZ ? -1 : 1); // Generate a random number in the range, then randomise negative flip.
        Location attackLocation = new Location(mobLocation.getWorld(), mobLocation.getX() + xOffset, mobLocation.getY() + yOffset, mobLocation.getZ() + zOffset);

        Location targetPlayerLoc = attackLocation.clone();
        targetPlayerLoc.setY(targetPlayerLoc.getY() - (player.getHeight() / 2.0f));
        Vector gradientDirection = mobLocation.toVector().subtract(targetPlayerLoc.toVector());
        targetPlayerLoc.setDirection(gradientDirection);

        Location targetPlayerMidpoint = targetPlayerLoc.clone();
        targetPlayerMidpoint.setY(targetPlayerMidpoint.getY() + (player.getHeight() / 2.0f));

        world.spawnParticle(Particle.REDSTONE, attackLocation, 5, 0.3f, 0.3f, 0.3f, new Particle.DustOptions(Color.GRAY, 2.0f));
        world.spawnParticle(Particle.EXPLOSION_LARGE, mobMidpoint, 1);
        world.playEffect(attackLocation, Effect.BLAZE_SHOOT, null);
        drawAttackLine(tickTime, 0.1f, world, playerMidpoint.toVector(), mobMidpoint.toVector(), targetPlayerMidpoint.toVector());
        playerActionLockListener.setLockedLocation(targetPlayerLoc);
        player.teleport(targetPlayerLoc);
    }

    /**
     * Performs the final slash of the All Out Attack, killing the mob and resetting the player.
     * @param playerLocation the position to return the player to
     * @param visibilityState the visibility state to set the player to
     */
    private void runFinalAttackSlash(Location playerLocation, boolean visibilityState)
    {
        World world = mob.getWorld();
        Location mobLocation = mob.getLocation();
        Location mobMidpoint = mobLocation.clone();
        mobMidpoint.setY(mobMidpoint.getY() + (mob.getHeight() / 2.0f));

        mobInvincibilityListener.stop();
        mobInvalidatedListener.stop();
        mob.damage(mob.getHealth());
        world.spawnParticle(Particle.EXPLOSION_LARGE, mobMidpoint, 1);
        world.playSound(mobMidpoint, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        Vector gradientDirection = mobLocation.toVector().subtract(playerLocation.toVector());
        playerLocation.setDirection(gradientDirection);
        playerActionLockListener.setLockedLocation(playerLocation);
        player.teleport(playerLocation);
        player.setInvisible(visibilityState);
        stop();
    }

    /**
     * Draws the line effect for an ALl Out Attack
     * @param drawTimeTicks the time it takes to draw the line over all points
     * @param particleStep a step value between 0.0 and 1.0 for how frequently particles should be placed
     * @param world the world to spawn in
     * @param points the vectors defining the line
     */
    private void drawAttackLine(int drawTimeTicks, double particleStep, World world, Vector... points)
    {
        for (int i = 0; i < points.length - 1; i++)
        {
            Vector lineStart = points[i];
            Vector lineEnd = points[i + 1];
            Vector lineDifference = lineEnd.clone().subtract(lineStart);
            int ticksPerStep = Math.round((float) (drawTimeTicks * particleStep));
            for (double step = 0.0f; step < 1.0f; step += particleStep)
            {
                Vector position = lineDifference.clone().multiply(step).add(lineStart);
                Bukkit.getScheduler().runTaskLater(
                        MobNegotiationPlugin.getInstance(),
                        () -> world.spawnParticle(Particle.REDSTONE, position.getX(), position.getY(), position.getZ(), 1, new Particle.DustOptions(Color.BLACK, 2.0f)),
                        ticksPerStep);
            }
        }
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
                case CANCEL -> stop();
            }
        }
    }
}
