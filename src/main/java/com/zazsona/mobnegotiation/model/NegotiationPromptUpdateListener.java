package com.zazsona.mobnegotiation.model;

public interface NegotiationPromptUpdateListener
{
    /**
     * An event fired whenever the {@link NegotiationPrompt} of a {@link Negotiation} is updated.
     * @param negotiation the updated negotiation
     * @param prompt the updated prompt, which can be null
     */
    void onNegotiationPromptChanged(Negotiation negotiation, NegotiationPrompt prompt);
}
