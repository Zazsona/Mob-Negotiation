package com.zazsona.mobnegotiation.model.script;

import com.zazsona.mobnegotiation.model.TextType;

public class NegotiationScriptResponseNode
{
    private static final String UNDEFINED_MSG = "No text.";

    private String text = UNDEFINED_MSG;
    private TextType textType = TextType.SPEECH;
    private PersonalityVariant<Float> successRates = new PersonalityVariant<>(100.0f, 100.f, 100.0f, 100.0f);
    private ScriptTextPersonalityVariant successResponses = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private ScriptTextPersonalityVariant failureResponses = new ScriptTextPersonalityVariant(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

    /**
     * Creates a new object with default text and a 100% success rate.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationScriptResponseNode()
    {

    }

    /**
     * Creates a new object with the provided text and a 100% success rate.
     * @param text the text to display
     */
    public NegotiationScriptResponseNode(String text)
    {
        this.text = text;
    }

    /**
     * Creates a new object with the provided text and a 100% success rate.
     * @param text the text to display
     * @param textType the text tone type
     */
    public NegotiationScriptResponseNode(String text, TextType textType)
    {
        this.text = text;
        this.textType = textType;
    }

    /**
     * Creates a new object with the provided text and personality based success rates.
     * @param text the text to display
     * @param textType the text tone type
     * @param successRates the chance to advance on the tree as a percentage for each personality type
     * @param successResponses the response message to for each personality type on success
     * @param failureResponses the response message to for each personality type on failure
     */
    public NegotiationScriptResponseNode(String text, TextType textType, PersonalityVariant<Float> successRates, ScriptTextPersonalityVariant successResponses, ScriptTextPersonalityVariant failureResponses)
    {
        this.text = text;
        this.textType = textType;
        this.successRates = successRates;
        this.successResponses = successResponses;
        this.failureResponses = failureResponses;
    }

    /**
     * Gets text
     * @return text
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets text
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Gets textType
     *
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
     * Gets successRates
     * @return successRates
     */
    public PersonalityVariant<Float> getSuccessRates()
    {
        return successRates;
    }

    /**
     * Sets successRates
     */
    public void setSuccessRates(PersonalityVariant<Float> successRates)
    {
        this.successRates = successRates;
    }

    /**
     * Gets successResponses
     * @return successResponses
     */
    public ScriptTextPersonalityVariant getSuccessResponses()
    {
        return successResponses;
    }

    /**
     * Sets successResponses
     */
    public void setSuccessResponses(ScriptTextPersonalityVariant successResponses)
    {
        this.successResponses = successResponses;
    }

    /**
     * Gets failureResponses
     * @return failureResponses
     */
    public ScriptTextPersonalityVariant getFailureResponses()
    {
        return failureResponses;
    }

    /**
     * Sets failureResponses
     */
    public void setFailureResponses(ScriptTextPersonalityVariant failureResponses)
    {
        this.failureResponses = failureResponses;
    }
}
