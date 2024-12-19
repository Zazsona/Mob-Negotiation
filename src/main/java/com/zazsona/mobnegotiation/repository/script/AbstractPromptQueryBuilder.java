package com.zazsona.mobnegotiation.repository.script;

import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPromptQueryBuilder {

    private PromptTypeSchema promptType;
    private Map<String, Object> tokens;

    protected AbstractPromptQueryBuilder() {
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

    /**
     * Builds the SQL query, configured with the specified properties, in a format that is ready for execution.
     * @return a SQL query
     */
    public abstract String buildQuery();

    /**
     * Gets the SQL query with placeholders (denoted by ${foo}) for any implementation-specific values to be fulfilled.
     * @return a SQL query w/ placeholders
     */
    protected String getQueryTemplate() {

        ArrayList<String> columns = getQueryColumns();
        String sourceTable = getQuerySourceTable();
        ArrayList<String> joins = getQueryJoins();
        ArrayList<String> filters = getQueryFilters();

        Map<String, String> queryTokens = new HashMap<>();
        queryTokens.put("columns", String.join("\n,    ", columns));
        queryTokens.put("sourceTable", sourceTable);
        queryTokens.put("joins", String.join("\n", joins));
        queryTokens.put("filters", String.join("\n", filters));
        return StringSubstitutor.replace(getQueryFormat(), queryTokens, "${", "}");
    }

    /**
     * Returns the base table to be used in the "FROM" section of the SQL query. Can include aliases.
     * @return the source table for the query, optionally with an alias.
     */
    protected abstract String getQuerySourceTable();

    /**
     * Gets the columns to be included in the query. These can be prefixed with the alias of the source or any joined tables.
     * These may also include function calls or subqueries.
     * @return columns to add to the query's column section.
     */
    protected abstract ArrayList<String> getQueryColumns();

    /**
     * Gets the filters (conditional statements) to be included in the query.
     * These will never contain "WHERE" as the keyword, so that must be specified in the Query Template with a "WHERE TRUE"
     * @return conditional clauses to add to the query's "WHERE" section.
     */
    protected abstract ArrayList<String> getQueryFilters();

    /**
     * Gets the joins to be included in the query.
     * @return the joins
     */
    protected abstract ArrayList<String> getQueryJoins();

    /**
     * Gets the basic query format structure with placeholders (denoted by ${foo}) to be fulfilled.
     * @return skeleton SQL query with placeholders.
     */
    protected String getQueryFormat() {
        return
                """
                SELECT
                    ${columns}
                FROM ${sourceTable}
                ${joins}
                WHERE TRUE
                ${filters}
                """;
    }

    /**
     * Gets the tokens map used to fulfill placeholders.
     * @return the tokens map
     */
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
    protected Map<String, Object> addSchemaTokensToMap(PromptTypeSchema promptTypeSchema, Map<String, Object> tokens)
    {
        if (promptTypeSchema.getPromptTable() != null && !promptTypeSchema.getPromptTable().isBlank())
            tokens.put("promptTable", promptTypeSchema.getPromptTable());
        else
            tokens.remove("promptTable");

        if (promptTypeSchema.getPromptTableId() != null && !promptTypeSchema.getPromptTableId().isBlank())
            tokens.put("promptTableId", promptTypeSchema.getPromptTableId());
        else
            tokens.remove("promptTableIdColumn");

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
            tokens.remove("promptResponseTableIdColumn");

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
