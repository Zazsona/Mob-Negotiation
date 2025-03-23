package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.model.PersonalityType;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.repository.script2.PromptTypeSchema;

import java.util.List;

public class PromptIdQueryCommandBuilder {
    private PromptTypeSchema promptType;
    private List<NegotiationEntityType> entityTypeFilter;
    private List<PersonalityType> personalityTypeFilter;

    public PromptIdQueryCommandBuilder(PromptTypeSchema promptType) {
        this.promptType = promptType;
    }

    public void setEntityTypeFilter(List<NegotiationEntityType> entityTypeFilter) {
        if (!promptType.isEntityRestrictedPrompt())
            throw new UnsupportedOperationException(promptType.getPromptTable() +" does not support filtering on Entity Type.");
        this.entityTypeFilter = entityTypeFilter;
    }

    public void setPersonalityTypeFilter(List<PersonalityType> personalityTypeFilter) {
        if (!promptType.isPersonalityRestrictedPrompt())
            throw new UnsupportedOperationException(promptType.getPromptTable() +" does not support filtering on Personality Type.");
        this.personalityTypeFilter = personalityTypeFilter;
    }
}
