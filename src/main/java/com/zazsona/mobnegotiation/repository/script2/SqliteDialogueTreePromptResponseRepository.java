package com.zazsona.mobnegotiation.repository.script2;

import com.zazsona.mobnegotiation.model2.Personable;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.script.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SqliteDialogueTreePromptResponseRepository extends SqlitePromptResponseRepository implements ISqliteDialogueTreePromptResponseRepository {

    public SqliteDialogueTreePromptResponseRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper) {
        super(dbUrl, username, password, scriptHelper, "en");
    }

    public SqliteDialogueTreePromptResponseRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, String preferredLanguageCode) {
        super(dbUrl, username, password, scriptHelper, preferredLanguageCode);
    }

    @Override
    public Map<PromptResponseReference, PromptResponseScriptNode> getPromptResponses(PromptTypeSchema promptType, List<Integer> promptIds) throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));
        queryParameters.put(":languageCode", getPreferredLanguageCode());

        Map<PromptResponseReference, PromptResponseScriptNode> responsesByRef = super.getPromptResponses(promptType, promptIds);
        Map<Integer, Personable<Float>> successRateByResponseId = executeResponseSuccessRateQuery("src/test/resources/GetPromptResponseSuccessRatesForPrompt.sql", queryParameters);
        for (Map.Entry<PromptResponseReference, PromptResponseScriptNode> promptEntry : responsesByRef.entrySet()) {
            int responseId = promptEntry.getKey().getResponseId();
            if (!successRateByResponseId.containsKey(responseId))
                continue;

            PromptResponseScriptNode baseReponse = promptEntry.getValue();
            Personable<Float> successRate = successRateByResponseId.get(responseId);
            PersonableDialogueTreeResponseScriptNode response = new PersonableDialogueTreeResponseScriptNode(baseReponse.getScriptLine(), baseReponse.getResponseType(), successRate);
            promptEntry.setValue(response);
        }
        return responsesByRef;
    }

    protected Connection getConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed())
            return this.connection;

        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        return this.connection;
    }

    private Map<Integer, Personable<Float>> executeResponseSuccessRateQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<Integer, Personable<Float>> successRateByResponseId = new HashMap<>();
        String responseSuccessRateSqlQuery = scriptHelper.prepareQueryFromFile(queryScriptPath, queryParameters);
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(responseSuccessRateSqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                int responseId = rs.getInt("ResponseId");
                PersonalityType personalityType = PersonalityType.fromId(rs.getInt("PersonalityId"));
                float successRate = rs.getFloat("SuccessRate");
                if (!successRateByResponseId.containsKey(responseId))
                    successRateByResponseId.put(responseId, new Personable<>());
                successRateByResponseId.get(responseId).setVariant(personalityType, successRate);
            }
        }
        return successRateByResponseId;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}
