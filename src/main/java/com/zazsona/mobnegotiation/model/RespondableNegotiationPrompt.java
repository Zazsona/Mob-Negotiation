package com.zazsona.mobnegotiation.model;

import java.util.ArrayList;

public class RespondableNegotiationPrompt extends NegotiationPrompt
{
    private ArrayList<NegotiationResponse> responses;

    public RespondableNegotiationPrompt(String mobMessage, Mood mood, TextType textType, ArrayList<NegotiationResponse> responses)
    {
        super(mobMessage, mood, textType);
        this.responses = responses;
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
