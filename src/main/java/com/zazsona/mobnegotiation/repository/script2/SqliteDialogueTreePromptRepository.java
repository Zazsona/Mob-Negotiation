package com.zazsona.mobnegotiation.repository.script2;


import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interfaces with the script database to retrieve data
 */
public class SqliteDialogueTreePromptRepository extends SqlitePromptRepository implements ISqliteDialogueTreePromptRepository {

    public SqliteDialogueTreePromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository) {
        super(dbUrl, username, password, scriptHelper, promptResponseRepository, "en");
    }

    public SqliteDialogueTreePromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository, String preferredLanguageCode) {
        super(dbUrl, username, password, scriptHelper, promptResponseRepository, preferredLanguageCode);
    }

    @Override
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
}
