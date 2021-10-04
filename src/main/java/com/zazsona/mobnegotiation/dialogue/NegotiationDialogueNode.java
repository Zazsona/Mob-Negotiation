package com.zazsona.mobnegotiation.dialogue;

import java.util.ArrayList;
import java.util.List;

public class NegotiationDialogueNode
{
    private String text = "No text.";
    private List<NegotiationDialogueOption> responses = new ArrayList<>();
    private List<NegotiationDialogueNode> children = new ArrayList<>();

    /**
     * Creates a new object with default text and no responses or children.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationDialogueNode()
    {

    }

    /**
     * Creates a new object with the provided text and no children or responses
     * @param text the text for the mob to say
     */
    public NegotiationDialogueNode(String text)
    {
        this.text = text;
    }

    /**
     * Creates a new object with the provided text and responses and no children
     * @param text the text for the mob to say
     * @param responses the responses for the player
     */
    public NegotiationDialogueNode(String text, List<NegotiationDialogueOption> responses)
    {
        this.text = text;
        this.responses = responses;
    }

    /**
     * Creates a new object with the provided text, responses, and children
     * @param text the text for the mob to say
     * @param responses the responses for the player
     * @param children the child nodes
     */
    public NegotiationDialogueNode(String text, List<NegotiationDialogueOption> responses, List<NegotiationDialogueNode> children)
    {
        this.text = text;
        this.responses = responses;
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
     * Gets responses
     * @return responses
     */
    public List<NegotiationDialogueOption> getResponses()
    {
        return responses;
    }

    /**
     * Gets children
     * @return children
     */
    public List<NegotiationDialogueNode> getChildren()
    {
        return children;
    }
}
