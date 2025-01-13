package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.Language;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
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


    // TODO: Use adhoc SQL scripts to get Prompts


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

        Path path = Paths.get("src/test/resources/GetPromptIdsHoldUpForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                           .replace(":personalityTypeIds", Arrays.toString(personalityIds));

        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
    }

    public List<Integer> getIdleTimeoutPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Path path = Paths.get("src/test/resources/GetPromptIdsIdleTimeoutForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                .replace(":personalityTypeIds", Arrays.toString(personalityIds));

        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
    }

    public List<Integer> getIdleWarningPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Path path = Paths.get("src/test/resources/GetPromptIdsIdleWarningForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                .replace(":personalityTypeIds", Arrays.toString(personalityIds));

        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
    }

    public List<Integer> getItemNegotiationPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes, boolean canBeInitialOffer, boolean canBeRevisedOffer, boolean canBeRepeatOffer, boolean canBeRejection, boolean canBeAcceptance) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Path path = Paths.get("src/test/resources/GetPromptIdsItemNegotationForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                .replace(":personalityTypeIds", Arrays.toString(personalityIds))
                .replace(":canBeInitialOffer", (canBeInitialOffer) ? "1" : "0")
                .replace(":canBeRevisedOffer", (canBeRevisedOffer) ? "1" : "0")
                .replace(":canBeRepeatOffer", (canBeRepeatOffer) ? "1" : "0")
                .replace(":canBeRejection", (canBeRejection) ? "1" : "0")
                .replace(":canBeAcceptance", (canBeAcceptance) ? "1" : "0");
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
    }

    public List<Integer> getMoneyNegotiationPromptIds(List<NegotiationEntityType> entityTypes, List<PersonalityType> personalityTypes, boolean canBeInitialOffer, boolean canBeRevisedOffer, boolean canBeRepeatOffer, boolean canBeRejection, boolean canBeAcceptance) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();
        int[] personalityIds = personalityTypes.stream().mapToInt(PersonalityType::getId).toArray();

        Path path = Paths.get("src/test/resources/GetPromptIdsMoneyNegotationForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                .replace(":personalityTypeIds", Arrays.toString(personalityIds))
                .replace(":canBeInitialOffer", (canBeInitialOffer) ? "1" : "0")
                .replace(":canBeRevisedOffer", (canBeRevisedOffer) ? "1" : "0")
                .replace(":canBeRepeatOffer", (canBeRepeatOffer) ? "1" : "0")
                .replace(":canBeRejection", (canBeRejection) ? "1" : "0")
                .replace(":canBeAcceptance", (canBeAcceptance) ? "1" : "0");
        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
    }

    public List<Integer> getPowerNegotiationPromptIds(List<NegotiationEntityType> entityTypes, boolean canBeInitialPrompt) throws IOException, SQLException {
        int[] entityIds = entityTypes.stream().mapToInt(NegotiationEntityType::getId).toArray();

        Path path = Paths.get("src/test/resources/GetPromptIdsMoneyNegotationForEntityAndPersonality.sql");
        String sqlQuery = String.join("", Files.readAllLines(path));
        sqlQuery = sqlQuery.replace(":entityTypeIds", Arrays.toString(entityIds))
                .replace(":canBeInitialPrompt", (canBeInitialPrompt) ? "1" : "0");

        try (PreparedStatement sqlStatement = getConnection().prepareStatement(sqlQuery)) {
            ResultSet results = sqlStatement.executeQuery();
            return getIntegerValuesAsList(results, "PromptId");
        }
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
