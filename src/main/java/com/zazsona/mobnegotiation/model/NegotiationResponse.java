package com.zazsona.mobnegotiation.model;

import java.util.UUID;

public class NegotiationResponse
{
    private String id;
    private String text = "...";
    private TextType textType = TextType.SPEECH;
    private NegotiationResponseType responseType = NegotiationResponseType.PARLEY;

    public NegotiationResponse(String text)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public NegotiationResponse(String text, TextType textType)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.textType = textType;
    }

    public NegotiationResponse(String text, NegotiationResponseType responseType)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.responseType = responseType;
    }

    public NegotiationResponse(String text, TextType textType, NegotiationResponseType responseType)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.textType = textType;
        this.responseType = responseType;
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
     * Gets textType
     * @return textType
     */
    public TextType getTextType()
    {
        return textType;
    }

    public void setTextType(TextType textType)
    {
        this.textType = textType;
    }

    /**
     * Gets type
     *
     * @return type
     */
    public NegotiationResponseType getResponseType()
    {
        return responseType;
    }

    public void setResponseType(NegotiationResponseType responseType)
    {
        this.responseType = responseType;
    }
}
