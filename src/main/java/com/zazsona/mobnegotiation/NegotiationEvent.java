package com.zazsona.mobnegotiation;

public interface NegotiationEvent
{
    /**
     * An event fired whenever the state of a {@link NegotiationProcess} is updated.
     * @param negotiation the updated negotiation
     */
    void onNegotiationStateUpdate(NegotiationProcess negotiation);
}
