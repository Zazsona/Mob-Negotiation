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
    public List<Integer> getPromptIds(PromptTypeSchema promptTypeSchema, List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes, int batchOffset, int batchSize) throws SQLException {
        String query = new PromptIdQueryBuilder()
                .setEntities(entityTypes)
                .setIncludeEntityTypesFilter(entityTypes != null)
                .setPersonalities(personalityTypes)
                .setIncludePersonalityTypesFilter(personalityTypes != null)
                .setBatchOffset(batchOffset)
                .setBatchSize(batchSize)
                .setPromptType(promptTypeSchema)
                .buildQuery();

        return getIntegerValuesAsList(query, promptTypeSchema.getPromptTableId());
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
