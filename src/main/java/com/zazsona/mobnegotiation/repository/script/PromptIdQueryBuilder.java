package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PromptIdQueryBuilder extends AbstractPromptQueryBuilder {
    private List<NegotiationEntityType> entities;
    private List<PersonalityType> personalities;
    private boolean includeEntityTypesFilter;
    private boolean includePersonalityTypesFilter;
    private int wildcardPersonalityId;
    private int batchOffset;
    private int batchSize;

    public PromptIdQueryBuilder() {
        super();
        setEntities(null);
        setPersonalities(null);
        setIncludeEntityTypesFilter(false);
        setIncludePersonalityTypesFilter(false);
        setWildcardPersonalityId(PersonalityType.WILDCARD.getId());
        setBatchOffset(0);
        setBatchSize(100);
    }

    public List<NegotiationEntityType> getEntities() {
        return entities;
    }

    public PromptIdQueryBuilder setEntities(List<NegotiationEntityType> entities) {
        this.entities = entities;
        return this;
    }

    public List<PersonalityType> getPersonalities() {
        return personalities;
    }

    public PromptIdQueryBuilder setPersonalities(List<PersonalityType> personalities) {
        this.personalities = personalities;
        return this;
    }

    public boolean isIncludeEntityTypesFilter() {
        return includeEntityTypesFilter;
    }

    public PromptIdQueryBuilder setIncludeEntityTypesFilter(boolean includeEntityTypesFilter) {
        this.includeEntityTypesFilter = includeEntityTypesFilter;
        return this;
    }

    public boolean isIncludePersonalityTypesFilter() {
        return includePersonalityTypesFilter;
    }

    public PromptIdQueryBuilder setIncludePersonalityTypesFilter(boolean includePersonalityTypesFilter) {
        this.includePersonalityTypesFilter = includePersonalityTypesFilter;
        return this;
    }

    public int getBatchOffset() {
        return batchOffset;
    }

    public PromptIdQueryBuilder setBatchOffset(int batchOffset) {
        this.batchOffset = batchOffset;
        this.getTokens().put("batchOffset", batchOffset);
        return this;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public PromptIdQueryBuilder setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        this.getTokens().put("batchSize", batchSize);
        return this;
    }

    public int getWildcardPersonalityId() {
        return wildcardPersonalityId;
    }

    public PromptIdQueryBuilder setWildcardPersonalityId(int wildcardPersonalityId) {
        this.wildcardPersonalityId = wildcardPersonalityId;
        this.getTokens().put("personalityWildcardId", wildcardPersonalityId);
        return this;
    }

    @Override
    public String buildQuery() {
        if (getPromptType() == PromptTypeSchema.POWER && isIncludePersonalityTypesFilter())
            MobNegotiationPlugin.getInstance().getLogger().warning(String.format("The provided Prompt Schema \"%s\" does not support filtering by PersonalityType. Filter will be ignored.", getPromptType().toString()));
        if (isIncludeEntityTypesFilter() && getEntities() == null)
            throw new NullPointerException("Entity Type filter is enabled, but no entities have been provided.");
        if (isIncludePersonalityTypesFilter() && getPersonalities() == null)
            throw new NullPointerException("Personality Type filter is enabled, but no personalities have been provided.");

        String queryFormat = getPromptIdsQueryFormat();
        String entityIdsCSV = (isIncludeEntityTypesFilter()) ? getEntities().stream().map(et -> Integer.toString(et.getId())).collect(Collectors.joining(", ")) : null;
        String personalityIdsCSV = (isIncludePersonalityTypesFilter()) ? getPersonalities().stream().map(pt -> Integer.toString(pt.getId())).collect(Collectors.joining(", ")) : null;
        getTokens().put("entityIds", entityIdsCSV);
        getTokens().put("personalityIds", personalityIdsCSV);

        String query = StringSubstitutor.replace(queryFormat, getTokens(), "${", "}");
        return query;
    }

    private String getPromptIdsQueryFormat() {
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
        if (isIncludeEntityTypesFilter())
            queryBuilder.append(ENTITIES_FILTER_QUERY_FORMAT);
        if (isIncludePersonalityTypesFilter())
            queryBuilder.append(PERSONALITY_FILTER_QUERY_FORMAT);
        queryBuilder.append(QUERY_SUFFIX);
        return queryBuilder.toString();
    }
}
