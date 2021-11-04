package com.zazsona.mobnegotiation.model;

public class NegotiationPrompt
{
    private String mobMessage;
    private Mood mobMood;

    public NegotiationPrompt(String mobMessage, Mood mood)
    {
        this.mobMessage = mobMessage;
        this.mobMood = mood;
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
}
