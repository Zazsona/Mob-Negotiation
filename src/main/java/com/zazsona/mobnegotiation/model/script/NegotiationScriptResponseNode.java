package com.zazsona.mobnegotiation.model.script;

public class NegotiationScriptResponseNode
{
    private static final String UNDEFINED_MSG = "No text.";

    private String text = UNDEFINED_MSG;
    private PersonalityVariant<Float> successRates = new PersonalityVariant<>(100.0f, 100.f, 100.0f, 100.0f);
    private PersonalityVariant<String> successResponses = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);
    private PersonalityVariant<String> failureResponses = new PersonalityVariant<>(UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG, UNDEFINED_MSG);

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
     * Creates a new object with the provided text and personality based success rates.
     * @param text the text to display
     * @param successRates the chance to advance on the tree as a percentage for each personality type
     * @param successResponses the response message to for each personality type on success
     * @param failureResponses the response message to for each personality type on failure
     */
    public NegotiationScriptResponseNode(String text, PersonalityVariant<Float> successRates, PersonalityVariant<String> successResponses, PersonalityVariant<String> failureResponses)
    {
        this.text = text;
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
    public PersonalityVariant<String> getSuccessResponses()
    {
        return successResponses;
    }

    /**
     * Sets successResponses
     */
    public void setSuccessResponses(PersonalityVariant<String> successResponses)
    {
        this.successResponses = successResponses;
    }

    /**
     * Gets failureResponses
     * @return failureResponses
     */
    public PersonalityVariant<String> getFailureResponses()
    {
        return failureResponses;
    }

    /**
     * Sets failureResponses
     */
    public void setFailureResponses(PersonalityVariant<String> failureResponses)
    {
        this.failureResponses = failureResponses;
    }
}
