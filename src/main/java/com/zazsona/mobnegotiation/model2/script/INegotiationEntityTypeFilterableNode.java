package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.NegotiationEntityType;

import java.util.Set;

public interface INegotiationEntityTypeFilterableNode {

    Set<NegotiationEntityType> getEntityTypeAllowList();

    boolean isEntityInAllowList(NegotiationEntityType entityType);
}
