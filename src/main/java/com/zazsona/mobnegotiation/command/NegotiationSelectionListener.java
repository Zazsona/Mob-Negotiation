package com.zazsona.mobnegotiation.command;

import com.zazsona.mobnegotiation.NegotiationProcess;
import com.zazsona.mobnegotiation.NegotiationPrompt;
import com.zazsona.mobnegotiation.NegotiationPromptItem;

public interface NegotiationSelectionListener
{
    /**
     * Fired when a {@link NegotiationPromptItem} is selected by a valid player.
     * @param negotiation the negotiation context
     * @param prompt the prompt holding the item
     * @param item the selected item
     */
    void onNegotiationItemSelected(NegotiationProcess negotiation, NegotiationPrompt prompt, NegotiationPromptItem item);
}
