package com.zazsona.mobnegotiation.model2.scriptarchive;

import com.zazsona.mobnegotiation.model2.script.ScriptLine;

public class NegotiationScriptPrompt
{
    private ScriptLine line;

    /**
     * Creates a new object with default text and no responses or children.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationScriptPrompt()
    {
        this(null);
    }

    /**
     * Creates a new object with the provided text and no children or responses
     * @param line the script line for this node
     */
    public NegotiationScriptPrompt(ScriptLine line)
    {
        this.line = line;
    }

    public ScriptLine getScriptLine() {
        return line;
    }

    public void setScriptLine(ScriptLine line) {
        this.line = line;
    }
}
