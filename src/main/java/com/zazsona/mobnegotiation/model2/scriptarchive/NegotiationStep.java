package com.zazsona.mobnegotiation.model2.scriptarchive;

import java.util.ArrayList;

public class NegotiationStep {
    private NegotiationStepPrompt prompt;
    private ArrayList<NegotiationStepResponseOption> responses;

    public NegotiationStep()
    {
        this(null, new ArrayList<>());
    }

    public NegotiationStep(NegotiationStepPrompt prompt)
    {
        this(prompt, new ArrayList<>());
    }

    public NegotiationStep(NegotiationStepPrompt prompt, ArrayList<NegotiationStepResponseOption> responses)
    {
        this.prompt = prompt;
        this.responses = responses;
    }

    public NegotiationStepPrompt getPrompt() {
        return prompt;
    }

    public void setPrompt(NegotiationStepPrompt prompt) {
        this.prompt = prompt;
    }

    public ArrayList<NegotiationStepResponseOption> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<NegotiationStepResponseOption> responses) {
        this.responses = responses;
    }
}
