package com.zazsona.mobnegotiation.model;

import java.util.ArrayList;

public class NegotiationPrompt
{
    private String mobMessage;
    private ArrayList<NegotiationResponse> responses;

    public NegotiationPrompt(String mobMessage, ArrayList<NegotiationResponse> responses)
    {
        this.mobMessage = mobMessage;
        this.responses = responses;
    }

    /**
     * Gets mobMessage
     * @return mobMessage
     */
    public String getMobMessage()
    {
        return mobMessage;
    }

    /**
     * Gets responses
     * @return responses
     */
    public ArrayList<NegotiationResponse> getResponses()
    {
        return responses;
    }
}
