package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.model2.PersonalityType;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromptLinkQueryBuilder extends AbstractPromptQueryBuilder {
    private boolean filterOnPromptIds;
    private List<Integer> promptIds;
    private boolean filterOnPromptResponseIds;
    private List<Integer> promptResponseIds;
    private boolean includeSuccessOptions;
    private boolean includeFailureOptions;
    private boolean includeNeutralOptions;

    public PromptLinkQueryBuilder() {
        super();
        setFilterOnPromptResponseIds(false);
        setPromptResponseIds(null);
        setFilterOnPromptIds(false);
        setPromptIds(null);

        setIncludeSuccessOptions(true);
        setIncludeNeutralOptions(true);
        setIncludeFailureOptions(true);
    }

    @Override
    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        if (promptType != PromptTypeSchema.UNCLASSIFIED && promptType.getPromptLinkTable() == null)
            throw new NotImplementedException("The provided type does not have a Prompt Link table.");
        return super.setPromptType(promptType);
    }

    public boolean isFilterOnPromptResponseIds() {
        return filterOnPromptResponseIds;
    }

    public PromptLinkQueryBuilder setFilterOnPromptResponseIds(boolean filterOnPromptResponseIds) {
        this.filterOnPromptResponseIds = filterOnPromptResponseIds;
        return this;
    }

    public List<Integer> getPromptResponseIds() {
        return promptResponseIds;
    }

    public PromptLinkQueryBuilder setPromptResponseIds(List<Integer> promptResponseIds) {
        this.promptResponseIds = promptResponseIds;
        return this;
    }

    public boolean isIncludeNeutralOptions() {
        return includeNeutralOptions;
    }

    public PromptLinkQueryBuilder setIncludeNeutralOptions(boolean includeNeutralOptions) {
        this.includeNeutralOptions = includeNeutralOptions;
        return this;
    }

    public boolean isIncludeFailureOptions() {
        return includeFailureOptions;
    }

    public PromptLinkQueryBuilder setIncludeFailureOptions(boolean includeFailureOptions) {
        this.includeFailureOptions = includeFailureOptions;
        return this;
    }

    public boolean isIncludeSuccessOptions() {
        return includeSuccessOptions;
    }

    public PromptLinkQueryBuilder setIncludeSuccessOptions(boolean includeSuccessOptions) {
        this.includeSuccessOptions = includeSuccessOptions;
        return this;
    }

    public List<Integer> getPromptIds() {
        return promptIds;
    }

    public PromptLinkQueryBuilder setPromptIds(List<Integer> promptIds) {
        this.promptIds = promptIds;
        return this;
    }

    public boolean isFilterOnPromptIds() {
        return filterOnPromptIds;
    }

    public PromptLinkQueryBuilder setFilterOnPromptIds(boolean filterOnPromptIds) {
        this.filterOnPromptIds = filterOnPromptIds;
        return this;
    }

    @Override
    public String buildQuery() {
        if (isFilterOnPromptResponseIds() && getPromptResponseIds() == null)
            throw new NullPointerException("Prompt Response ID filter is enabled, but no IDs have been provided.");
        if (isFilterOnPromptIds() && getPromptIds() == null)
            throw new NullPointerException("Prompt ID filter is enabled, but no IDs have been provided.");

        String queryTemplate = getQueryTemplate();
        String promptIdsCSV = (isFilterOnPromptIds()) ? getPromptIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        String promptResponseIdsCSV = (isFilterOnPromptResponseIds()) ? getPromptResponseIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        getTokens().put("promptResponseIds", promptResponseIdsCSV);
        getTokens().put("promptIds", promptIdsCSV);

        return StringSubstitutor.replace(queryTemplate, getTokens(), "${", "}");
    }

    @Override
    protected String getQuerySourceTable() {
        return "${promptLinkTable} PL";
    }

    @Override
    protected ArrayList<String> getQueryColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("PL.\"${promptTableIdColumn}\" AS PromptId");
        columns.add("PL.\"${promptResponseTableIdColumn}\" AS PromptResponseId");
        columns.add("PL.\"Next${promptTableIdColumn}\" AS NextPromptId");
        columns.add("PL.FollowOnSuccess");
        columns.add("PL.FollowOnFailure");
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (isFilterOnPromptResponseIds())
            filters.add("AND PL.\"${promptResponseTableIdColumn}\" IN (${promptResponseIds})");
        if (isFilterOnPromptIds())
            filters.add("AND PL.\"${promptTableIdColumn}\" IN (${promptIds})");
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        return new ArrayList<>();
    }
}
