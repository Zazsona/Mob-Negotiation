package com.zazsona.mobnegotiation.model;

public interface NegotiationPromptUpdateListener
{
    /**
     * An event fired whenever the {@link NegotiationPrompt} of a {@link Negotiation} is updated.
     * @param negotiation the updated negotiation
     * @param prompt the updated prompt, which can be null
     * @param passive a boolean on if this is a passive message, such as idle chat, and does not indicate an advancement of negotiations
     */
    void onNegotiationPromptChanged(Negotiation negotiation, NegotiationPrompt prompt, boolean passive);
}
