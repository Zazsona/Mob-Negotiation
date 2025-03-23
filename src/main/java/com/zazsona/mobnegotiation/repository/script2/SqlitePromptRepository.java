package com.zazsona.mobnegotiation.repository.script2;


import com.zazsona.mobnegotiation.model2.*;
import com.zazsona.mobnegotiation.model2.script.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Interfaces with the script database to retrieve data
 */
public class SqlitePromptRepository implements ISqlitePromptRepository {

    protected final String DB_URL;
    protected final String DB_USERNAME;
    protected final String DB_PASSWORD;

    protected SqlQueryScriptHelper scriptHelper;
    protected ISqlitePromptResponseRepository promptResponseRepository;
    protected String preferredLanguageCode;

    protected Connection connection;

    public SqlitePromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository) {
        this(dbUrl, username, password, scriptHelper, promptResponseRepository, "en");
    }

    public SqlitePromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository, String preferredLanguageCode) {
        this.DB_URL = dbUrl;
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;

        this.promptResponseRepository = promptResponseRepository;
        this.scriptHelper = scriptHelper;
        this.preferredLanguageCode = preferredLanguageCode;
    }

    @Override
    public String getPreferredLanguageCode() {
        return preferredLanguageCode;
    }

    @Override
    public void setPreferredLanguageCode(String preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    @Override
    public List<Integer> getPromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, @Nullable List<PersonalityType> personalityTypes) throws IOException, SQLException {
        boolean filterOnEntities = promptType.isEntityRestrictedPrompt() && entityTypes != null;
        boolean filterOnPersonality = promptType.isPersonalityRestrictedPrompt() && personalityTypes != null;
        int[] entityIds = (entityTypes == null) ? new int[0] : entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = (personalityTypes == null) ? new int[0] : personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        if (entityIds.length > 0)
            queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        if (personalityIds.length > 0)
            queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));

        if (filterOnEntities && filterOnPersonality)
            return executePromptIdQuery("src/test/resources/GetPromptIdsForEntityAndPersonality.sql", queryParameters);
        else if (filterOnEntities)
            return executePromptIdQuery("src/test/resources/GetPromptIdsForEntity.sql", queryParameters);
        else if (filterOnPersonality)
            return executePromptIdQuery("src/test/resources/GetPromptIdsForPersonality.sql", queryParameters);
        else
            return executePromptIdQuery("src/test/resources/GetPromptIds.sql", queryParameters);
    }

    @Override
    public Map<Integer, PromptScriptNode> getPrompts(PromptTypeSchema promptType, List<Integer> promptIds, boolean includeResponses) throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));
        queryParameters.put(":languageCode", getPreferredLanguageCode());

        Map<Integer, PromptScriptNode> promptsById = executePromptQuery("src/test/resources/GetPrompts.sql", queryParameters);
        if (includeResponses) {
            Map<PromptResponseReference, PromptResponseScriptNode> responsesByRef = promptResponseRepository.getPromptResponses(promptType, promptIds);
            for (Map.Entry<PromptResponseReference, PromptResponseScriptNode> responseEntry : responsesByRef.entrySet()) {
                int promptId = responseEntry.getKey().getPromptId();
                PromptResponseScriptNode response = responseEntry.getValue();
                PromptScriptNode prompt = promptsById.get(promptId);
                prompt.getResponses().add(response);
            }
        }
        return promptsById;
    }

    protected Connection getConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed())
            return this.connection;

        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        return this.connection;
    }

    protected AbstractMap.SimpleEntry<Integer, PromptScriptNode> getPromptFromQueryResult(ResultSet rs) throws SQLException {
        Integer promptId = rs.getInt("PromptId");
        String promptText = rs.getString("PromptText");
        ScriptLineType promptLineType = ScriptLineType.fromId(rs.getInt("PromptScriptLineTypeId"));
        ScriptLineTone promptLineTone = ScriptLineTone.fromId(rs.getInt("PromptScriptLineToneId"));
        ScriptLine promptScriptLine = new ScriptLine(promptText, promptLineType, promptLineTone);
        return new AbstractMap.SimpleEntry<>(promptId, new PromptScriptNode(promptScriptLine));
    }

    protected List<Integer> executePromptIdQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        String sqlQuery = scriptHelper.prepareQueryFromFile(queryScriptPath, queryParameters);
        List<Integer> promptIds = new ArrayList<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next())
                promptIds.add(rs.getInt("PromptId"));
        }
        return promptIds;
    }

    protected Map<Integer, PromptScriptNode> executePromptQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        String sqlQuery = scriptHelper.prepareQueryFromFile(queryScriptPath, queryParameters);
        Map<Integer, PromptScriptNode> promptsById = new HashMap<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                Map.Entry<Integer, PromptScriptNode> promptById = getPromptFromQueryResult(rs);
                promptsById.put(promptById.getKey(), promptById.getValue());
            }
        }
        return promptsById;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}
