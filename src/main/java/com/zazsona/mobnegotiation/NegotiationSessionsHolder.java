package com.zazsona.mobnegotiation;

import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;

/**
 * A singleton that maintains a collection of all active negotiations.
 */
public class NegotiationSessionsHolder
{
    private static NegotiationSessionsHolder instance;
    private HashMap<Player, NegotiationProcess> negotiations;
    private NegotiationEventListener negotiationEventListener;

    public static NegotiationSessionsHolder getInstance()
    {
        if (instance == null)
            instance = new NegotiationSessionsHolder();
        return instance;
    }

    private NegotiationSessionsHolder()
    {
        this.negotiations = new HashMap<>();
        this.negotiationEventListener = negotiation ->
        {
            NegotiationState state = negotiation.getState();
            if (state.isTerminating())
                removeNegotiation(negotiation);
        };
    }

    /**
     * Adds a negotiation to the holder, cancelling and removing any previous entries for the associated player.
     * @param negotiation the negotiation to add
     */
    public void addNegotiation(NegotiationProcess negotiation)
    {
        Player player = negotiation.getPlayer();
        if (negotiations.containsKey(player))
            getNegotiationForPlayer(player).stop();

        negotiation.addEventListener(negotiationEventListener);
        negotiations.put(negotiation.getPlayer(), negotiation);
    }

    /**
     * Removes the provided negotiation from the holder, provided it exists and has been terminated.
     * @param negotiation the negotiation to remove
     * @throws InvalidParameterException negotiation has not reached a terminating state
     */
    public void removeNegotiation(NegotiationProcess negotiation)
    {
        if (!negotiation.getState().isTerminating())
            throw new InvalidParameterException("A negotiation must have terminated before it can be removed.");
        else if (hasNegotiation(negotiation))
        {
            negotiations.remove(negotiation.getPlayer());
            negotiation.removeEventListener(negotiationEventListener);
        }
    }

    /**
     * Checks if this holder contains the provided negotiation
     * @param negotiation the negotiation to check
     * @return true if negotiation is stored
     */
    public boolean hasNegotiation(NegotiationProcess negotiation)
    {
        Player player = negotiation.getPlayer();
        return (negotiations.containsKey(player) && negotiations.get(player) == negotiation);
    }

    /**
     * Checks if this holder contains a negotiation for the specified player
     * @param player the player to check
     * @return true if player has a negotiation
     */
    public boolean hasNegotiationForPlayer(Player player)
    {
        return negotiations.containsKey(player);
    }

    /**
     * Gets a collection of all negotiations in this holder.
     * @return the negotiations
     */
    public Collection<NegotiationProcess> getNegotiations()
    {
        return negotiations.values();
    }

    /**
     * Gets the negotiation associated with the current player
     * @param player the player who is negotiating
     * @return the negotiation, or null if none is available.
     */
    public NegotiationProcess getNegotiationForPlayer(Player player)
    {
        return negotiations.get(player);
    }
}
