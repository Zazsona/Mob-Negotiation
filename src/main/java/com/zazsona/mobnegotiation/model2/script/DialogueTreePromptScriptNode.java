package com.zazsona.mobnegotiation.model2.script;

import java.util.List;

public class DialogueTreePromptScriptNode extends PromptScriptNode {
    private boolean canBeInitialPrompt;

    public DialogueTreePromptScriptNode()
    {
        super();
    }

    public DialogueTreePromptScriptNode(ScriptLine scriptLine, List<PromptResponseScriptNode> responses, boolean canBeInitialPrompt)
    {
        super(scriptLine, responses);
        this.canBeInitialPrompt = canBeInitialPrompt;
    }

    public boolean canBeInitialPrompt() {
        return canBeInitialPrompt;
    }

    public void setCanBeInitialPrompt(boolean canBeInitialPrompt) {
        this.canBeInitialPrompt = canBeInitialPrompt;
    }
}
