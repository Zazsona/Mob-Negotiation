package com.zazsona.mobnegotiation.model2.scriptarchive;

import com.zazsona.mobnegotiation.model.Mood;
import com.zazsona.mobnegotiation.model.TextType;

public class NegotiationStepPrompt {

    private String mobMessage = "...";
    private Mood mobMood = Mood.NEUTRAL;
    private TextType textType = TextType.SPEECH;

    public NegotiationStepPrompt(String mobMessage)
    {
        this.mobMessage = mobMessage;
    }

    public NegotiationStepPrompt(String mobMessage, TextType textType)
    {
        this.mobMessage = mobMessage;
        this.textType = textType;
    }

    public NegotiationStepPrompt(String mobMessage, Mood mood)
    {
        this.mobMessage = mobMessage;
        this.mobMood = mood;
    }

    public NegotiationStepPrompt(String mobMessage, Mood mood, TextType textType)
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
