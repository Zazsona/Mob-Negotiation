package com.zazsona.mobnegotiation.repository.script2;


import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import com.zazsona.mobnegotiation.model2.script.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Interfaces with the script database to retrieve data
 */
public class SqliteDynamicOfferPromptRepository extends SqlitePromptRepository implements ISqliteDynamicOfferPromptRepository {

    public SqliteDynamicOfferPromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository) {
        super(dbUrl, username, password, scriptHelper, promptResponseRepository, "en");
    }

    public SqliteDynamicOfferPromptRepository(String dbUrl, @Nullable String username, @Nullable String password, SqlQueryScriptHelper scriptHelper, ISqlitePromptResponseRepository promptResponseRepository, String preferredLanguageCode) {
        super(dbUrl, username, password, scriptHelper, promptResponseRepository, preferredLanguageCode);
    }

    @Override
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
}
