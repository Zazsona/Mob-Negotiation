package com.zazsona.mobnegotiation.repository;

import com.zazsona.mobnegotiation.model.Negotiation;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface INegotiationRepository
{
    void addNegotiation(Negotiation negotiation);

    void removeNegotiation(Negotiation negotiation);

    Negotiation getNegotiation(String negotiationId);

    boolean hasNegotiation(String negotiationId);

    boolean hasNegotiationForPlayer(Player player);

    Collection<Negotiation> getNegotiations();

    Negotiation getNegotiationForPlayer(Player player);
}
