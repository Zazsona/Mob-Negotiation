package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.Language;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.script.ScriptLine;
import com.zazsona.mobnegotiation.model2.script.ScriptLineTone;
import com.zazsona.mobnegotiation.model2.script.ScriptLineType;
import org.apache.commons.text.StringSubstitutor;

import javax.annotation.Nullable;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Interfaces with the script database to retrieve data
 */
public class SqliteScriptRepository {
    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private final int CONNECTION_IDLE_TIMEOUT_SECONDS;

    private Connection connection;
    private Instant lastConnQueryTimestamp;
    private Timer connIdleTimer;

    public SqliteScriptRepository(String dbUrl, @Nullable String username, @Nullable String password) {
        this(dbUrl, username, password, 300);
    }

    public SqliteScriptRepository(String dbUrl, @Nullable String username, @Nullable String password, int connectionIdleTimeoutSeconds) {
        this.DB_URL = dbUrl;
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;
        this.CONNECTION_IDLE_TIMEOUT_SECONDS = connectionIdleTimeoutSeconds;
        this.connection = null;
        this.connIdleTimer = null;
        this.lastConnQueryTimestamp = Instant.EPOCH;
    }

    // TODO: Review the below function
    // TODO: Make a getPrompts function with similar abstraction

    /**
     * Gets the IDs for Prompts that satisfy the provided filters
     * @param promptTypeSchema the type of prompt to get
     * @param entityTypes the entity type(s) that the prompts can be associated with
     * @param personalityTypes the personality type(s) that the prompts can be associated with
     * @param batchOffset the offset for the current batch
     * @param batchSize the size of the batches
     * @return a list of IDs for prompts that meet the criteria
     * @throws SQLException error querying database
     */
    public List<Integer> getPromptIds(PromptTypeSchema promptTypeSchema, NegotiationEntityType[] entityTypes, PersonalityType[] personalityTypes, int batchOffset, int batchSize) throws SQLException {
        if (promptTypeSchema == PromptTypeSchema.POWER && personalityTypes != null)
            throw new IllegalArgumentException(String.format("The provided Prompt Schema \"%s\" does not support filtering by PersonalityType.", promptTypeSchema.toString()));

        boolean filterOnEntityType = entityTypes != null;
        boolean filterOnPersonalityType = personalityTypes != null;

        String queryFormat = getPromptIdsQueryFormat(filterOnEntityType, filterOnPersonalityType);
        String entityIdsCSV = (filterOnEntityType) ? Arrays.stream(entityTypes).map(et -> Integer.toString(et.getId())).collect(Collectors.joining(", ")) : null;
        String personalityIdsCSV = (filterOnPersonalityType) ? Arrays.stream(personalityTypes).map(pt -> Integer.toString(pt.getId())).collect(Collectors.joining(", ")) : null;

        Map<String, Object> tokens = addSchemaTokensToMap(promptTypeSchema, new HashMap<>());
        tokens.put("entityIds", entityIdsCSV);
        tokens.put("personalityIds", personalityIdsCSV);
        tokens.put("personalityWildcardId", PersonalityType.WILDCARD);
        tokens.put("batchSize", batchSize);
        tokens.put("batchOffset", batchOffset);

        String query = StringSubstitutor.replace(queryFormat, tokens, "${", "}");
        return getIntegerValuesAsList(query, promptTypeSchema.getPromptTableId());
    }

