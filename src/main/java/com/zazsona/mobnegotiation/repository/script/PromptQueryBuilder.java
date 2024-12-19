package com.zazsona.mobnegotiation.repository.script;

import org.apache.commons.text.StringSubstitutor;

import java.util.*;
import java.util.stream.Collectors;

public class PromptQueryBuilder extends AbstractPromptQueryBuilder {
    private boolean filterOnPromptIds;
    private List<Integer> promptIds;
    private String languageCode;

    public PromptQueryBuilder() {
        super();
        setFilterOnPromptIds(false);
        setPromptIds(null);
        setLanguageCode("en");
    }

    public List<Integer> getPromptIds() {
        return promptIds;
    }

    public boolean isFilterOnPromptIds() {
        return filterOnPromptIds;
    }

    public void setFilterOnPromptIds(boolean filterOnPromptIds) {
        this.filterOnPromptIds = filterOnPromptIds;
    }

    public PromptQueryBuilder setPromptIds(List<Integer> promptIds) {
        this.promptIds = promptIds;
        return this;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public PromptQueryBuilder setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        getTokens().put("languageCode", languageCode);
        return this;
    }

    @Override
    public String buildQuery() {
        if (filterOnPromptIds && getPromptIds() == null)
            throw new NullPointerException("Prompt ID filter is enabled, but no IDs have been provided.");

        String queryFormat = getQueryTemplate();
        String promptIdsCSV = (isFilterOnPromptIds()) ? getPromptIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        getTokens().put("promptIds", promptIdsCSV);

        return StringSubstitutor.replace(queryFormat, getTokens(), "${", "}");
    }

    @Override
    protected String getQuerySourceTable() {
        return "\"${promptTable}\" PT";
    }

    @Override
    protected ArrayList<String> getQueryColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("PT.\"${promptTableIdColumn}\" AS PromptId");
        columns.add("COALESCE(TCT.Text, TC.Text) AS Text");
        columns.add("SL.ScriptLineTypeId");
        columns.add("SL.ScriptLineToneId");
        if (getPromptType() == PromptTypeSchema.IDLE_WARNING || getPromptType() == PromptTypeSchema.IDLE_TIMEOUT || getPromptType() == PromptTypeSchema.HOLD_UP)
            columns.add("PT.PersonalityId");
        else if (getPromptType() == PromptTypeSchema.MONEY || getPromptType() == PromptTypeSchema.ITEM) {
            columns.add("PT.CanBeInitialOffer");
            columns.add("PT.CanBeRevisedOffer");
            columns.add("PT.CanBeRepeatOffer");
            columns.add("PT.CanBeRejection");
            columns.add("PT.CanBeAcceptance");
        }
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (isFilterOnPromptIds())
            filters.add("AND PT.\"${promptTableIdColumn}\" IN (${promptIds})");
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        ArrayList<String> joins = new ArrayList<>();
        joins.add("INNER JOIN ScriptLine SL ON SL.ScriptLineId = PT.ScriptLineId");
        joins.add("INNER JOIN TextContent TC ON TC.TextContentId = SL.TextContentId");
        joins.add("LEFT JOIN TextContentTranslation TCT ON TCT.TextContentId = TCT.TextContentId AND TCT.LanguageCode = '${languageCode}'");
        return joins;
    }
}
