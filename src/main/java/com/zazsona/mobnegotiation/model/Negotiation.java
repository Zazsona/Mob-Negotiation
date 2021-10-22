package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model.entitystate.EntityActionLockListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvalidatedEventListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvalidatedListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvincibilityListener;
import com.zazsona.mobnegotiation.model.exception.InvalidParticipantsException;
import com.zazsona.mobnegotiation.model.script.NegotiationScript;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptLoader;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptNode;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptResponseNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.logging.Level;

public class Negotiation
{
    private final String POWER_TEXT = "Lend me your power.";
    private final String ITEM_TEXT = "I want items.";
    private final String ATTACK_TEXT = "All Out Attack";
    private final String CANCEL_TEXT = "Return to Battle";

    private String negotiationId;
    private Player player;
    private String playerName;
    private Mob mob;
    private String mobName;
    private PersonalityType mobPersonality;
    private INegotiationEntityEligibilityChecker eligibilityChecker;
    private NegotiationState state;
    private Random rand;
    private ArrayList<NegotiationEventListener> listeners;

    private EntityActionLockListener playerActionLockListener;
    private EntityInvincibilityListener playerInvincibilityListener;
    private EntityInvalidatedListener playerInvalidatedListener;
    private EntityInvalidatedEventListener playerInvalidatedHandler;
    private EntityActionLockListener mobActionLockListener;
    private EntityInvincibilityListener mobInvincibilityListener;
    private EntityInvalidatedListener mobInvalidatedListener;
    private EntityInvalidatedEventListener mobInvalidatedHandler;

    private NegotiationScript script;
    private NegotiationScriptNode scriptNode;
    private NegotiationStage stage;

