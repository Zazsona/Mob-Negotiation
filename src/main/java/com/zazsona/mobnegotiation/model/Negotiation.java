package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model.action.AttackAction;
import com.zazsona.mobnegotiation.model.action.IAction;
import com.zazsona.mobnegotiation.model.action.IActionListener;
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
    private IAction action;
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
                this.action = new AttackAction(player, mob, playerActionLockListener, mobInvincibilityListener, mobInvalidatedListener);
                this.action.addListener(new IActionListener()
                {
                    @Override
                    public void onActionStart()
                    {
                        state = NegotiationState.ACTIVE_ATTACK;
                        updateListeners();
                    }

                    @Override
                    public void onActionComplete()
                    {
                        action.removeListener(this);
                        stop(NegotiationState.FINISHED_ATTACK);
                    }
                });
                this.action.execute();
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

        this.state = terminatingState;
        MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();

        playerInvalidatedListener.removeListener(playerInvalidatedHandler);
        playerInvalidatedListener.stop();
        playerActionLockListener.stop();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> playerInvincibilityListener.stop(), PluginConfig.getNegotiationDmgGracePeriod());

        mobInvalidatedListener.removeListener(mobInvalidatedHandler);
        mobInvalidatedListener.stop();
        mobActionLockListener.stop();
        mobInvincibilityListener.stop();

        if (action != null && action.isActive())
            action.stop();

        this.updateListeners();
        plugin.getLogger().info(String.format("%s completed negotiation with %s.", player.getName(), mob.getName()));
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
