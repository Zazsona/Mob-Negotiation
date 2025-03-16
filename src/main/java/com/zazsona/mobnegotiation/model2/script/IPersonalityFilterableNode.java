package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;
import com.zazsona.mobnegotiation.model2.PersonalityType;

import java.util.List;
import java.util.Set;

public interface IPersonalityFilterableNode {

    Set<PersonalityType> getPersonalityAllowList();

    boolean isPersonalityInAllowList(PersonalityType personalityType);
}
