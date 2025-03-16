package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.IPersonable;
import com.zazsona.mobnegotiation.model2.Personable;
import com.zazsona.mobnegotiation.model2.ResponseType;

public class PersonableDialogueTreeResponseScriptNode extends PromptResponseScriptNode {
    private IPersonable<Float> successRate;

    public PersonableDialogueTreeResponseScriptNode()
    {
        this(null, ResponseType.PARLEY);
    }

    public PersonableDialogueTreeResponseScriptNode(ScriptLine scriptLine, ResponseType responseType)
    {
        this(scriptLine, responseType, new Personable<>(0.0f, 0.0f, 0.0f, 0.0f));
    }

    public PersonableDialogueTreeResponseScriptNode(ScriptLine scriptLine, ResponseType responseType, IPersonable<Float> successRate) {
        super(scriptLine, responseType);
        this.successRate = successRate;
    }

    public IPersonable<Float> getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(IPersonable<Float> successRate) {
        this.successRate = successRate;
    }
}
