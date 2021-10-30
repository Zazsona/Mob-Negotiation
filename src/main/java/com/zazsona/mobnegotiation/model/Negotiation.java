package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model.action.*;
import com.zazsona.mobnegotiation.model.entitystate.EntityActionLockListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvalidatedEventListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvalidatedListener;
import com.zazsona.mobnegotiation.model.entitystate.EntityInvincibilityListener;
import com.zazsona.mobnegotiation.model.exception.InvalidParticipantsException;
import com.zazsona.mobnegotiation.model.script.NegotiationScript;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptLoader;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptNode;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptResponseNode;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.naming.ConfigurationException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.logging.Level;

public class Negotiation
{
    protected final String POWER_TEXT = "Lend me your power.";
    protected final String ITEM_TEXT = "I want items.";
    protected final String ATTACK_TEXT = "All Out Attack";
    protected final String CANCEL_TEXT = "Return to Battle";

    protected final String ACCEPT_OFFER_TEXT = "I'll take it.";
    protected final String DENY_OFFER_TEXT = "You can do better than that.";

    protected String negotiationId;
    protected Player player;
    protected Mob mob;
    protected PersonalityType mobPersonality;
    protected NegotiationState state;
    protected IAction action;
    protected NegotiationPrompt prompt;
    protected NegotiationScript script;
    protected INegotiationEntityEligibilityChecker eligibilityChecker;
    protected ICooldownRespository cooldownRespository;
    protected ArrayList<NegotiationStateListener> stateListeners;
    protected ArrayList<NegotiationPromptUpdateListener> promptUpdateListeners;

    protected EntityActionLockListener playerActionLockListener;
    protected EntityInvincibilityListener playerInvincibilityListener;
    protected EntityInvalidatedListener playerInvalidatedListener;
    protected EntityInvalidatedEventListener playerInvalidatedHandler;
    protected EntityActionLockListener mobActionLockListener;
    protected EntityInvincibilityListener mobInvincibilityListener;
    protected EntityInvalidatedListener mobInvalidatedListener;
    protected EntityInvalidatedEventListener mobInvalidatedHandler;

