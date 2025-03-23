package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.*;
import com.zazsona.mobnegotiation.model2.script.*;
import com.zazsona.mobnegotiation.repository.script2.PromptTypeSchema;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.util.*;

/**
 * Interfaces with the script database to retrieve data
 */
public class SqliteScriptRepository {

    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private final int CONNECTION_IDLE_TIMEOUT_SECONDS;

    private String preferredLanguageCode;
    private Connection connection;
    private Instant lastConnQueryTimestamp;
    private Timer connIdleTimer;

    public SqliteScriptRepository(String dbUrl, @Nullable String username, @Nullable String password) {
        this(dbUrl, username, password, 300);
    }

    public SqliteScriptRepository(String dbUrl, @Nullable String username, @Nullable String password, int connectionIdleTimeoutSeconds) {
        this(dbUrl, username, password, connectionIdleTimeoutSeconds, "en");
    }

    public SqliteScriptRepository(String dbUrl, @Nullable String username, @Nullable String password, int connectionIdleTimeoutSeconds, String preferredLanguageCode) {
        this.DB_URL = dbUrl;
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;
        this.CONNECTION_IDLE_TIMEOUT_SECONDS = connectionIdleTimeoutSeconds;
        this.preferredLanguageCode = preferredLanguageCode;
        this.connection = null;
        this.connIdleTimer = null;
        this.lastConnQueryTimestamp = Instant.EPOCH;
    }

    public String getPreferredLanguageCode() {
        return preferredLanguageCode;
    }

    public void setPreferredLanguageCode(String preferredLanguageCode) {
        this.preferredLanguageCode = preferredLanguageCode;
    }

