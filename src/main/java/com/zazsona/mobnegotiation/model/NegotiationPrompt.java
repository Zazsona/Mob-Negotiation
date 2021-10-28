package com.zazsona.mobnegotiation.model;

import java.util.ArrayList;

public class NegotiationPrompt
{
    private String mobMessage;
    private Mood mobMood;
    private ArrayList<NegotiationResponse> responses;

    public NegotiationPrompt(String mobMessage, Mood mood, ArrayList<NegotiationResponse> responses)
    {
        this.mobMessage = mobMessage;
        this.mobMood = mood;
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
     * Gets mobMood
     * @return mobMood
     */
    public Mood getMobMood()
    {
        return mobMood;
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
