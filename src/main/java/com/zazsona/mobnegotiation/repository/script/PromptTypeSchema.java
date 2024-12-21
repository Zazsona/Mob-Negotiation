package com.zazsona.mobnegotiation.repository.script;

public enum PromptTypeSchema {
    UNCLASSIFIED(null, null, null, null, null, null, null, null, false, false),
    IDLE_WARNING("IdleWarningPrompt", "IdleWarningPromptId", "IdleWarningPromptEntities", null, null, null, null, null, false, true),
    IDLE_TIMEOUT("IdleTimeoutPrompt", "IdleTimeoutPromptId", "IdleTimeoutPromptEntities", null, null, null, null, null, false, true),
    HOLD_UP("HoldUpPrompt", "HoldUpPromptId", "HoldUpPromptEntities", "HoldUpPromptResponses", "HoldUpPromptResponse", "HoldUpPromptResponseId",null, null, false, true),
    ITEM("ItemNegotiationPrompt", "ItemNegotiationPromptId", "ItemNegotiationPromptEntities", "ItemNegotiationPromptResponses", "ItemNegotiationPromptResponse", "ItemNegotiationPromptResponseId",null, null, true, true),
    MONEY("MoneyNegotiationPrompt", "MoneyNegotiationPromptId", "MoneyNegotiationPromptEntities", "MoneyNegotiationPromptResponses", "MoneyNegotiationPromptResponse", "MoneyNegotiationPromptResponseId", null, null, true, true),
    POWER("PowerNegotiationPrompt", "PowerNegotiationPromptId", "PowerNegotiationPromptEntities", "PowerNegotiationPromptResponses", "PowerNegotiationPromptResponse", "PowerNegotiationPromptResponseId", "PowerNegotiationResponseSuccessRate", "PowerNegotiationPromptLink", false, false);

    private final String promptTable;
    private final String promptTableId;
    private final String promptEntitiesTable;
    private final String promptResponsesTable;
    private final String promptResponseTable;
    private final String promptResponseTableId;
    private final String promptResponseSuccessRateTable;
    private final String promptLinkTable;

    private final boolean isCyclicPrompt;
    private final boolean isPersonablePrompt;

    PromptTypeSchema(String promptTable, String promptTableId, String promptEntitiesTable, String promptResponsesTable, String promptResponseTable, String promptResponseTableId, String promptResponseSuccessRateTable, String promptLinkTable, boolean isCyclicPrompt, boolean isPersonablePrompt) {
        this.promptTable = promptTable;
        this.promptTableId = promptTableId;
        this.promptEntitiesTable = promptEntitiesTable;
        this.promptResponsesTable = promptResponsesTable;
        this.promptResponseTable = promptResponseTable;
        this.promptResponseTableId = promptResponseTableId;
        this.promptResponseSuccessRateTable = promptResponseSuccessRateTable;
        this.promptLinkTable = promptLinkTable;
        this.isCyclicPrompt = isCyclicPrompt;
        this.isPersonablePrompt = isPersonablePrompt;
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

    public boolean isCyclicPrompt() {
        return isCyclicPrompt;
    }

    public boolean isPersonablePrompt() {
        return isPersonablePrompt;
    }
}
