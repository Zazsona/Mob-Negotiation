package com.zazsona.mobnegotiation.repository.script2;

import java.util.Objects;

public class PromptResponseReference {
    private int promptId;
    private int responseId;

    public PromptResponseReference(int promptId, int responseId) {
        this.promptId = promptId;
        this.responseId = responseId;
    }

    public int getPromptId() {
        return promptId;
    }

    public int getResponseId() {
        return responseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromptResponseReference that = (PromptResponseReference) o;
        return getPromptId() == that.getPromptId() && getResponseId() == that.getResponseId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPromptId(), getResponseId());
    }
}