    public List<Integer> getDialogueTreePromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, boolean requireCanBeInitialPrompt) throws IOException, SQLException {
        if (!promptType.isDialogueTreePrompt())
            throw new IllegalArgumentException(String.format("Prompt Type %s is not a valid Dialogue Tree prompt.", promptType.getPromptTable()));

        boolean filterOnEntities = promptType.isEntityRestrictedPrompt() && entityTypes != null;
        int[] entityIds = (!filterOnEntities) ? new int[0] : entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        if (entityIds.length > 0)
            queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":requireCanBeInitialPrompt", (requireCanBeInitialPrompt) ? "1" : "0");

        if (filterOnEntities)
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDialogueTreePromptIdsForEntity.sql", queryParameters);
        else
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDialogueTreePromptIds.sql", queryParameters);
    }

    public List<Integer> getDynamicOfferPromptIds(PromptTypeSchema promptType, @Nullable List<NegotiationEntityType> entityTypes, @Nullable List<PersonalityType> personalityTypes, boolean requireCanBeInitialOffer, boolean requireCanBeRevisedOffer, boolean requireCanBeRepeatOffer, boolean requireCanBeRejection, boolean requireCanBeAcceptance) throws IOException, SQLException {
        if (!promptType.isDynamicOfferPrompt())
            throw new IllegalArgumentException(String.format("Prompt Type %s is not a valid Dynamic Offer prompt.", promptType.getPromptTable()));

        boolean filterOnEntities = promptType.isEntityRestrictedPrompt() && entityTypes != null;
        boolean filterOnPersonality = promptType.isPersonalityRestrictedPrompt() && personalityTypes != null;
        int[] entityIds = (!filterOnEntities) ? new int[0] : entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = (!filterOnPersonality) ? new int[0] : personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        if (entityIds.length > 0)
            queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        if (personalityIds.length > 0)
            queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));
        queryParameters.put(":requireCanBeInitialOffer", (requireCanBeInitialOffer) ? "1" : "0");
        queryParameters.put(":requireCanBeRevisedOffer", (requireCanBeRevisedOffer) ? "1" : "0");
        queryParameters.put(":requireCanBeRepeatOffer", (requireCanBeRepeatOffer) ? "1" : "0");
        queryParameters.put(":requireCanBeRejection", (requireCanBeRejection) ? "1" : "0");
        queryParameters.put(":requireCanBeAcceptance", (requireCanBeAcceptance) ? "1" : "0");

        if (filterOnEntities && filterOnPersonality)
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDynamicOfferPromptIdsForEntityAndPersonality.sql", queryParameters);
        else if (filterOnEntities)
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDynamicOfferPromptIdsForEntity.sql", queryParameters);
        else if (filterOnPersonality)
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDynamicOfferPromptIdsForPersonality.sql", queryParameters);
        else
            return executePromptIdQuery("src/main/resources/db/adhoc/GetDynamicOfferPromptIds.sql", queryParameters);
    }

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

    public Map<Integer, PromptScriptNode> getPrompts(PromptTypeSchema promptType, List<Integer> promptIds, boolean includeResponses) throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":tableName", promptType.getPromptTable());
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));
        queryParameters.put(":languageCode", Arrays.toString(promptIdsArr));

        return executePromptQuery("src/test/resources/GetPrompts.sql", queryParameters, includeResponses);
    }

    /**
     * Gets the languages supported by this script.
     * This does not guarantee all entries in the script are translated.
     * @return list of supported languages
     * @throws SQLException error connecting to/querying the database
     */
    public List<Language> getSupportedLanguages() throws SQLException {
        Statement statement = null;
        try {
            final String tableName = "Language";
            final String codeCol = "LanguageCode";
            final String nameCol = "Name";

            statement = getConnection().createStatement();
            lastConnQueryTimestamp = Instant.now();
            ResultSet rs = statement.executeQuery(String.format("SELECT \"%s\", \"%s\" FROM \"%s\";", codeCol, nameCol, tableName));

            ArrayList<Language> languages = new ArrayList<>();
            while (rs.next())
            {
                String languageCode = rs.getString(codeCol);
                String languageName = rs.getString(nameCol);
                Language language = new Language(languageCode, languageName);
                languages.add(language);
            }
            return languages;
        }
        finally {
            if (statement != null)
                statement.close();
        }
    }

    private Connection getConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed())
            return this.connection;

        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        TimerTask idleCheckTask = new TimerTask() {
            public void run() {
                try {
                    closeConnectionIfIdle();
                }
                catch (SQLException e) {
                    MobNegotiationPlugin.getInstance().getLogger().warning("Failed to close idle DB connection: " + e);
                }
                finally {
                    connIdleTimer.cancel();
                    connIdleTimer = null;
                }

            }
        };
        connIdleTimer = new Timer("ConnectionIdleTimer");
        connIdleTimer.schedule(idleCheckTask, 30);

        return this.connection;
    }

    private void closeConnectionIfIdle() throws SQLException {
        Instant now = Instant.now();
        Instant threshold = now.minusSeconds(CONNECTION_IDLE_TIMEOUT_SECONDS);
        if (lastConnQueryTimestamp.isBefore(threshold)) {
            if (connection != null && !connection.isClosed())
                connection.close();
        }
    }

    private Map<Integer, PromptScriptNode> getPromptsWithResponsesFromQueryResults(ResultSet rs) throws SQLException {
        Map<Integer, PromptScriptNode> promptsById = new HashMap<>();
        while (rs.next()) {
            Integer promptId = rs.getInt("PromptId");
            if (!promptsById.containsKey(promptId)) {
                Map.Entry<Integer, PromptScriptNode> promptById = getPromptFromQueryResult(rs);
                promptsById.put(promptById.getKey(), promptById.getValue());
            }

            Map.Entry<Integer, PromptResponseScriptNode> responseById = getPromptResponseFromQueryResult(rs);
            promptsById.get(promptId).getResponses().add(responseById.getValue());
        }
        return promptsById;
    }

    private AbstractMap.SimpleEntry<Integer, PromptScriptNode> getPromptFromQueryResult(ResultSet rs) throws SQLException {
        Integer promptId = rs.getInt("PromptId");
        String promptText = rs.getString("PromptText");
        ScriptLineType promptLineType = ScriptLineType.fromId(rs.getInt("PromptScriptLineTypeId"));
        ScriptLineTone promptLineTone = ScriptLineTone.fromId(rs.getInt("PromptScriptLineToneId"));
        ScriptLine promptScriptLine = new ScriptLine(promptText, promptLineType, promptLineTone);
        return new AbstractMap.SimpleEntry<>(promptId, new PromptScriptNode(promptScriptLine));
    }

    private AbstractMap.SimpleEntry<Integer, PromptResponseScriptNode> getPromptResponseFromQueryResult(ResultSet rs) throws SQLException {
        Integer responseId = rs.getInt("ResponseId");
        String responseText = rs.getString("ResponseText");
        ScriptLineType responseLineType = ScriptLineType.fromId(rs.getInt("ResponseScriptLineTypeId"));
        ScriptLineTone responseLineTone = ScriptLineTone.fromId(rs.getInt("ResponseScriptLineToneId"));
        ResponseType responseType = ResponseType.fromId(rs.getInt("ResponseTypeId"));
        ScriptLine responseScriptLine = new ScriptLine(responseText, responseLineType, responseLineTone);
        return new AbstractMap.SimpleEntry<>(responseId, new PromptResponseScriptNode(responseScriptLine, responseType));
    }

    private String prepareQueryFromFile(String queryScriptPath, Map<String, String> queryParameters) throws IOException {
        Path path = Paths.get(queryScriptPath);
        String sqlQuery = String.join("", Files.readAllLines(path));
        for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
            sqlQuery = sqlQuery.replace(parameter.getKey(), parameter.getValue());
        }
        return sqlQuery;
    }

    private List<Integer> executePromptIdQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        String sqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
        List<Integer> promptIds = new ArrayList<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next())
                promptIds.add(rs.getInt("PromptId"));
        }
        return promptIds;
    }

    private Map<Integer, PromptScriptNode> executePromptQuery(String queryScriptPath, Map<String, String> queryParameters, boolean includeResponses) throws IOException, SQLException {
        String sqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
        Map<Integer, PromptScriptNode> promptsById = new HashMap<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                Map.Entry<Integer, PromptScriptNode> promptById = getPromptFromQueryResult(rs);
                promptsById.put(promptById.getKey(), promptById.getValue());
            }
        }

        if (!includeResponses)
            return promptsById;

        Map<Integer, Set<String>> pluginDepsByResponseId = executeResponseDependencyQuery("src/test/resources/GetPromptResponsePluginDependenciesForPrompt.sql", queryParameters);
        Map<Integer, Set<String>> permDepsByResponseId = executeResponseDependencyQuery("src/test/resources/GetPromptResponsePermissionDependenciesForPrompt.sql", queryParameters);
        Map<Integer, Personable<Float>> successRateByResponseId = executeResponseSuccessRateQuery("src/test/resources/GetPromptResponseSuccessRatesForPrompt.sql", queryParameters);
        Map<PromptResponseReference, PromptResponseScriptNode> promptResponsesByRef = executePromptResponseQuery("src/test/resources/GetPromptResponsesForPrompt.sql", queryParameters);

        for (Map.Entry<PromptResponseReference, PromptResponseScriptNode> promptEntry : promptResponsesByRef.entrySet()) {
            PromptResponseReference responseRef = promptEntry.getKey();
            int promptId = responseRef.getPromptId();
            int responseId = responseRef.getResponseId();
            PromptResponseScriptNode response = promptEntry.getValue();

            response.getPluginKeyAllowList().addAll(pluginDepsByResponseId.get(responseId));
            response.getPermissionKeyAllowList().addAll(permDepsByResponseId.get(responseId));
            // TODO: Add success rate details (needs a prompt response object that supports this)

            PromptScriptNode prompt = promptsById.get(promptEntry.getKey().getPromptId());
            prompt.getResponses().add(response);
        }

        return promptsById;
    }

    private Map<PromptResponseReference, PromptResponseScriptNode> executePromptResponseQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<PromptResponseReference, PromptResponseScriptNode> promptResponsesByRef = new HashMap<>();
        String responseSqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
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

    private Map<Integer, Personable<Float>> executeResponseSuccessRateQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<Integer, Personable<Float>> successRateByResponseId = new HashMap<>();
        String responseSuccessRateSqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
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

    private Map<Integer, Set<String>> executeResponseDependencyQuery(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        Map<Integer, Set<String>> dependenciesByResponseId = new HashMap<>();
        String responsePluginDependencySqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
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

    private class PromptResponseReference {
        private int promptId;
        private int responseId;

        public PromptResponseReference(int promptId, int responseId) {
            this.promptId = promptId;
            this.responseId = responseId;
        }

        public int getPromptId() {
            return promptId;
        }

        public int getResponseId() {
            return responseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PromptResponseReference that = (PromptResponseReference) o;
            return getPromptId() == that.getPromptId() && getResponseId() == that.getResponseId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPromptId(), getResponseId());
        }
    }
}
