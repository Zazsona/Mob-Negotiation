package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamicOfferPromptScriptNode extends PromptScriptNode implements IPersonalityFilterableNode, INegotiationEntityTypeFilterableNode {
    private Set<NegotiationEntityType> entityAllowList;
    private Set<PersonalityType> personalityAllowList;
    private boolean canBeInitialOffer;
    private boolean canBeRevisedOffer;
    private boolean canBeRepeatOffer;
    private boolean canBeRejection;
    private boolean canBeAcceptance;

    public DynamicOfferPromptScriptNode()
    {
        super();
        this.personalityAllowList = new HashSet<>();
        this.entityAllowList = new HashSet<>();
    }

    public DynamicOfferPromptScriptNode(ScriptLine scriptLine, List<PromptResponseScriptNode> responses, Set<PersonalityType> personalityAllowList, Set<NegotiationEntityType> entityAllowList, boolean canBeInitialOffer, boolean canBeRevisedOffer, boolean canBeRepeatOffer, boolean canBeRejection, boolean canBeAcceptance)
    {
        super(scriptLine, responses);
        this.personalityAllowList = personalityAllowList;
        this.entityAllowList = entityAllowList;
        this.canBeInitialOffer = canBeInitialOffer;
        this.canBeRevisedOffer = canBeRevisedOffer;
        this.canBeRepeatOffer = canBeRepeatOffer;
        this.canBeRejection = canBeRejection;
        this.canBeAcceptance = canBeAcceptance;
    }

    public boolean canBeInitialOffer() {
        return canBeInitialOffer;
    }

    public void setCanBeInitialOffer(boolean canBeInitialOffer) {
        this.canBeInitialOffer = canBeInitialOffer;
    }

    public boolean canBeRevisedOffer() {
        return canBeRevisedOffer;
    }

    public void setCanBeRevisedOffer(boolean canBeRevisedOffer) {
        this.canBeRevisedOffer = canBeRevisedOffer;
    }

    public boolean canBeRepeatOffer() {
        return canBeRepeatOffer;
    }

    public void setCanBeRepeatOffer(boolean canBeRepeatOffer) {
        this.canBeRepeatOffer = canBeRepeatOffer;
    }

    public boolean canBeRejection() {
        return canBeRejection;
    }

    public void setCanBeRejection(boolean canBeRejection) {
        this.canBeRejection = canBeRejection;
    }

    public boolean canBeAcceptance() {
        return canBeAcceptance;
    }

    public void setCanBeAcceptance(boolean canBeAcceptance) {
        this.canBeAcceptance = canBeAcceptance;
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
