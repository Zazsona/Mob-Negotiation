package com.zazsona.mobnegotiation.repository;

import com.zazsona.mobnegotiation.model.NegotiationEventListener;
import com.zazsona.mobnegotiation.model.Negotiation;
import com.zazsona.mobnegotiation.model.NegotiationState;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;

/**
 * A singleton that maintains a collection of all active negotiations.
 */
public class NegotiationRepository implements INegotiationRepository
{
    private HashMap<String, Negotiation> negotiations;
    private NegotiationEventListener negotiationEventListener;

    public NegotiationRepository()
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
    @Override
    public void addNegotiation(Negotiation negotiation)
    {
        if (negotiations.containsKey(negotiation.getNegotiationId()))
            getNegotiation(negotiation.getNegotiationId()).stop();

        negotiation.addEventListener(negotiationEventListener);
        negotiations.put(negotiation.getNegotiationId(), negotiation);
    }

    /**
     * Removes the provided negotiation from the holder, provided it exists and has been terminated.
     * @param negotiation the negotiation to remove
     * @throws InvalidParameterException negotiation has not reached a terminating state
     */
    @Override
    public void removeNegotiation(Negotiation negotiation)
    {
        if (!negotiation.getState().isTerminating())
            throw new InvalidParameterException("A negotiation must be terminated before it can be removed.");
        else if (hasNegotiation(negotiation.getNegotiationId()))
        {
            negotiations.remove(negotiation.getNegotiationId());
            negotiation.removeEventListener(negotiationEventListener);
        }
    }

    /**
     * Gets the negotiatyion defined by the id
     * @param negotiationId the negotiation to get
     * @return the negotiation, or null if none is found
     */
    @Override
    public Negotiation getNegotiation(String negotiationId)
    {
        return negotiations.get(negotiationId);
    }

    /**
     * Checks if this holder contains the provided negotiation
     * @param negotiationId the negotiation to check
     * @return true if negotiation is stored
     */
    @Override
    public boolean hasNegotiation(String negotiationId)
    {
        return negotiations.get(negotiationId) != null;
    }

    /**
     * Checks if this holder contains a negotiation for the specified player
     * @param player the player to check
     * @return true if player has a negotiation
     */
    @Override
    public boolean hasNegotiationForPlayer(Player player)
    {
        return getNegotiationForPlayer(player) != null;
    }

    /**
     * Gets a collection of all negotiations in this holder.
     * @return the negotiations
     */
    @Override
    public Collection<Negotiation> getNegotiations()
    {
        return negotiations.values();
    }

    /**
     * Gets the negotiation associated with the current player
     * @param player the player who is negotiating
     * @return the negotiation, or null if none is available.
     */
    @Override
    public Negotiation getNegotiationForPlayer(Player player)
    {
        for (Negotiation negotiation : negotiations.values())
        {
            if (negotiation.getPlayer() == player)
                return negotiation;
        }
        return null;
    }
}
