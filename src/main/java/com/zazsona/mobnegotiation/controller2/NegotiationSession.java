package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.Negotiation;
import view2.world.NegotiationWorldState;

public class NegotiationSession
{
    private Negotiation negotiation;
    private NegotiationWorldState negotiationWorldState;

    public NegotiationSession(Negotiation negotiation, NegotiationWorldState negotiationWorldState) {
        this.negotiation = negotiation;
        this.negotiationWorldState = negotiationWorldState;
    }

    public Negotiation getNegotiation() {
        return negotiation;
    }

    public NegotiationWorldState getNegotiationWorldState() {
        return negotiationWorldState;
    }
}
