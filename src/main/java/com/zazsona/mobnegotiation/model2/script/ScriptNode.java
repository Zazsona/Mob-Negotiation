package com.zazsona.mobnegotiation.model2.script;

import java.util.List;

public class ScriptNode {
    private ScriptLine line;

    /**
     * Creates a new object with the provided text
     * @param line the script line for this node
     */
    public ScriptNode(ScriptLine line)
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
