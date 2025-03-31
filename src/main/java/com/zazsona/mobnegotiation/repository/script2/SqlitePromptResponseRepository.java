package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.ResponseType;
import com.zazsona.mobnegotiation.model2.script.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SqlitePromptResponseRepository implements ISqlitePromptResponseRepository {

    protected final String DB_URL;
    protected final String DB_USERNAME;
    protected final String DB_PASSWORD;

    protected SqlQueryScriptHelper scriptHelper;
    protected String preferredLanguageCode;

    protected Connection connection;

    public SqlitePromptResponseRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper) {
        this(dbUrl, username, password, scriptHelper, "en");
    }

    public SqlitePromptResponseRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, String preferredLanguageCode) {
        this.DB_URL = dbUrl;
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;

        this.scriptHelper = scriptHelper;
        this.preferredLanguageCode = preferredLanguageCode;
    }

    public String getPreferredLanguageCode() {
        return preferredLanguageCode;
    }

    public void setPreferredLanguageCode(String preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    public Map<PromptResponseReference, PromptResponseScriptNode> getPromptResponses(PromptTypeSchema promptType, List<Integer> promptIds) throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));
        queryParameters.put(":languageCode", getPreferredLanguageCode());

        Map<Integer, Set<String>> pluginDepsByResponseId = executeResponseDependencyQuery("src/test/resources/GetPromptResponsePluginDependenciesForPrompt.sql", queryParameters);
        Map<Integer, Set<String>> permDepsByResponseId = executeResponseDependencyQuery("src/test/resources/GetPromptResponsePermissionDependenciesForPrompt.sql", queryParameters);
        Map<PromptResponseReference, PromptResponseScriptNode> promptResponsesByRef = executePromptResponseQuery("src/test/resources/GetPromptResponsesForPrompt.sql", queryParameters);

        for (Map.Entry<PromptResponseReference, PromptResponseScriptNode> promptEntry : promptResponsesByRef.entrySet()) {
            int responseId = promptEntry.getKey().getResponseId();
            PromptResponseScriptNode response = promptEntry.getValue();
            response.getPluginKeyAllowList().addAll(pluginDepsByResponseId.get(responseId));
            response.getPermissionKeyAllowList().addAll(permDepsByResponseId.get(responseId));
        }
        return promptResponsesByRef;
    }

    protected Connection getConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed())
            return this.connection;

        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        return this.connection;
    }

    protected AbstractMap.SimpleEntry<Integer, PromptResponseScriptNode> getPromptResponseFromQueryResult(ResultSet rs) throws SQLException {
        Integer responseId = rs.getInt("ResponseId");
        String responseText = rs.getString("ResponseText");
        ScriptLineType responseLineType = ScriptLineType.fromId(rs.getInt("ResponseScriptLineTypeId"));
        ScriptLineTone responseLineTone = ScriptLineTone.fromId(rs.getInt("ResponseScriptLineToneId"));
        ResponseType responseType = ResponseType.fromId(rs.getInt("ResponseTypeId"));
        ScriptLine responseScriptLine = new ScriptLine(responseText, responseLineType, responseLineTone);
        return new AbstractMap.SimpleEntry<>(responseId, new PromptResponseScriptNode(responseScriptLine, responseType));
    }

    protected Map<PromptResponseReference, PromptResponseScriptNode> executePromptResponseQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<PromptResponseReference, PromptResponseScriptNode> promptResponsesByRef = new HashMap<>();
        String responseSqlQuery = scriptHelper.prepareQueryFromFile(queryScriptPath, queryParameters);
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(responseSqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                PromptResponseScriptNode promptResponse = getPromptResponseFromQueryResult(rs).getValue();
                int promptId = rs.getInt("PromptId");
                int responseId = rs.getInt("ResponseId");
                PromptResponseReference ref = new PromptResponseReference(promptId, responseId);
                promptResponsesByRef.put(ref, promptResponse);
            }
        }
        return promptResponsesByRef;
    }

    protected Map<Integer, Set<String>> executeResponseDependencyQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<Integer, Set<String>> dependenciesByResponseId = new HashMap<>();
        String responsePluginDependencySqlQuery = scriptHelper.prepareQueryFromFile(queryScriptPath, queryParameters);
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(responsePluginDependencySqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                int responseId = rs.getInt("ResponseId");
                String dependencyKey = rs.getString("DependencyKey");
                if (!dependenciesByResponseId.containsKey(responseId))
                    dependenciesByResponseId.put(responseId, new HashSet<>());
                dependenciesByResponseId.get(responseId).add(dependencyKey);
            }
        }
        return dependenciesByResponseId;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}