    public Negotiation(Player player, Mob mob, INegotiationEntityEligibilityChecker eligibilityChecker, ICooldownRespository cooldownRespository)
    {
        this.negotiationId = UUID.randomUUID().toString();
        this.player = player;
        this.eligibilityChecker = eligibilityChecker;
        this.cooldownRespository = cooldownRespository;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.stateListeners = new ArrayList<>();
        this.promptUpdateListeners = new ArrayList<>();

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
     * Gets the current action
     * @return the action, or null if none has been selected
     */
    public IAction getAction()
    {
        return action;
    }

    /**
     * Gets mobPersonality
     * @return the personality of the mob
     */
    public PersonalityType getMobPersonality()
    {
        return mobPersonality;
    }

    /**
     * Gets the current prompt
     * @return the prompt, containing the mob message and accepted responses, or null if negotiation has not begun.
     */
    public NegotiationPrompt getCurrentPrompt()
    {
        return prompt;
    }

    /**
     * Adds a listener to handle negotiation state change events
     * @param listener the listener to add
     * @return boolean on success
     */
    public boolean addListener(NegotiationStateListener listener)
    {
        return stateListeners.add(listener);
    }

    /**
     * Removes a listener from handling negotiation state change events
     * @param listener the listener to remove
     * @return boolean on success
     */
    public boolean removeListener(NegotiationStateListener listener)
    {
        return stateListeners.remove(listener);
    }

    /**
     * Adds a listener to handle negotiation prompt update events
     * @param listener the listener to add
     * @return boolean on success
     */
    public boolean addListener(NegotiationPromptUpdateListener listener)
    {
        return promptUpdateListeners.add(listener);
    }

    /**
     * Removes a listener from handling negotiation prompt update events
     * @param listener the listener to remove
     * @return boolean on success
     */
    public boolean removeListener(NegotiationPromptUpdateListener listener)
    {
        return promptUpdateListeners.remove(listener);
    }

    /**
     * Initialises the negotiation process and, if successful, presents the UI to the player.
     * @return true on successful initialisation
     */
    public boolean start()
    {
        try
        {
            this.state = NegotiationState.INITIALISING;
            this.updateStateListeners();

            this.script = NegotiationScriptLoader.loadScript(mob.getType());
            initialiseEntities();
            beginNegotiation();
            return true;
        }
        catch (InvalidParticipantsException e)
        {
            // This is an error we can handle without disturbing administrative users
            if (mob != null && mob.isValid())
                mob.setTarget(player);
            stop(NegotiationState.FINISHED_ERROR_INITIALISATION_FAILURE);
            return false;
        }
        catch (Exception e)
        {
            if (mob != null && mob.isValid())
                mob.setTarget(player);
            MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while initialising negotiation:", e);
            player.sendMessage(ChatColor.RED+"Unable to start mob negotiation. Please contact a server administrator to check the logs.");
            stop(NegotiationState.FINISHED_ERROR_INITIALISATION_FAILURE);
            return false;
        }
    }

    /**
     * Prepares the entities for negotiation, setting their positions and starting listeners
     */
    protected void initialiseEntities() throws InvalidParticipantsException
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
     * @throws ConfigurationException all options are disabled
     */
    protected void beginNegotiation() throws ConfigurationException
    {
        MobNegotiationPlugin.getInstance().getLogger().info(String.format("%s started negotiation with %s.", player.getName(), mob.getName()));
        this.state = NegotiationState.STARTED;
        this.updateStateListeners();

        String mobMessage = script.getGreetingMessage().getVariant(mobPersonality);
        ArrayList<NegotiationResponse> responses = new ArrayList<>();
        if (PluginConfig.isPowerNegotiationEnabled())
            responses.add(new NegotiationResponse(POWER_TEXT, NegotiationResponseType.SPEECH));
        if (PluginConfig.isItemNegotiationEnabled())
            responses.add(new NegotiationResponse(ITEM_TEXT, NegotiationResponseType.SPEECH));
        if (PluginConfig.isAllOutAttackEnabled())
            responses.add(new NegotiationResponse(ATTACK_TEXT, NegotiationResponseType.ATTACK));
        if (responses.size() > 0)
            responses.add(new NegotiationResponse(CANCEL_TEXT, NegotiationResponseType.CANCEL));

        if (responses.size() > 0)
        {
            this.prompt = new NegotiationPrompt(mobMessage, Mood.NEUTRAL, responses);
            updatePromptListeners(this.prompt);
        }
        else
            throw new ConfigurationException("All negotiation options are disabled.");
    }

    public void nextPrompt(NegotiationResponse response)
    {
        String responseText = response.getText();
        if (!state.isTerminating() && responseText.equals(CANCEL_TEXT))
        {
            stop(NegotiationState.FINISHED_CANCEL);
        }
        else if (state == NegotiationState.STARTED)
        {
            if (responseText.equals(POWER_TEXT) && PluginConfig.isPowerNegotiationEnabled())
            {
                this.action = createPowerNegotiationAction();
                this.action.execute();
                this.prompt = convertScriptNodeToPrompt(((PowerNegotiationAction) this.action).getCurrentNode());
                updatePromptListeners(this.prompt);
            }
            else if (responseText.equals(ITEM_TEXT) && PluginConfig.isItemNegotiationEnabled())
            {
                this.action = createItemNegotiationAction();
                this.action.execute();
            }
            else if (responseText.equals(ATTACK_TEXT) && PluginConfig.isAllOutAttackEnabled())
            {
                this.action = createAttackAction();
                this.action.execute();
            }
        }
        else if (state == NegotiationState.ACTIVE_POWER && action instanceof PowerNegotiationAction)
        {
            PowerNegotiationAction powerNegotiationAction = (PowerNegotiationAction) action;
            powerNegotiationAction.nextNode(responseText);
        }
        else if (state == NegotiationState.ACTIVE_ITEM && action instanceof ItemNegotiationAction)
        {
            ItemNegotiationAction itemNegotiationAction = (ItemNegotiationAction) action;
            if (responseText.equals(ACCEPT_OFFER_TEXT))
                itemNegotiationAction.acceptOffer();
            else if (responseText.equals(DENY_OFFER_TEXT))
                itemNegotiationAction.denyOffer();
        }
        else if (this.prompt != null)
        {
            this.prompt = null;
            updatePromptListeners(null);
        }
    }

    /**
     * Creates a new {@link AttackAction} with relevant listeners for updating negotiation state.
     * @return the action
     */
    private AttackAction createAttackAction()
    {
        AttackAction attackAction = new AttackAction(player, mob, playerActionLockListener, mobInvincibilityListener, mobInvalidatedListener);
        attackAction.addListener(new IActionListener()
        {
            @Override
            public void onActionStart(IAction action)
            {
                state = NegotiationState.ACTIVE_ATTACK;
                updateStateListeners();
            }

            @Override
            public void onActionComplete(IAction action)
            {
                action.removeListener(this);
                stop(NegotiationState.FINISHED_ATTACK);
            }
        });
        return attackAction;
    }

    /**
     * Creates a new {@link PowerNegotiationAction} with relevant listeners for updating negotiation state.
     * @return the action
     */
    private PowerNegotiationAction createPowerNegotiationAction()
    {
        PowerNegotiationAction powerNegotiationAction = new PowerNegotiationAction(player, mob, script, mobPersonality);
        powerNegotiationAction.addListener(new IPowerNegotiationActionListener()
        {
            @Override
            public void onActionStart(IAction action)
            {
                state = NegotiationState.ACTIVE_POWER;
                updateStateListeners();
            }

            @Override
            public void onNodeLoaded(NegotiationScriptNode node)
            {
                if (node != null)
                {
                    prompt = convertScriptNodeToPrompt(node);
                    updatePromptListeners(prompt);
                }
            }

            @Override
            public void onActionComplete(IAction action)
            {
                PowerNegotiationAction pnAction = (PowerNegotiationAction) action;
                pnAction.removeListener(this);
                boolean powerGiven = pnAction.getGivenPowers().size() > 0;
                if (powerGiven)
                    executeMobExit();
                stop(NegotiationState.FINISHED_POWER);
            }
        });
        return powerNegotiationAction;
    }

    /**
     * Creates a new {@link ItemNegotiationAction} with relevant listeners for updating negotiation state.
     * @return the action
     */
    private ItemNegotiationAction createItemNegotiationAction()
    {
        ItemNegotiationAction itemNegotiationAction = new ItemNegotiationAction(player, mob);
        itemNegotiationAction.addListener(new IItemNegotiationActionListener()
        {
            private boolean initialOfferMade = true;
            private OfferState offerState;

            @Override
            public void onActionStart(IAction action)
            {
                state = NegotiationState.ACTIVE_ITEM;
                initialOfferMade = false;
                updateStateListeners();
            }

            @Override
            public void onOfferUpdated(ItemNegotiationAction action, OfferState state, ItemStack offeredStack)
            {
                offerState = state;
                if (state == OfferState.PENDING)
                {
                    String mobMessage = (initialOfferMade) ? script.getFurtherItemOfferMessage().getVariant(mobPersonality) : script.getInitialItemOfferMessage().getVariant(mobPersonality);
                    initialOfferMade = true;
                    ArrayList<NegotiationResponse> responses = new ArrayList<>();
                    responses.add(new NegotiationResponse(ACCEPT_OFFER_TEXT, NegotiationResponseType.SPEECH));
                    responses.add(new NegotiationResponse(DENY_OFFER_TEXT, NegotiationResponseType.SPEECH));
                    responses.add(new NegotiationResponse(CANCEL_TEXT, NegotiationResponseType.CANCEL));
                    prompt = new NegotiationPrompt(mobMessage, Mood.NEUTRAL, responses);
                    updatePromptListeners(prompt);
                }
            }

            @Override
            public void onActionComplete(IAction action)
            {
                action.removeListener(this);
                if (offerState == OfferState.ACCEPTED)
                {
                    String mobMessage = script.getAcceptedItemOfferMessage().getVariant(mobPersonality);
                    prompt = new NegotiationPrompt(mobMessage, Mood.HAPPY, null);
                    updatePromptListeners(prompt);
                    executeMobExit();
                }
                else if (offerState == OfferState.DENIED)
                {
                    String mobMessage = script.getRefuseItemDemandMessage().getVariant(mobPersonality);
                    prompt = new NegotiationPrompt(mobMessage, Mood.ANGRY, null);
                    updatePromptListeners(prompt);
                }
                stop(NegotiationState.FINISHED_ITEM);
            }
        });
        return itemNegotiationAction;
    }

    /**
     * Removes the mob associated with this negotiation with particle animations.
     */
    protected void executeMobExit()
    {
        World world = mob.getWorld();
        Location particleLocation = mob.getLocation();
        particleLocation.setY(particleLocation.getY() + (mob.getHeight() / 2.0f));
        mob.remove();
        world.spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.5f, 0.5f, 0.5f, new Particle.DustOptions(Color.WHITE, 5.0f));
        world.playSound(particleLocation, Sound.BLOCK_SAND_FALL, 1.0f, 1.0f);
    }

