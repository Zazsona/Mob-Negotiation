package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.model.Negotiation;

public interface NegotiationEventListener
{
    /**
     * An event fired whenever the state of a {@link Negotiation} is updated.
     * @param negotiation the updated negotiation
     */
    void onNegotiationStateUpdate(Negotiation negotiation);
}