    public Negotiation(Player player, Mob mob, INegotiationEntityEligibilityChecker eligibilityChecker)
    {
        this.negotiationId = UUID.randomUUID().toString();
        this.player = player;
        this.eligibilityChecker = eligibilityChecker;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.rand = new Random();
        this.listeners = new ArrayList<>();
        this.mobName = (mob.getCustomName() == null) ? mob.getName() : mob.getCustomName();
        this.playerName = player.getName();
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
     * @return the negotiation stage to present
     */
    public NegotiationStage start()
    {
        try
        {
            this.state = NegotiationState.INITIALISING;
            this.updateListeners();

            this.script = NegotiationScriptLoader.loadScript(mob.getType());
            List<NegotiationScriptNode> trees = this.script.getPowerTrees();
            this.scriptNode = trees.get(rand.nextInt(trees.size()));
            initialiseEntities();
        }
        catch (InvalidParticipantsException e)
        {
            // This is an error we can handle without disturbing administrative users
            stop(NegotiationState.FINISHED_ERROR_INITIALISATION_FAILURE);
        }
        catch (Exception e)
        {
            MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while initialising negotiation:", e);
            stop(NegotiationState.FINISHED_ERROR_INITIALISATION_FAILURE);
            return null;
        }

        try
        {
            return beginNegotiation();
        }
        catch (Exception e)
        {
            MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error during negotiation:", e);
            stop(NegotiationState.FINISHED_ERROR_UNKNOWN);
            return null;
        }
    }

    /**
     * Prepares the entities for negotiation, setting their positions and starting listeners
     */
    private void initialiseEntities() throws InvalidParticipantsException
    {
        if (eligibilityChecker.canEntitiesNegotiate(player, mob))
            throw new InvalidParticipantsException();

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
        playerInvalidatedHandler = entity -> stop(NegotiationState.FINISHED_ERROR_ENTITY_LOST);
        playerInvalidatedListener.addListener(playerInvalidatedHandler);
        playerActionLockListener.start();
        playerInvincibilityListener.start();
        playerInvalidatedListener.start();

        mobActionLockListener = new EntityActionLockListener(plugin, mob);
        mobInvincibilityListener = new EntityInvincibilityListener(plugin, mob);
        mobInvalidatedListener = new EntityInvalidatedListener(plugin, mob);
        mobInvalidatedHandler = entity -> stop(NegotiationState.FINISHED_ERROR_ENTITY_LOST);
        mobInvalidatedListener.addListener(mobInvalidatedHandler);
        mobActionLockListener.start();
        mobInvincibilityListener.start();
        mobInvalidatedListener.start();

        // Mob Personality
        long entityIdMsb = mob.getUniqueId().getMostSignificantBits();
        long personalityCode = entityIdMsb % 100;
        if (personalityCode % 2 == 0 && personalityCode < 50)
            this.mobPersonality = PersonalityType.UPBEAT;
        else if (personalityCode % 2 == 0 && personalityCode >= 50)
            this.mobPersonality = PersonalityType.GLOOMY;
        else if (personalityCode % 2 != 0 && personalityCode < 50)
            this.mobPersonality = PersonalityType.IRRITABLE;
        else if (personalityCode % 2 != 0 && personalityCode >= 50)
            this.mobPersonality = PersonalityType.TIMID;
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
    private NegotiationStage beginNegotiation()
    {
        MobNegotiationPlugin.getInstance().getLogger().info(String.format("%s started negotiation with %s.", player.getName(), mob.getName()));
        this.state = NegotiationState.STARTED;
        this.updateListeners();

        String mobMessage = script.getGreetingMessage().getVariant(mobPersonality);
        this.stage = new NegotiationStage(negotiationId, playerName, mobName, mobMessage);
        NegotiationResponse powerResponse = new NegotiationResponse(POWER_TEXT, NegotiationResponseType.SPEECH);
        NegotiationResponse itemResponse = new NegotiationResponse(ITEM_TEXT, NegotiationResponseType.SPEECH);
        NegotiationResponse attackResponse = new NegotiationResponse(ATTACK_TEXT, NegotiationResponseType.ATTACK);
        NegotiationResponse cancelResponse = new NegotiationResponse(CANCEL_TEXT, NegotiationResponseType.CANCEL);
        stage.getResponses().add(powerResponse);
        stage.getResponses().add(itemResponse);
        stage.getResponses().add(attackResponse);
        stage.getResponses().add(cancelResponse);
        return stage;
    }

    public NegotiationStage nextStage(NegotiationResponse response)
    {
        String responseText = response.getText();
        if (state == NegotiationState.STARTED)
        {
            if (responseText.equals(POWER_TEXT))
            {
                NegotiationStage stage = convertScriptNodeToNegotiationStage(scriptNode);
                this.stage = stage;
                this.state = NegotiationState.ACTIVE_POWER;
                this.updateListeners();
                return stage;
            }
            else if (responseText.equals(ITEM_TEXT))
            {
                stop(NegotiationState.FINISHED_ITEM);
            }
            else if (responseText.equals(ATTACK_TEXT))
            {
                attack();
            }
            else if (responseText.equals(CANCEL_TEXT))
            {
                stop(NegotiationState.FINISHED_CANCEL);
            }
        }

        if (state == NegotiationState.ACTIVE_POWER)
        {
            return nextScriptStage(responseText);
        }
        return null;
    }

    private NegotiationStage nextScriptStage(String responseText)
    {
        NegotiationScriptResponseNode selectedResponse = null;
        for (NegotiationScriptResponseNode responseNode : scriptNode.getResponses())
        {
            if (responseNode.getText().equals(responseText))
                selectedResponse = responseNode;
        }

        float roll = rand.nextFloat() * 100;
        if (selectedResponse != null && roll < selectedResponse.getSuccessRates().getVariant(mobPersonality))
        {
            List<NegotiationScriptNode> children = this.scriptNode.getChildren();
            if (children.size() > 0)
            {
                NegotiationScriptNode childNode = children.get(rand.nextInt(children.size()));
                NegotiationStage stage = convertScriptNodeToNegotiationStage(childNode);
                String mobMessage = stage.getMobMessage();
                stage.setMobMessage(selectedResponse.getSuccessResponses().getVariant(mobPersonality) + "\n" + mobMessage);
                this.scriptNode = childNode;
                this.stage = stage;
            }
            else
            {
                String mobMessage = selectedResponse.getSuccessResponses().getVariant(mobPersonality) + "\n" + this.script.getPowerSuccessMessage().getVariant(mobPersonality);
                this.stage = new NegotiationStage(negotiationId, playerName, mobName, mobMessage);
                stop(NegotiationState.FINISHED_POWER);
            }
        }
        else
        {
            String mobMessage = selectedResponse.getFailureResponses().getVariant(mobPersonality);
            this.stage = new NegotiationStage(negotiationId, playerName, mobName, mobMessage);
            stop(NegotiationState.FINISHED_POWER);
        }
        return stage;
    }

    private NegotiationStage convertScriptNodeToNegotiationStage(NegotiationScriptNode scriptNode)
    {
        String mobMessage = scriptNode.getText();
        NegotiationStage stage = new NegotiationStage(negotiationId, playerName, mobName, mobMessage);
        for (NegotiationScriptResponseNode responseNode : scriptNode.getResponses())
            stage.getResponses().add(new NegotiationResponse(responseNode.getText(), NegotiationResponseType.SPEECH));
        stage.getResponses().add(new NegotiationResponse(ATTACK_TEXT, NegotiationResponseType.ATTACK));
        stage.getResponses().add(new NegotiationResponse(CANCEL_TEXT, NegotiationResponseType.CANCEL));
        return stage;
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
        if (state.isTerminating())
            return;

        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();

        playerInvalidatedListener.removeListener(playerInvalidatedHandler);
        playerInvalidatedListener.stop();
        playerActionLockListener.stop();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> playerInvincibilityListener.stop(), PluginConfig.getNegotiationDmgGracePeriod());

        mobInvalidatedListener.removeListener(mobInvalidatedHandler);
        mobInvalidatedListener.stop();
        mobActionLockListener.stop();
        mobInvincibilityListener.stop();

        this.state = terminatingState;
        this.updateListeners();
        plugin.getLogger().info(String.format("%s completed negotiation with %s.", player.getName(), mob.getName()));
    }

    /**
     * Executes the all out attack
     */
    private void attack()
    {
        this.state = NegotiationState.ACTIVE_ATTACK;
        this.updateListeners();

        final Location originalPlayerLoc = player.getLocation();
        final boolean originalPlayerVisibility = player.isInvisible();
        final int ticksInterval = 8; // Do not set this too high, or the anti-cheat fly checker will throw a fit.
        final int slashes = 3;
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
        stop(NegotiationState.FINISHED_ATTACK);
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
}
