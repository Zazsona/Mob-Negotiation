package com.zazsona.mobnegotiation.model.script;

import com.zazsona.mobnegotiation.model.Mood;
import com.zazsona.mobnegotiation.model.TextType;

import java.util.ArrayList;
import java.util.List;

public class NegotiationScriptNode
{
    private static final String UNDEFINED_MSG = "No text.";

    private String text = UNDEFINED_MSG;
    private Mood mood = Mood.NEUTRAL;
    private TextType textType = TextType.SPEECH;
    private List<NegotiationScriptResponseNode> responses = new ArrayList<>();
    private List<NegotiationScriptNode> children = new ArrayList<>();

    /**
     * Creates a new object with default text and no responses or children.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationScriptNode()
    {

    }

    /**
     * Creates a new object with the provided text and no children or responses
     * @param text the text for the mob to say
     * @param mood the mood of the text
     * @param textType the format of the text
     */
    public NegotiationScriptNode(String text, Mood mood, TextType textType)
    {
        this.text = text;
        this.mood = mood;
        this.textType = textType;
    }

    /**
     * Creates a new object with the provided text and responses and no children
     * @param text the text for the mob to say
     * @param responses the responses for the player
     */
    public NegotiationScriptNode(String text, List<NegotiationScriptResponseNode> responses)
    {
        this.text = text;
        this.responses = responses;
    }

    /**
     * Creates a new object with the provided text and responses and no children
     * @param text the text for the mob to say
     * @param mood the mood of the text
     * @param textType the format of the text
     * @param responses the responses for the player
     */
    public NegotiationScriptNode(String text, Mood mood, TextType textType, List<NegotiationScriptResponseNode> responses)
    {
        this.text = text;
        this.responses = responses;
        this.mood = mood;
    }

    /**
     * Creates a new object with the provided text, responses, and children
     * @param text the text for the mob to say
     * @param mood the mood of the text
     * @param textType the format of the text
     * @param responses the responses for the player
     * @param children the child nodes
     */
    public NegotiationScriptNode(String text, Mood mood, TextType textType, List<NegotiationScriptResponseNode> responses, List<NegotiationScriptNode> children)
    {
        this.text = text;
        this.responses = responses;
        this.mood = mood;
        this.textType = textType;
        this.children = children;
    }

    /**
     * Gets text
     * @return text
     */
    public String getText()
    {
        return text;
    }

    /**
     * Gets mood
     * @return mood
     */
    public Mood getMood()
    {
        return mood;
    }

    /**
     * Gets textType
     * @return textType
     */
    public TextType getTextType()
    {
        return textType;
    }

    /**
     * Gets responses
     * @return responses
     */
    public List<NegotiationScriptResponseNode> getResponses()
    {
        return responses;
    }

    /**
     * Gets children
     * @return children
     */
    public List<NegotiationScriptNode> getChildren()
    {
        return children;
    }
}
