package com.zazsona.mobnegotiation.repository.script;

public enum PromptTypeSchema {
    UNCLASSIFIED(null, null, null, null, null, null, null, null),
    IDLE_WARNING("IdleWarningPrompt", "IdleWarningPromptId", "IdleWarningPromptEntities", null, null, null, null, null),
    IDLE_TIMEOUT("IdleTimeoutPrompt", "IdleTimeoutPromptId", "IdleTimeoutPromptEntities", null, null, null, null, null),
    HOLD_UP("HoldUpPrompt", "HoldUpPromptId", "HoldUpPromptEntities", "HoldUpPromptResponses", "HoldUpPromptResponse", "HoldUpPromptResponseId",null, null),
    ITEM("ItemNegotiationPrompt", "ItemNegotiationPromptId", "ItemNegotiationPromptEntities", "ItemNegotiationPromptResponses", "ItemNegotiationPromptResponse", "ItemNegotiationPromptResponseId",null, null),
    MONEY("MoneyNegotiationPrompt", "MoneyNegotiationPromptId", "MoneyNegotiationPromptEntities", "MoneyNegotiationPromptResponses", "MoneyNegotiationPromptResponse", "MoneyNegotiationPromptResponseId", null, null),
    POWER("PowerNegotiationPrompt", "PowerNegotiationPromptId", "PowerNegotiationPromptEntities", "PowerNegotiationPromptResponses", "PowerNegotiationPromptResponse", "PowerNegotiationPromptResponseId", "PowerNegotiationResponseSuccessRate", "PowerNegotiationPromptLink");

    private final String promptTable;
    private final String promptTableId;
    private final String promptEntitiesTable;
    private final String promptResponsesTable;
    private final String promptResponseTable;
    private final String promptResponseTableId;
    private final String promptResponseSuccessRateTable;
    private final String promptLinkTable;

    PromptTypeSchema(String promptTable) {
        this(
                promptTable,
                String.format("%sId", promptTable),
                String.format("%sEntities", promptTable),
                String.format("%sResponses", promptTable),
                String.format("%sResponse", promptTable),
                String.format("%sResponseId", promptTable),
                String.format("%sResponseSuccessRate", promptTable),
                String.format("%sLink", promptTable)
        );
    }

    PromptTypeSchema(String promptTable, String promptTableId, String promptEntitiesTable, String promptResponsesTable, String promptResponseTable, String promptResponseTableId, String promptResponseSuccessRateTable, String promptLinkTable) {
        this.promptTable = promptTable;
        this.promptTableId = promptTableId;
        this.promptEntitiesTable = promptEntitiesTable;
        this.promptResponsesTable = promptResponsesTable;
        this.promptResponseTable = promptResponseTable;
        this.promptResponseTableId = promptResponseTableId;
        this.promptResponseSuccessRateTable = promptResponseSuccessRateTable;
        this.promptLinkTable = promptLinkTable;
    }

    public String getPromptTable() {
        return promptTable;
    }

    public String getPromptTableId() {
        return promptTableId;
    }

    public String getPromptEntitiesTable() {
        return promptEntitiesTable;
    }

    public String getPromptResponsesTable() {
        return promptResponsesTable;
    }

    public String getPromptResponseTable() {
        return promptResponseTable;
    }

    public String getPromptResponseTableId() {
        return promptResponseTableId;
    }

    public String getPromptResponseSuccessRateTable() {
        return promptResponseSuccessRateTable;
    }

    public String getPromptLinkTable() {
        return promptLinkTable;
    }
}
