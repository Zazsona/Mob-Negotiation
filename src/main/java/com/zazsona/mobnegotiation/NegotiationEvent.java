package com.zazsona.mobnegotiation;

public interface NegotiationEvent
{
    /**
     * An event fired whenever the state of a {@link NegotiationProcess} is updated.
     * @param state the state of negotiations
     */
    void onNegotiationStateUpdate(NegotiationState state);
}
