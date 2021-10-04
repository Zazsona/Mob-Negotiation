package com.zazsona.mobnegotiation.dialogue;

public class NegotiationDialogueOption
{
    private String text = "No text.";
    private float upbeatSuccess = 100.0f;
    private float timidSuccess = 100.0f;
    private float irritableSuccess = 100.0f;
    private float gloomySuccess = 100.0f;

    /**
     * Creates a new object with default text and a 100% success rate.
     * This constructor is mostly available for Json Deserialisation
     */
    public NegotiationDialogueOption()
    {

    }

    /**
     * Creates a new object with the provided text and a 100% success rate.
     * @param text the text to display
     */
    public NegotiationDialogueOption(String text)
    {
        this.text = text;
    }

    /**
     * Creates a new object with the provided text and personality based success rates.
     * @param text the text to display
     * @param upbeatSuccess the chance to advance on the tree for upbeat personalities (%)
     * @param timidSuccess the chance to advance on the tree for timid personalities (%)
     * @param irritableSuccess the chance to advance on the tree for irritable personalities (%)
     * @param gloomySuccess the chance to advance on the tree for gloomy personalities (%)
     */
    public NegotiationDialogueOption(String text, float upbeatSuccess, float timidSuccess, float irritableSuccess, float gloomySuccess)
    {
        this.text = text;
        this.upbeatSuccess = upbeatSuccess;
        this.timidSuccess = timidSuccess;
        this.irritableSuccess = irritableSuccess;
        this.gloomySuccess = gloomySuccess;
    }
}
