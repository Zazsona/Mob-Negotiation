package com.zazsona.mobnegotiation.model2.scriptarchive;

import com.zazsona.mobnegotiation.model2.script.ScriptLine;

import java.util.List;

public class NegotiationScriptRespondablePrompt extends NegotiationScriptPrompt
{
    private List<NegotiationScriptResponse> responses;

    /**
     * Creates a new object with default text and no responses or children.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationScriptRespondablePrompt()
    {
        this(null, null);
    }

    /**
     * Creates a new object with the provided text and no children or responses
     * @param line the script line for this node
     */
    public NegotiationScriptRespondablePrompt(ScriptLine line)
    {
        this(line, null);
    }

    /**
     * Creates a new object with the provided text and responses.
     * @param line the script line for this node
     * @param responses the responses for the player
     */
    public NegotiationScriptRespondablePrompt(ScriptLine line, List<NegotiationScriptResponse> responses)
    {
        super(line);
        this.responses = responses;
    }

    /**
     * Gets responses
     * @return responses
     */
    public List<NegotiationScriptResponse> getResponses()
    {
        return responses;
    }

    public void setResponses(List<NegotiationScriptResponse> responses) {
        this.responses = responses;
    }
}
