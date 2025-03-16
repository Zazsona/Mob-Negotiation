package com.zazsona.mobnegotiation.model2.script;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PromptScriptNode extends ScriptNode {
    private List<PromptResponseScriptNode> responses;

    /**
     * Creates a new object with default text and no responses.
     * This constructor is mostly available for Json Deserialisation
     */
    public PromptScriptNode()
    {
        this(null, new ArrayList<>());
    }

    /**
     * Creates a new object with the provided text and no responses
     * @param line the script line for this node
     */
    public PromptScriptNode(ScriptLine line)
    {
        this(line, new ArrayList<>());
    }

    /**
     * Creates a new object with the provided text and responses
     * @param line the script line for this node
     * @param responses child nodes
     */
    public PromptScriptNode(ScriptLine line, @NonNull List<PromptResponseScriptNode> responses)
    {
        super(line);
        this.responses = responses;
    }

    public List<PromptResponseScriptNode> getResponses() {
        return responses;
    }

    public void setResponses(List<PromptResponseScriptNode> responses) {
        this.responses = responses;
    }
}