    private static String getPromptIdsQueryFormat(boolean includeEntityTypesFilter, boolean includePersonalityTypesFilter) {
        final String BASE_QUERY_FORMAT =
                """
                SELECT
                    PT."${promptIdColumn}"
                FROM "${promptTable}" PT
                """;

        final String ENTITIES_FILTER_QUERY_FORMAT =
                """
                INNER JOIN "${promptEntitiesTable}" PTE
                    ON PT."${promptIdColumn}" = PTE."${promptIdColumn}"
                    AND PTE.EntityTypeId IN (${entityIds})
                """;

        final String PERSONALITY_FILTER_QUERY_FORMAT =
                """
                WHERE PT.PersonalityId IN (${personalityIds})
                OR PT.PersonalityId = ${personalityWildcardId}
                """;

        final String QUERY_SUFFIX =
                """
                LIMIT ${batchSize}
                OFFSET ${batchOffset}
                ;""";

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(BASE_QUERY_FORMAT);
        if (includeEntityTypesFilter)
            queryBuilder.append(ENTITIES_FILTER_QUERY_FORMAT);
        if (includePersonalityTypesFilter)
            queryBuilder.append(PERSONALITY_FILTER_QUERY_FORMAT);
        queryBuilder.append(QUERY_SUFFIX);
        return queryBuilder.toString();
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
            ResultSet rs = statement.executeQuery(String.format("SELECT %s, %s FROM %s;", codeCol, nameCol, tableName));

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

    /**
     * Adds values to the provided tokens map for the various schema properties of the given PromptType.
     * If the property value in {@link PromptTypeSchema} is null or blank, no mapping will be added.
     * If a mapping is already present for a given key, it will not be altered.
     * @param promptTypeSchema the prompt type schema details
     * @param tokens the map to add to
     * @return reference to the same map that was passed in for easy chaining
     */
    private Map<String, Object> addSchemaTokensToMap(PromptTypeSchema promptTypeSchema, Map<String, Object> tokens)
    {
        if (promptTypeSchema.getPromptTable() != null && !promptTypeSchema.getPromptTable().isBlank())
            tokens.putIfAbsent("promptTable", promptTypeSchema.getPromptTable());
        if (promptTypeSchema.getPromptTableId() != null && !promptTypeSchema.getPromptTableId().isBlank())
            tokens.putIfAbsent("promptTableId", promptTypeSchema.getPromptTableId());
        if (promptTypeSchema.getPromptEntitiesTable() != null && !promptTypeSchema.getPromptEntitiesTable().isBlank())
            tokens.putIfAbsent("promptEntitiesTable", promptTypeSchema.getPromptEntitiesTable());
        if (promptTypeSchema.getPromptResponsesTable() != null && !promptTypeSchema.getPromptResponsesTable().isBlank())
            tokens.putIfAbsent("promptResponsesTable", promptTypeSchema.getPromptResponsesTable());
        if (promptTypeSchema.getPromptResponseSuccessRateTable() != null && !promptTypeSchema.getPromptResponseSuccessRateTable().isBlank())
            tokens.putIfAbsent("promptResponseSuccessRateTable", promptTypeSchema.getPromptResponseSuccessRateTable());
        if (promptTypeSchema.getPromptLinkTable() != null && !promptTypeSchema.getPromptLinkTable().isBlank())
            tokens.putIfAbsent("promptLinkTable", promptTypeSchema.getPromptLinkTable());

        return tokens;
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

    private List<Integer> getIntegerValuesAsList(String query, String columnName) throws SQLException
    {
        try (Statement statement = getConnection().createStatement()) {
            lastConnQueryTimestamp = Instant.now();
            ResultSet rs = statement.executeQuery(query);
            return getIntegerValuesAsList(rs, columnName);
        }
    }

    private static ArrayList<Integer> getIntegerValuesAsList(ResultSet rs, String columnName) throws SQLException {
        ArrayList<Integer> values = new ArrayList<>();
        while (rs.next())
            values.add(rs.getInt(columnName));
        return values;
    }

    private List<String> getStringValuesAsList(String query, String columnName) throws SQLException
    {
        try (Statement statement = getConnection().createStatement()) {
            lastConnQueryTimestamp = Instant.now();
            ResultSet rs = statement.executeQuery(query);
            return getStringValuesAsList(rs, columnName);
        }
    }

    private static ArrayList<String> getStringValuesAsList(ResultSet rs, String columnName) throws SQLException {
        ArrayList<String> values = new ArrayList<>();
        while (rs.next())
            values.add(rs.getString(columnName));
        return values;
    }

    private List<ScriptLine> getScriptLineValuesAsList(String query, String textColumnName, String typeColumnName, String toneColumnName) throws SQLException
    {
        try (Statement statement = getConnection().createStatement()) {
            lastConnQueryTimestamp = Instant.now();
            ResultSet rs = statement.executeQuery(query);
            return getScriptLineValuesAsList(rs, textColumnName, typeColumnName, toneColumnName);
        }
    }

    private static List<ScriptLine> getScriptLineValuesAsList(ResultSet rs, String textColumnName, String typeColumnName, String toneColumnName) throws SQLException {
        ArrayList<ScriptLine> values = new ArrayList<>();
        while (rs.next())
        {
            String text = rs.getString(textColumnName);
            ScriptLineType lineType = ScriptLineType.fromId(rs.getInt(typeColumnName));
            ScriptLineTone lineTone = ScriptLineTone.fromId(rs.getInt(toneColumnName));

            ScriptLine scriptLine = new ScriptLine(text, lineType, lineTone);
            values.add(scriptLine);
        }
        return values;
    }
}