    protected NegotiationPrompt convertScriptNodeToPrompt(NegotiationScriptNode scriptNode)
    {
        ArrayList<NegotiationResponse> responses = new ArrayList<>();
        if (scriptNode.getResponses() != null)
        {
            for (NegotiationScriptResponseNode responseNode : scriptNode.getResponses())
                responses.add(new NegotiationResponse(responseNode.getText(), NegotiationResponseType.SPEECH));
            responses.add(new NegotiationResponse(CANCEL_TEXT, NegotiationResponseType.CANCEL));
        }
        return new NegotiationPrompt(scriptNode.getText(), scriptNode.getMood(), responses);
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
    protected void stop(NegotiationState terminatingState)
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

        if (!terminatingState.isErroneous())
            cooldownRespository.setCooldown(player, PluginConfig.getNegotiationCooldownTicks());

        this.updateStateListeners();
        plugin.getLogger().info(String.format("%s completed negotiation with %s.", player.getName(), mob.getName()));
    }

    /**
     * Notifies all subscribed listeners of a state update
     */
    private void updateStateListeners()
    {
        for (int i = stateListeners.size() - 1; i >= 0; i--)
        {
            try
            {
                stateListeners.get(i).onNegotiationStateUpdate(this);
            }
            catch (Exception e)
            {
                MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while handling negotiation listener:", e);
            }
        }
    }

    /**
     * Notifies all subscribed listeners of a prompt update
     */
    private void updatePromptListeners(NegotiationPrompt prompt)
    {
        for (int i = promptUpdateListeners.size() - 1; i >= 0; i--)
        {
            try
            {
                promptUpdateListeners.get(i).onNegotiationPromptChanged(this, prompt);
            }
            catch (Exception e)
            {
                MobNegotiationPlugin.getInstance().getLogger().log(Level.SEVERE, "Error while handling negotiation listener:", e);
            }
        }
    }
}
