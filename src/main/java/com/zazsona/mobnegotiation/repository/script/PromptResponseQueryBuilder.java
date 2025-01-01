package com.zazsona.mobnegotiation.repository.script;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromptResponseQueryBuilder extends AbstractPromptQueryBuilder {
    private boolean filterOnPromptResponseIds;
    private List<Integer> promptResponseIds;
    private boolean includePermissionDependencies;
    private boolean includePluginDependencies;
    private String languageCode;

    public PromptResponseQueryBuilder() {
        super();
        setFilterOnPromptResponseIds(false);
        setPromptResponseIds(null);
        setIncludePermissionDependencies(true);
        setIncludePluginDependencies(true);
        setLanguageCode("en");
    }

    @Override
    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        if (promptType != PromptTypeSchema.UNCLASSIFIED && promptType.getPromptResponseTable() == null)
            throw new NotImplementedException("The provided type does not have a Response table.");
        return super.setPromptType(promptType);
    }

    public boolean isFilterOnPromptResponseIds() {
        return filterOnPromptResponseIds;
    }

    public PromptResponseQueryBuilder setFilterOnPromptResponseIds(boolean filterOnPromptResponseIds) {
        this.filterOnPromptResponseIds = filterOnPromptResponseIds;
        return this;
    }

    public List<Integer> getPromptResponseIds() {
        return promptResponseIds;
    }

    public PromptResponseQueryBuilder setPromptResponseIds(List<Integer> promptResponseIds) {
        this.promptResponseIds = promptResponseIds;
        return this;
    }

    public boolean isIncludePermissionDependencies() {
        return includePermissionDependencies;
    }

    public PromptResponseQueryBuilder setIncludePermissionDependencies(boolean includePermissionDependencies) {
        this.includePermissionDependencies = includePermissionDependencies;
        return this;
    }

    public boolean isIncludePluginDependencies() {
        return includePluginDependencies;
    }

    public PromptResponseQueryBuilder setIncludePluginDependencies(boolean includePluginDependencies) {
        this.includePluginDependencies = includePluginDependencies;
        return this;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public PromptResponseQueryBuilder setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        getTokens().put("languageCode", languageCode);
        return this;
    }

    @Override
    public String buildQuery() {
        if (isFilterOnPromptResponseIds() && getPromptResponseIds() == null)
            throw new NullPointerException("Prompt Response ID filter is enabled, but no IDs have been provided.");

        String queryTemplate = getQueryTemplate();
        String promptResponseIdsCSV = (isFilterOnPromptResponseIds()) ? getPromptResponseIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        getTokens().put("promptResponseIds", promptResponseIdsCSV);

        return StringSubstitutor.replace(queryTemplate, getTokens(), "${", "}");
    }

    @Override
    protected String getQuerySourceTable() {
        return "\"${promptResponseTable}\" PR";
    }

    @Override
    protected ArrayList<String> getQueryColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("PRS.\"${promptTableIdColumn}\" AS PromptId");
        columns.add("PR.\"${promptResponseTableIdColumn}\" AS PromptResponseId");
        columns.add("R.ResponseId");
        columns.add("COALESCE(TCT.Text, TC.Text) AS ResponseText");
        columns.add("SL.ScriptLineTypeId");
        columns.add("SL.ScriptLineToneId");

        if (isIncludePluginDependencies())
            columns.add("(SELECT GROUP_CONCAT(PluginKey, ',') FROM ResponsePluginDependency RPLD WHERE RPLD.ResponseId = R.ResponseId) AS PluginDependencyKeys");
        if (isIncludePermissionDependencies())
            columns.add("(SELECT GROUP_CONCAT(PermissionKey, ',') FROM ResponsePermissionDependency RPED WHERE RPED.ResponseId = R.ResponseId) AS PermissionDependencyKeys");
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (isFilterOnPromptResponseIds())
            filters.add("AND PR.\"${promptResponseTableIdColumn}\" IN (${promptResponseIds})");
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        ArrayList<String> joins = new ArrayList<>();
        joins.add("INNER JOIN Response R ON R.ResponseId = PR.ResponseId");
        joins.add("INNER JOIN ${promptResponsesTable} PRS ON PRS.\"${promptResponseTableIdColumn}\" = PR.\"${promptResponseTableIdColumn}\"");

        joins.add("INNER JOIN ScriptLine SL ON SL.ScriptLineId = R.ScriptLineId");
        joins.add("INNER JOIN TextContent TC ON TC.TextContentId = SL.TextContentId");
        joins.add("LEFT JOIN TextContentTranslation TCT ON TCT.TextContentId = TCT.TextContentId AND TCT.LanguageCode = '${languageCode}'");

        return joins;
    }
}
