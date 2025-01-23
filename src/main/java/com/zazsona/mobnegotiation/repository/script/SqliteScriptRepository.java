package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.Language;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.ResponseType;
import com.zazsona.mobnegotiation.model2.script.*;

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


    // TODO: Add method to get sequel power prompt IDs


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

    public List<Integer> getHoldUpPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));

        return getPromptIds("src/test/resources/GetPromptIdsHoldUpForEntityAndPersonality.sql", queryParameters);
    }

    /**
     * Gets the prompts (including their responses) for the provided IDs
     * @param promptIds the IDs to get prompts for
     * @return a map of prompts, keyed by the prompt ID
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, PromptScriptNode> getHoldUpPrompts(List<Integer> promptIds) throws IOException, SQLException {
        return getHoldUpPrompts(promptIds, null, null);
    }

    /**
     * Gets the prompts for the provided IDs including responses where their dependencies are present in the pluginKeys and permissionKeys
     * @param promptIds the IDs to get prompts for
     * @param pluginKeys the available plugins to check against response dependencies
     * @param permissionKeys the available permissions to check against response dependencies
     * @return a map of prompts, keyed by the prompt ID
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, PromptScriptNode> getHoldUpPrompts(List<Integer> promptIds, List<String> pluginKeys, List<String> permissionKeys) throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        boolean isPluginKeysNullOrEmpty = (pluginKeys == null || pluginKeys.isEmpty());
        boolean isPermissionKeysNullOrEmpty = (permissionKeys == null || permissionKeys.isEmpty());

        String promptIdsCsv = Arrays.toString(promptIdsArr);
        String pluginKeysCsv = (isPluginKeysNullOrEmpty) ? "NULL" : String.join(",", pluginKeys);
        String permissionKeysCsv = (isPermissionKeysNullOrEmpty) ? "NULL" : String.join(",", permissionKeys);

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":filterPluginDependencies", (isPluginKeysNullOrEmpty) ? "0" : "1");
        queryParameters.put(":pluginKeys", pluginKeysCsv);
        queryParameters.put(":filterPermissionDependencies", (isPermissionKeysNullOrEmpty) ? "0" : "1");
        queryParameters.put(":permissionKeys", permissionKeysCsv);
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", promptIdsCsv);

        return getPrompts("src/test/resources/GetPromptsHoldUp.sql", queryParameters, true);
    }

    public List<Integer> getIdleTimeoutPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));

        return getPromptIds("src/test/resources/GetPromptIdsIdleTimeoutForEntityAndPersonality.sql", queryParameters);
    }

    public List<Integer> getIdleWarningPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));

        return getPromptIds("src/test/resources/GetPromptIdsIdleWarningForEntityAndPersonality.sql", queryParameters);
    }

    /**
     * Gets the idle timeout prompts for the provided IDs
     * @param promptIds the IDs to get prompts for
     * @return a map of prompts, keyed by the prompt ID
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, PromptScriptNode> getIdleTimeoutPrompts(List<Integer> promptIds)  throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));

        return getPrompts("src/test/resources/GetPromptsIdleTimeout.sql", queryParameters, false);
    }

    /**
     * Gets the idle warning prompts for the provided IDs
     * @param promptIds the IDs to get prompts for
     * @return a map of prompts, keyed by the prompt ID
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, PromptScriptNode> getIdleWarningPrompts(List<Integer> promptIds)  throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));

        return getPrompts("src/test/resources/GetPromptsIdleWarning.sql", queryParameters, false);
    }

    public List<Integer> getItemNegotiationPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes, boolean canBeInitialOffer, boolean canBeRevisedOffer, boolean canBeRepeatOffer, boolean canBeRejection, boolean canBeAcceptance) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));
        queryParameters.put(":canBeInitialOffer", (canBeInitialOffer) ? "1" : "0");
        queryParameters.put(":canBeRevisedOffer", (canBeRevisedOffer) ? "1" : "0");
        queryParameters.put(":canBeRepeatOffer", (canBeRepeatOffer) ? "1" : "0");
        queryParameters.put(":canBeRejection", (canBeRejection) ? "1" : "0");
        queryParameters.put(":canBeAcceptance", (canBeAcceptance) ? "1" : "0");

        return getPromptIds("src/test/resources/GetPromptIdsItemNegotiationForEntityAndPersonality.sql", queryParameters);
    }

    public Map<Integer, PromptScriptNode> getItemNegotiationPrompts(List<Integer> promptIds)  throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));

        return getPrompts("src/test/resources/GetPromptsItemNegotiation.sql", queryParameters, false);
    }

    public List<Integer> getMoneyNegotiationPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes, boolean canBeInitialOffer, boolean canBeRevisedOffer, boolean canBeRepeatOffer, boolean canBeRejection, boolean canBeAcceptance) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIds));
        queryParameters.put(":canBeInitialOffer", (canBeInitialOffer) ? "1" : "0");
        queryParameters.put(":canBeRevisedOffer", (canBeRevisedOffer) ? "1" : "0");
        queryParameters.put(":canBeRepeatOffer", (canBeRepeatOffer) ? "1" : "0");
        queryParameters.put(":canBeRejection", (canBeRejection) ? "1" : "0");
        queryParameters.put(":canBeAcceptance", (canBeAcceptance) ? "1" : "0");

        return getPromptIds("src/test/resources/GetPromptIdsMoneyNegotiationForEntityAndPersonality.sql", queryParameters);
    }

    public Map<Integer, PromptScriptNode> getMoneyNegotiationPrompts(List<Integer> promptIds)  throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));

        return getPrompts("src/test/resources/GetPromptsMoneyNegotiation.sql", queryParameters, false);
    }

    public List<Integer> getPowerNegotiationPromptIds(List<NegotiationEntityType> entityTypes, boolean canBeInitialPrompt) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":entityTypeIds", Arrays.toString(entityIds));
        queryParameters.put(":canBeInitialPrompt", (canBeInitialPrompt) ? "1" : "0");

        return getPromptIds("src/test/resources/GetPromptIdsPowerNegotiationForEntity.sql", queryParameters);
    }

    public Map<Integer, PromptScriptNode> getPowerNegotiationPrompts(List<Integer> promptIds, List<PersonalityType> personalityTypes)  throws IOException, SQLException {
        int[] promptIdsArr = promptIds.stream().mapToInt(Integer::intValue).toArray();
        int[] personalityIdsArr = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(":languageCode", preferredLanguageCode);
        queryParameters.put(":promptIds", Arrays.toString(promptIdsArr));
        queryParameters.put(":personalityTypeIds", Arrays.toString(personalityIdsArr));

        String sqlQuery = prepareQueryFromFile("src/test/resources/GetPromptsPowerNegotiation.sql", queryParameters);
        Map<Integer, PromptScriptNode> promptsById = new HashMap<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                Integer promptId = rs.getInt("PromptId");
                if (!promptsById.containsKey(promptId)) {
                    Map.Entry<Integer, PromptScriptNode> promptById = getPromptFromQueryResult(rs);
                    promptsById.put(promptById.getKey(), promptById.getValue());
                }

                PromptResponseScriptNode baseResponse = getPromptResponseFromQueryResult(rs).getValue();
                float responseSuccessRate = rs.getFloat("ResponseSuccessRate");
                SuccessRatedPromptResponseScriptNode responseNode = new SuccessRatedPromptResponseScriptNode(baseResponse.getScriptLine(), baseResponse.getResponseType(), responseSuccessRate);
                promptsById.get(promptId).getChildren().add(responseNode);
            }
        }
        return promptsById;
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

    private Map<Integer, PromptScriptNode> getPromptsFromQueryResults(ResultSet rs) throws SQLException {
        Map<Integer, PromptScriptNode> promptsById = new HashMap<>();
        while (rs.next()) {
            Map.Entry<Integer, PromptScriptNode> promptById = getPromptFromQueryResult(rs);
            promptsById.put(promptById.getKey(), promptById.getValue());
        }
        return promptsById;
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

    private List<Integer> getPromptIds(String queryScriptPath, Map<String, String> queryParameters) throws IOException, SQLException {
        String sqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
        List<Integer> promptIds = new ArrayList<>();
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next())
                promptIds.add(rs.getInt("PromptId"));
        }
        return promptIds;
    }

    private Map<Integer, PromptScriptNode> getPrompts(String queryScriptPath, Map<String, String> queryParameters, boolean includeResponses) throws IOException, SQLException {
        String sqlQuery = prepareQueryFromFile(queryScriptPath, queryParameters);
        Map<Integer, PromptScriptNode> prompts;
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet rs = sqlStatement.executeQuery();
            prompts = (includeResponses) ? getPromptsWithResponsesFromQueryResults(rs) : getPromptsFromQueryResults(rs);
        }
        return prompts;
    }
}
