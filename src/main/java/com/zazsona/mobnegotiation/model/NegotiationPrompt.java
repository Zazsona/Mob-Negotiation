package com.zazsona.mobnegotiation.model;

public class NegotiationPrompt
{
    private String mobMessage = "...";
    private Mood mobMood = Mood.NEUTRAL;
    private TextType textType = TextType.SPEECH;

    public NegotiationPrompt(String mobMessage)
    {
        this.mobMessage = mobMessage;
    }

    public NegotiationPrompt(String mobMessage, TextType textType)
    {
        this.mobMessage = mobMessage;
        this.textType = textType;
    }

    public NegotiationPrompt(String mobMessage, Mood mood)
    {
        this.mobMessage = mobMessage;
        this.mobMood = mood;
    }

    public NegotiationPrompt(String mobMessage, Mood mood, TextType textType)
    {
        this.mobMessage = mobMessage;
        this.mobMood = mood;
        this.textType = textType;
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
     * Gets the {@link TextType} of the Mob Message
     * @return textType
     */
    public TextType getMobMessageTextType()
    {
        return textType;
    }
}
