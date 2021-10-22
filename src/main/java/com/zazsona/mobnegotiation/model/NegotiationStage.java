package com.zazsona.mobnegotiation.model;

import java.util.ArrayList;
import java.util.Arrays;

public class NegotiationStage
{
    private String negotiationId;
    private String playerName;
    private String mobName;
    private String mobMessage;
    private ArrayList<NegotiationResponse> responses;

    public NegotiationStage(String negotiationId, String playerName, String mobName, String mobMessage, NegotiationResponse... responses)
    {
        this.negotiationId = negotiationId;
        this.playerName = playerName;
        this.mobName = mobName;
        this.mobMessage = mobMessage;
        this.responses = new ArrayList<>();
        this.responses.addAll(Arrays.asList(responses));
    }

    public NegotiationStage(String negotiationId, String playerName, String mobName, String mobMessage)
    {
        this.negotiationId = negotiationId;
        this.playerName = playerName;
        this.mobName = mobName;
        this.mobMessage = mobMessage;
        this.responses = new ArrayList<>();
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
