package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.model2.PersonalityType;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromptResponseSuccessRateQueryBuilder extends AbstractPromptQueryBuilder {
    private boolean filterOnPromptResponseIds;
    private List<Integer> promptResponseIds;
    private boolean filterOnPersonalityId;
    private List<PersonalityType> personalityTypes;
    private int wildcardPersonalityId;

    public PromptResponseSuccessRateQueryBuilder() {
        super();
        setFilterOnPromptResponseIds(false);
        setPromptResponseIds(null);
        setFilterOnPersonalityId(false);
        setPersonalityTypes(null);
        setWildcardPersonalityId(PersonalityType.WILDCARD.getId());
    }

    @Override
    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        if (promptType != PromptTypeSchema.UNCLASSIFIED && promptType.getPromptResponseSuccessRateTable() == null)
            throw new NotImplementedException("The provided type does not have a Response Success Rate table.");
        return super.setPromptType(promptType);
    }

    public boolean isFilterOnPromptResponseIds() {
        return filterOnPromptResponseIds;
    }

    public PromptResponseSuccessRateQueryBuilder setFilterOnPromptResponseIds(boolean filterOnPromptResponseIds) {
        this.filterOnPromptResponseIds = filterOnPromptResponseIds;
        return this;
    }

    public List<Integer> getPromptResponseIds() {
        return promptResponseIds;
    }

    public PromptResponseSuccessRateQueryBuilder setPromptResponseIds(List<Integer> promptResponseIds) {
        this.promptResponseIds = promptResponseIds;
        return this;
    }

    public boolean isFilterOnPersonalityId() {
        return filterOnPersonalityId;
    }

    public PromptResponseSuccessRateQueryBuilder setFilterOnPersonalityId(boolean filterOnPersonalityId) {
        this.filterOnPersonalityId = filterOnPersonalityId;
        return this;
    }

    public List<PersonalityType> getPersonalityTypes() {
        return personalityTypes;
    }

    public PromptResponseSuccessRateQueryBuilder setPersonalityTypes(List<PersonalityType> personalityTypes) {
        this.personalityTypes = personalityTypes;
        return this;
    }

    public int getWildcardPersonalityId() {
        return wildcardPersonalityId;
    }

    public PromptResponseSuccessRateQueryBuilder setWildcardPersonalityId(int wildcardPersonalityId) {
        this.wildcardPersonalityId = wildcardPersonalityId;
        this.getTokens().put("personalityWildcardId", wildcardPersonalityId);
        return this;
    }

    @Override
    public String buildQuery() {
        if (isFilterOnPromptResponseIds() && getPromptResponseIds() == null)
            throw new NullPointerException("Prompt Response ID filter is enabled, but no IDs have been provided.");
        if (isFilterOnPersonalityId() && getPersonalityTypes() == null)
            throw new NullPointerException("Personality ID filter is enabled, but no IDs have been provided.");

        String queryTemplate = getQueryTemplate();
        String promptResponseIdsCSV = (isFilterOnPromptResponseIds()) ? getPromptResponseIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        String personalityIdsCSV = (isFilterOnPersonalityId()) ? getPersonalityTypes().stream().map(pt -> Integer.toString(pt.getId())).collect(Collectors.joining(", ")) : null;
        getTokens().put("promptResponseIds", promptResponseIdsCSV);
        getTokens().put("personalityIds", personalityIdsCSV);

        return StringSubstitutor.replace(queryTemplate, getTokens(), "${", "}");
    }

    @Override
    protected String getQuerySourceTable() {
        return "${promptResponseSuccessRateTable} RSR";
    }

    @Override
    protected ArrayList<String> getQueryColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("RSR.\"${promptResponseTableIdColumn}\" AS PromptResponseId");
        columns.add("RSR.PersonalityId");
        columns.add("RSR.SuccessRate");
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (isFilterOnPromptResponseIds())
            filters.add("AND RSR.\"${promptResponseTableIdColumn}\" IN (${promptResponseIds})");
        if (isFilterOnPersonalityId())
            filters.add("AND RSR.PersonalityId IN (${personalityIds}) OR PT.PersonalityId = ${personalityWildcardId}");
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        return new ArrayList<>();
    }
}
