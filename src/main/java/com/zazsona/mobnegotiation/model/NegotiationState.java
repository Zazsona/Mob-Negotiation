package com.zazsona.mobnegotiation.model;

public enum NegotiationState
{
    NONE(false, false),
    INITIALISING(false, false),
    STARTED(false, false),
    ACTIVE_POWER(false, false),
    ACTIVE_ITEM(false, false),
    ACTIVE_MONEY(false, false),
    ACTIVE_ATTACK(false, false),
    FINISHED_POWER(true, false),
    FINISHED_ITEM(true, false),
    FINISHED_MONEY(true, false),
    FINISHED_ATTACK(true, false),
    FINISHED_CANCEL(true, false),
    FINISHED_TIMEOUT(true, false),
    FINISHED_ERROR_INITIALISATION_FAILURE(true, true),
    FINISHED_ERROR_ENTITY_LOST(true, true),
    FINISHED_ERROR_UNKNOWN(true, true);

    private final boolean isTerminating;
    private final boolean isError;

    NegotiationState(boolean isTerminating, boolean isError)
    {
        this.isTerminating = isTerminating;
        this.isError = isError;
    }

    /**
     * Gets if this enum value represents a terminating point within negotiations.
     * @return true is this state ends a negotiation
     */
    public boolean isTerminating()
    {
        return this.isTerminating;
    }

    /**
     * Gets if this enum value represents an erroneous state
     * @return true is this state results from an error
     */
    public boolean isErroneous()
    {
        return this.isError;
    }
}
