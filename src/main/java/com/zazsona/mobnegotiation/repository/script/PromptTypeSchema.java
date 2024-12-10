package com.zazsona.mobnegotiation.repository.script;

public enum PromptTypeSchema {
    UNCLASSIFIED(null, null, null, null, null, null),
    IDLE_WARNING("IdleWarningPrompt", "IdleWarningPromptId", "IdleWarningPromptEntities", null, null, null),
    IDLE_TIMEOUT("IdleTimeoutPrompt", "IdleTimeoutPromptId", "IdleTimeoutPromptEntities", null, null, null),
    HOLD_UP("HoldUpPrompt", "HoldUpPromptId", "HoldUpPromptEntities", "HoldUpPromptResponses", null, null),
    ITEM("ItemNegotiationPrompt", "ItemNegotiationPromptId", "ItemNegotiationPromptEntities", "ItemNegotiationPromptResponses", null, null),
    MONEY("MoneyNegotiationPrompt", "MoneyNegotiationPromptId", "MoneyNegotiationPromptEntities", "MoneyNegotiationPromptResponses", null, null),
    POWER("PowerNegotiationPrompt", "PowerNegotiationPromptId", "PowerNegotiationPromptEntities", "PowerNegotiationPromptResponses", "PowerNegotiationResponseSuccessRate", "PowerNegotiationPromptLink");

    private final String promptTable;
    private final String promptTableId;
    private final String promptEntitiesTable;
    private final String promptResponsesTable;
    private final String promptResponseSuccessRateTable;
    private final String promptLinkTable;

    PromptTypeSchema(String promptTable) {
        this(
                promptTable,
                String.format("%sId", promptTable),
                String.format("%sEntities", promptTable),
                String.format("%sResponses", promptTable),
                String.format("%sResponseSuccessRate", promptTable),
                String.format("%sLink", promptTable)
        );
    }

    PromptTypeSchema(String promptTable, String promptTableId, String promptEntitiesTable, String promptResponsesTable, String promptResponseSuccessRateTable, String promptLinkTable) {
        this.promptTable = promptTable;
        this.promptTableId = promptTableId;
        this.promptEntitiesTable = promptEntitiesTable;
        this.promptResponsesTable = promptResponsesTable;
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

    public String getPromptResponseSuccessRateTable() {
        return promptResponseSuccessRateTable;
    }

    public String getPromptLinkTable() {
        return promptLinkTable;
    }
}
