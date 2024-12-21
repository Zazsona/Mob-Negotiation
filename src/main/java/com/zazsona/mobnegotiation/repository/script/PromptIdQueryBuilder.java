package com.zazsona.mobnegotiation.repository.script;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.text.StringSubstitutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PromptIdQueryBuilder extends AbstractPromptQueryBuilder {
    private List<NegotiationEntityType> entities;
    private List<PersonalityType> personalities;
    private Set<CyclicPromptScenario> cyclicPromptScenarios;
    private boolean includeEntityTypesFilter;
    private boolean includePersonalityTypesFilter;
    private boolean includeCyclicPromptScenarioFilter;
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

    @Override
    public AbstractPromptQueryBuilder setPromptType(PromptTypeSchema promptType) {
        if (promptType != PromptTypeSchema.UNCLASSIFIED && promptType.getPromptTable() == null)
            throw new NotImplementedException("The provided type does not have a Prompt table.");
        return super.setPromptType(promptType);
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

    public Set<CyclicPromptScenario> getCyclicPromptScenarios() {
        return cyclicPromptScenarios;
    }

    public PromptIdQueryBuilder setCyclicPromptScenarios(Set<CyclicPromptScenario> cyclicPromptScenarios) {
        this.cyclicPromptScenarios = cyclicPromptScenarios;
        return this;
    }

    public boolean isIncludeCyclicPromptScenarioFilter() {
        return includeCyclicPromptScenarioFilter;
    }

    public PromptIdQueryBuilder setIncludeCyclicPromptScenarioFilter(boolean includeCyclicPromptScenarioFilter) {
        this.includeCyclicPromptScenarioFilter = includeCyclicPromptScenarioFilter;
        return this;
    }

    @Override
    public String buildQuery() {
        if (isIncludeEntityTypesFilter() && getEntities() == null)
            throw new NullPointerException("Entity Type filter is enabled, but no entities have been provided.");
        if (isIncludePersonalityTypesFilter() && getPersonalities() == null)
            throw new NullPointerException("Personality Type filter is enabled, but no personalities have been provided.");
        if (!getPromptType().isPersonablePrompt() && isIncludePersonalityTypesFilter())
            MobNegotiationPlugin.getInstance().getLogger().info(String.format("The provided Prompt Schema \"%s\" does not support filtering by PersonalityType. Filter will be ignored.", getPromptType().toString()));
        if (!getPromptType().isCyclicPrompt() && isIncludeCyclicPromptScenarioFilter())
            MobNegotiationPlugin.getInstance().getLogger().info(String.format("The provided Prompt Schema \"%s\" does not support filtering by cyclic prompt scenarios. Filter will be ignored.", getPromptType().toString()));

        String queryTemplate = getQueryTemplate();
        String entityIdsCSV = (isIncludeEntityTypesFilter()) ? getEntities().stream().map(et -> Integer.toString(et.getId())).collect(Collectors.joining(", ")) : null;
        String personalityIdsCSV = (isIncludePersonalityTypesFilter()) ? getPersonalities().stream().map(pt -> Integer.toString(pt.getId())).collect(Collectors.joining(", ")) : null;
        getTokens().put("entityIds", entityIdsCSV);
        getTokens().put("personalityIds", personalityIdsCSV);

        return StringSubstitutor.replace(queryTemplate, getTokens(), "${", "}");
    }

    @Override
    protected String getQuerySourceTable() {
        return "\"${promptTable}\" PT";
    }

    @Override
    protected ArrayList<String> getQueryColumns() {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("PT.\"${promptTableIdColumn}\" AS PromptId");
        return columns;
    }

    @Override
    protected ArrayList<String> getQueryFilters() {
        ArrayList<String> filters = new ArrayList<>();
        if (getPromptType().isPersonablePrompt() && isIncludePersonalityTypesFilter())
            filters.add("AND PT.PersonalityId IN (${personalityIds}) OR PT.PersonalityId = ${personalityWildcardId}");
        if (getPromptType().isCyclicPrompt() && isIncludeCyclicPromptScenarioFilter()) {
            Set<CyclicPromptScenario> scenarios = getCyclicPromptScenarios();
            if (scenarios.contains(CyclicPromptScenario.INITIAL_OFFER))
                filters.add("AND PT.CanBeInitialOffer = 1");
            if (scenarios.contains(CyclicPromptScenario.REVISED_OFFER))
                filters.add("AND PT.CanBeRevisedOffer = 1");
            if (scenarios.contains(CyclicPromptScenario.REPEAT_OFFER))
                filters.add("AND PT.CanBeRepeatOffer = 1");
            if (scenarios.contains(CyclicPromptScenario.REJECTION))
                filters.add("AND PT.CanBeRejection = 1");
            if (scenarios.contains(CyclicPromptScenario.ACCEPTANCE))
                filters.add("AND PT.CanBeAcceptance = 1");
        }
        return filters;
    }

    @Override
    protected ArrayList<String> getQueryJoins() {
        ArrayList<String> joins = new ArrayList<>();
        if (isIncludeEntityTypesFilter())
            joins.add("INNER JOIN \"${promptEntitiesTable}\" PTE ON PT.\"${promptTableIdColumn}\" = PTE.\"${promptTableIdColumn}\" AND PTE.EntityTypeId IN (${entityIds})");
        return joins;
    }

    @Override
    protected String getQueryFormat() {
        String baseTemplate = super.getQueryFormat();
        return String.format("%s%nOFFSET ${batchOffset}%nLIMIT ${batchSize}", baseTemplate);
    }
}
