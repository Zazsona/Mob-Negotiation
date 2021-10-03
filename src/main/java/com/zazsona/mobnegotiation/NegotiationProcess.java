package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.NegotiationPromptItem.NegotiationPromptItemType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class NegotiationProcess
{
    private Player player;
    private Mob mob;
    private NegotiationState state;
    private Random rand;
    private ArrayList<NegotiationEventListener> listeners;

    private String mobChatTag;

    public NegotiationProcess(Player player, Mob mob)
    {
        this.player = player;
        this.mob = mob;
        this.state = NegotiationState.NONE;
        this.rand = new Random();
        this.listeners = new ArrayList<>();

        String mobName = (mob.getCustomName() == null) ? mob.getName() : mob.getCustomName();
        this.mobChatTag = String.format("<%s>", mobName);
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
        this.state = NegotiationState.INITIALISING;
        this.updateListeners();
        beginNegotiation();
    }

    /**
     * Presents the negotiation UI to the user and marks the negotiation state as "STARTED"
     */
    private void beginNegotiation()
    {
        this.state = NegotiationState.STARTED;
        this.updateListeners();

        List<String> alertMessages = PluginConfig.getNegotiationAlertMessages();
        String alertMessage = alertMessages.get(rand.nextInt(alertMessages.size()));
        String alertFormat = "" + ChatColor.RED + ChatColor.BOLD;
        String formattedAlertMessage = alertFormat + alertMessage;
        player.sendTitle(formattedAlertMessage, null, 2, 30, 7);
        player.sendMessage(String.format("%s %s", mobChatTag, formattedAlertMessage));
        player.sendMessage(String.format("%s Please... Spare me. I'll give you anything.", mobChatTag));

        BaseComponent[] prompt = new NegotiationPromptBuilder(UUID.randomUUID())
                .addItem(new NegotiationPromptItem("Lend me your power.", NegotiationPromptItemType.SPEECH))
                .addItem(new NegotiationPromptItem("I want items.", NegotiationPromptItemType.SPEECH))
                .addItem(new NegotiationPromptItem("Attack", NegotiationPromptItemType.ATTACK))
                .addItem(new NegotiationPromptItem("Return to battle", NegotiationPromptItemType.CANCEL))
                .build();

        player.spigot().sendMessage(ChatMessageType.CHAT, prompt);
        Bukkit.getScheduler().runTaskLater(MobNegotiationPlugin.getInstance(), this::stop, 80);
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
