package com.zazsona.mobnegotiation.repository.script;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromptResponseIdQueryBuilder extends AbstractPromptQueryBuilder {
    private boolean filterOnPromptIds;
    private List<Integer> promptIds;
    private boolean filterResponsesOnPlugins;
    private List<String> installedPluginsKeys;
    private boolean filterResponsesOnPlayerPermissions;
    private List<String> playerPermissionKeys;
    private int batchOffset;
    private int batchSize;

    public PromptResponseIdQueryBuilder() {
        super();
        setFilterOnPromptIds(false);
        setPromptIds(null);
        setFilterResponsesOnPlugins(false);
        setInstalledPluginsKeys(null);
        setFilterResponsesOnPlayerPermissions(false);
        setPlayerPermissionKeys(null);
        setBatchOffset(0);
        setBatchSize(100);
    }

    @Override
    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        if (promptType != PromptTypeSchema.UNCLASSIFIED && promptType.getPromptResponseTable() == null)
            throw new NotImplementedException("The provided type does not have a Response table.");
        return super.setPromptType(promptType);
    }

    public List<Integer> getPromptIds() {
        return promptIds;
    }

    public boolean isFilterOnPromptIds() {
        return filterOnPromptIds;
    }

    public PromptResponseIdQueryBuilder setFilterOnPromptIds(boolean filterOnPromptIds) {
        this.filterOnPromptIds = filterOnPromptIds;
        return this;
    }

    public PromptResponseIdQueryBuilder setPromptIds(List<Integer> promptIds) {
        this.promptIds = promptIds;
        return this;
    }

    public boolean isFilterResponsesOnPlugins() {
        return filterResponsesOnPlugins;
    }

    public PromptResponseIdQueryBuilder setFilterResponsesOnPlugins(boolean filterResponsesOnPlugins) {
        this.filterResponsesOnPlugins = filterResponsesOnPlugins;
        return this;
    }

    public List<String> getInstalledPluginsKeys() {
        return installedPluginsKeys;
    }

    public PromptResponseIdQueryBuilder setInstalledPluginsKeys(List<String> installedPluginsKeys) {
        this.installedPluginsKeys = installedPluginsKeys;
        return this;
    }

    public boolean isFilterResponsesOnPlayerPermissions() {
        return filterResponsesOnPlayerPermissions;
    }

    public PromptResponseIdQueryBuilder setFilterResponsesOnPlayerPermissions(boolean filterResponsesOnPlayerPermissions) {
        this.filterResponsesOnPlayerPermissions = filterResponsesOnPlayerPermissions;
        return this;
    }

    public List<String> getPlayerPermissionKeys() {
        return playerPermissionKeys;
    }

    public PromptResponseIdQueryBuilder setPlayerPermissionKeys(List<String> playerPermissionKeys) {
        this.playerPermissionKeys = playerPermissionKeys;
        return this;
    }

    public int getBatchOffset() {
        return batchOffset;
    }

    public PromptResponseIdQueryBuilder setBatchOffset(int batchOffset) {
        this.batchOffset = batchOffset;
        this.getTokens().put("batchOffset", batchOffset);
        return this;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public PromptResponseIdQueryBuilder setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        this.getTokens().put("batchSize", batchSize);
        return this;
    }

    @Override
    public String buildQuery() {
        if (filterOnPromptIds && getPromptIds() == null)
            throw new NullPointerException("Prompt ID filter is enabled, but no IDs have been provided.");
        if (filterResponsesOnPlugins && getInstalledPluginsKeys() == null)
            throw new NullPointerException("Plugin dependency filter is enabled, but no installed plugin keys have been provided.");
        if (filterResponsesOnPlayerPermissions && getPlayerPermissionKeys() == null)
            throw new NullPointerException("Player permissions filter is enabled, but no permission keys have been provided.");

        String queryTemplate = getQueryTemplate();
        String promptIdsCSV = (isFilterOnPromptIds()) ? getPromptIds().stream().map(pid -> Integer.toString(pid)).collect(Collectors.joining(", ")) : null;
        String pluginKeysCSV = (isFilterResponsesOnPlugins()) ? getInstalledPluginsKeys().stream().map(pk -> String.format("'%s'", pk)).collect(Collectors.joining(", ")) : null;
        String permissionKeysCSV = (isFilterResponsesOnPlayerPermissions()) ? getPlayerPermissionKeys().stream().map(pk -> String.format("'%s'", pk)).collect(Collectors.joining(", ")) : null;
        getTokens().put("promptIds", promptIdsCSV);
        getTokens().put("pluginKeys", pluginKeysCSV);
        getTokens().put("permissionKeys", permissionKeysCSV);

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
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (isFilterOnPromptIds())
            filters.add("AND PRS.\"${promptTableIdColumn}\" IN (${promptIds})");
        if (isFilterResponsesOnPlugins())
            filters.add("AND (RD.DependencyType IS NOT 'PLUGIN' OR RD.DependencyKey IN (${pluginKeys}))");
        if (isFilterResponsesOnPlayerPermissions())
            filters.add("AND (RD.DependencyType IS NOT 'PERMISSION' OR RD.DependencyKey IN (${permissionKeys}))");
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        ArrayList<String> joins = new ArrayList<>();
        joins.add("INNER JOIN Response R ON R.ResponseId = PR.ResponseId");
        joins.add("INNER JOIN ${promptResponsesTable} PRS ON PRS.\"${promptResponseTableIdColumn}\" = PR.\"${promptResponseTableIdColumn}\"");

        if (isFilterResponsesOnPlayerPermissions() || isFilterResponsesOnPlugins()) {
            joins.add("""
                    LEFT JOIN (
                        SELECT
                            ResponseId
                        ,   PermissionKey AS DependencyKey
                        ,   'PERMISSION' AS DependencyType
                        FROM ResponsePermissionDependency
                        UNION ALL
                        SELECT
                            ResponseId
                        ,   PluginKey AS DependencyKey
                        ,   'PLUGIN' AS DependencyType
                        FROM ResponsePluginDependency
                    ) RD
                        ON RD.ResponseId = R.ResponseId
                    """);
        }
        return joins;
    }

    @Override
    protected String getQueryFormat() {
        String baseTemplate = super.getQueryFormat();
        return String.format("%s%nOFFSET ${batchOffset}%nLIMIT ${batchSize}", baseTemplate);
    }
}
