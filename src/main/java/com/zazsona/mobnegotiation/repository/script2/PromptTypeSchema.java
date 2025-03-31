package com.zazsona.mobnegotiation.repository.script2;

public enum PromptTypeSchema {
    UNCLASSIFIED(null, false, false, false, false),
    IDLE_WARNING("IdleWarningPrompt", false, false, true, true),
    IDLE_TIMEOUT("IdleTimeoutPrompt", false, false, true, true),
    HOLD_UP("HoldUpPrompt", false, false, true, true),
    ITEM("ItemNegotiationPrompt", false, true, true, true),
    MONEY("MoneyNegotiationPrompt", false, true, true, true),
    POWER("PowerNegotiationPrompt", true, false, false, true);

    private final String promptTable;
    private final boolean isDynamicOfferPrompt;
    private final boolean isDialogueTreePrompt;
    private final boolean isPersonalityRestrictedPrompt;
    private final boolean isEntityRestrictedPrompt;

    PromptTypeSchema(String promptTable, boolean isDialogueTreePrompt, boolean isDynamicOfferPrompt, boolean isPersonalityRestrictedPrompt, boolean isEntityRestrictedPrompt) {
        this.promptTable = promptTable;
        this.isDialogueTreePrompt = isDialogueTreePrompt;
        this.isDynamicOfferPrompt = isDynamicOfferPrompt;
        this.isPersonalityRestrictedPrompt = isPersonalityRestrictedPrompt;
        this.isEntityRestrictedPrompt = isEntityRestrictedPrompt;
    }

    public String getPromptTable() {
        return promptTable;
    }

    public String getPromptTableId() {
        return promptTable + "Id";
    }

    public String getPromptEntitiesTable() {
        return promptTable + "Entities";
    }

    public String getPromptResponsesTable() {
        return promptTable + "Responses";
    }

    public String getPromptResponseTable() {
        return promptTable + "Response";
    }

    public String getPromptResponseTableId() {
        return promptTable + "ResponseId";
    }

    public String getPromptResponseSuccessRateTable() {
        return promptTable + "ResponseSuccessRate";
    }

    public String getPromptLinkTable() {
        return promptTable + "Link";
    }

    public boolean isDynamicOfferPrompt() {
        return isDynamicOfferPrompt;
    }

    public boolean isDialogueTreePrompt() {
        return isDialogueTreePrompt;
    }

    public boolean isPersonalityRestrictedPrompt() {
        return isPersonalityRestrictedPrompt;
    }

    public boolean isEntityRestrictedPrompt() {
        return isEntityRestrictedPrompt;
    }
}
