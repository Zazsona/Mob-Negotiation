package com.zazsona.mobnegotiation;

public enum NegotiationState
{
    NONE(false),
    INITIALISING(false),
    STARTED(false),
    FINISHED_POWER(true),
    FINISHED_ITEM(true),
    FINISHED_ATTACK(true),
    FINISHED_CANCEL(true),
    FINISHED_ERROR_INITIALISATION_FAILURE(true),
    FINISHED_ERROR_ENTITY_LOST(true);

    private final boolean isTerminating;

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
