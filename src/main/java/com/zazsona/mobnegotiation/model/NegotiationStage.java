package com.zazsona.mobnegotiation.model;

import java.util.ArrayList;
import java.util.Arrays;

public class NegotiationStage
{
    private String negotiationId;
    private NegotiationState state;
    private String playerName;
    private String mobName;
    private PersonalityType mobPersonality;
    private String mobMessage;
    private ArrayList<NegotiationResponse> responses;

    public NegotiationStage(String negotiationId, NegotiationState state, String playerName, String mobName, PersonalityType mobPersonality, String mobMessage, NegotiationResponse... responses)
    {
        this.negotiationId = negotiationId;
        this.state = state;
        this.playerName = playerName;
        this.mobName = mobName;
        this.mobPersonality = mobPersonality;
        this.mobMessage = mobMessage;
        this.responses = new ArrayList<>();
        this.responses.addAll(Arrays.asList(responses));
    }

    /**
     * Gets negotiation id
     *
     * @return id
     */
    public String getNegotiationId()
    {
        return negotiationId;
    }

    /**
     * Gets state
     *
     * @return state
     */
    public NegotiationState getState()
    {
        return state;
    }

    public void setState(NegotiationState state)
    {
        this.state = state;
    }

    /**
     * Gets playerName
     *
     * @return playerName
     */
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    /**
     * Gets mobName
     *
     * @return mobName
     */
    public String getMobName()
    {
        return mobName;
    }

    public void setMobName(String mobName)
    {
        this.mobName = mobName;
    }

    /**
     * Gets mobPersonality
     *
     * @return mobPersonality
     */
    public PersonalityType getMobPersonality()
    {
        return mobPersonality;
    }

    public void setMobPersonality(PersonalityType mobPersonality)
    {
        this.mobPersonality = mobPersonality;
    }

    /**
     * Gets mobMessage
     *
     * @return mobMessage
     */
    public String getMobMessage()
    {
        return mobMessage;
    }

    public void setMobMessage(String mobMessage)
    {
        this.mobMessage = mobMessage;
    }

    /**
     * Gets responses
     *
     * @return responses
     */
    public ArrayList<NegotiationResponse> getResponses()
    {
        return responses;
    }
}
