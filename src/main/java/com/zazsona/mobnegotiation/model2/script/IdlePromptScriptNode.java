package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdlePromptScriptNode extends PromptScriptNode implements IPersonalityFilterableNode, INegotiationEntityTypeFilterableNode {
    private Set<NegotiationEntityType> entityAllowList;
    private Set<PersonalityType> personalityAllowList;

    public IdlePromptScriptNode()
    {
        super();
        this.personalityAllowList = new HashSet<>();
        this.entityAllowList = new HashSet<>();
    }

    public IdlePromptScriptNode(ScriptLine scriptLine, List<PromptResponseScriptNode> responses, HashSet<PersonalityType> personalityAllowList, HashSet<NegotiationEntityType> entityAllowList)
    {
        super(scriptLine, responses);
        this.personalityAllowList = personalityAllowList;
        this.entityAllowList = entityAllowList;
    }

    @Override
    public Set<PersonalityType> getPersonalityAllowList() {
        return personalityAllowList;
    }

    @Override
    public boolean isPersonalityInAllowList(PersonalityType personalityType) {
        return personalityAllowList.contains(personalityType);
    }

    @Override
    public boolean isEntityInAllowList(NegotiationEntityType entityType) {
        return entityAllowList.contains(entityType);
    }

    @Override
    public Set<NegotiationEntityType> getEntityTypeAllowList() {
        return entityAllowList;
    }
}
