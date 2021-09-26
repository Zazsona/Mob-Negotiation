package com.zazsona.mobnegotiation;

public enum NegotiationState
{
    NONE(false),
    STARTED(false),
    FINISHED(true);

    public final boolean isTerminating;

    NegotiationState(boolean isTerminating)
    {
        this.isTerminating = isTerminating;
    }

    /**
     * Gets if this enum value represents a terminating point within negotiations.
     * @return true is this state ends a negotiation
     */
    public boolean isTerminating()
    {
        return this.isTerminating;
    }
}
