package com.zazsona.mobnegotiation.repository.script;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPromptQueryBuilder {

    private PromptTypeSchema promptType;
    private Map<String, Object> tokens;

    public AbstractPromptQueryBuilder() {
        this.tokens = new HashMap<>();
        setPromptType(PromptTypeSchema.UNCLASSIFIED);
    }

    public PromptTypeSchema getPromptType() {
        return promptType;
    }

    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        this.promptType = promptType;
        addSchemaTokensToMap(promptType, this.getTokens());
        return this;
    }

    public abstract String buildQuery();

    protected Map<String, Object> getTokens()
    {
        return tokens;
    }

    /**
     * Adds values to the provided tokens map for the various schema properties of the given PromptType.
     * @param promptTypeSchema the prompt type schema details
     * @param tokens the map to add to
     * @return reference to the same map that was passed in for easy chaining
     */
    private Map<String, Object> addSchemaTokensToMap(PromptTypeSchema promptTypeSchema, Map<String, Object> tokens)
    {
        if (promptTypeSchema.getPromptTable() != null && !promptTypeSchema.getPromptTable().isBlank())
            tokens.put("promptTable", promptTypeSchema.getPromptTable());
        else
            tokens.remove("promptTable");

        if (promptTypeSchema.getPromptTableId() != null && !promptTypeSchema.getPromptTableId().isBlank())
            tokens.put("promptTableId", promptTypeSchema.getPromptTableId());
        else
            tokens.remove("promptTableId");

        if (promptTypeSchema.getPromptEntitiesTable() != null && !promptTypeSchema.getPromptEntitiesTable().isBlank())
            tokens.put("promptEntitiesTable", promptTypeSchema.getPromptEntitiesTable());
        else
            tokens.remove("promptEntitiesTable");

        if (promptTypeSchema.getPromptResponsesTable() != null && !promptTypeSchema.getPromptResponsesTable().isBlank())
            tokens.put("promptResponsesTable", promptTypeSchema.getPromptResponsesTable());
        else
            tokens.remove("promptResponsesTable");

        if (promptTypeSchema.getPromptResponseTable() != null && !promptTypeSchema.getPromptResponseTable().isBlank())
            tokens.put("promptResponseTable", promptTypeSchema.getPromptResponseTable());
        else
            tokens.remove("promptResponseTable");

        if (promptTypeSchema.getPromptResponseTableId() != null && !promptTypeSchema.getPromptResponseTableId().isBlank())
            tokens.put("promptResponseTableId", promptTypeSchema.getPromptResponseTableId());
        else
            tokens.remove("promptResponseTable");

        if (promptTypeSchema.getPromptResponseSuccessRateTable() != null && !promptTypeSchema.getPromptResponseSuccessRateTable().isBlank())
            tokens.put("promptResponseSuccessRateTable", promptTypeSchema.getPromptResponseSuccessRateTable());
        else
            tokens.remove("promptResponseSuccessRateTable");

        if (promptTypeSchema.getPromptLinkTable() != null && !promptTypeSchema.getPromptLinkTable().isBlank())
            tokens.put("promptLinkTable", promptTypeSchema.getPromptLinkTable());
        else
            tokens.remove("promptLinkTable");

        return tokens;
    }
}
