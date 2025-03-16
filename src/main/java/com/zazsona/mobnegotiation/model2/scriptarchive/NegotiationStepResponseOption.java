package com.zazsona.mobnegotiation.model2.scriptarchive;

import com.zazsona.mobnegotiation.model.TextType;
import com.zazsona.mobnegotiation.model2.script.ResponseActionType;

import java.util.UUID;

public class NegotiationStepResponseOption {

    private String id;
    private String text = "...";
    private TextType textType = TextType.SPEECH;
    private ResponseActionType responseType = ResponseActionType.NEGOTIATE;

    public NegotiationStepResponseOption(String text)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public NegotiationStepResponseOption(String text, TextType textType)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.textType = textType;
    }

    public NegotiationStepResponseOption(String text, ResponseActionType responseType)
    {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.responseType = responseType;
    }

    public NegotiationStepResponseOption(String text, TextType textType, ResponseActionType responseType)
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
    public ResponseActionType getResponseType()
    {
        return responseType;
    }

    public void setResponseType(ResponseActionType responseType)
    {
        this.responseType = responseType;
    }
}
