package com.zazsona.mobnegotiation.model;

import java.util.UUID;

public class NegotiationResponse
{
    private String id;
    private String text;
    private NegotiationResponseType type;

    public NegotiationResponse(String text, NegotiationResponseType type)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.type = type;
    }

    /**
     * Gets id
     *
     * @return id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Gets text
     *
     * @return text
     */
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Gets type
     *
     * @return type
     */
    public NegotiationResponseType getType()
    {
        return type;
    }

    public void setType(NegotiationResponseType type)
    {
        this.type = type;
    }
}
