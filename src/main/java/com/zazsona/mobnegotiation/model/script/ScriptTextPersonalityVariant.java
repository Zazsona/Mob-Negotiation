package com.zazsona.mobnegotiation.model.script;

import com.zazsona.mobnegotiation.model.PersonalityType;
import com.zazsona.mobnegotiation.model.TextType;

/**
 * A class representing an object with alternatives for each {@link PersonalityType}
 */
public class ScriptTextPersonalityVariant extends PersonalityVariant<String>
{
    private TextType upbeatTextType = TextType.SPEECH;
    private TextType timidTextType = TextType.SPEECH;
    private TextType irritableTextType = TextType.SPEECH;
    private TextType gloomyTextType = TextType.SPEECH;

    public ScriptTextPersonalityVariant()
    {

    }

    public ScriptTextPersonalityVariant(String upbeat, String timid, String irritable, String gloomy)
    {
        super(upbeat, timid, irritable, gloomy);

        this.upbeatTextType = TextType.SPEECH;
        this.timidTextType = TextType.SPEECH;
        this.irritableTextType = TextType.SPEECH;
        this.gloomyTextType = TextType.SPEECH;
    }

    public ScriptTextPersonalityVariant(String upbeat, String timid, String irritable, String gloomy, TextType upbeatTextType, TextType timidTextType, TextType irritableTextType, TextType gloomyTextType)
    {
        super(upbeat, timid, irritable, gloomy);

        this.upbeatTextType = upbeatTextType;
        this.timidTextType = timidTextType;
        this.irritableTextType = irritableTextType;
        this.gloomyTextType = gloomyTextType;
    }

    /**
     * Gets the {@link TextType} associated with this personality type.
     * @param personalityType the personality type
     * @return the {@link TextType}
     */
    public TextType getVariantType(PersonalityType personalityType)
    {
        return switch (personalityType)
        {
            case UPBEAT -> upbeatTextType;
            case TIMID -> timidTextType;
            case IRRITABLE -> irritableTextType;
            case GLOOMY -> gloomyTextType;

        };
    }

    /**
     * Gets the {@link TextType} associated with upbeaString personalities.
     * @return the {@link TextType}
     */
    public TextType getUpbeatType()
    {
        return upbeatTextType;
    }

    /**
     * Sets the {@link TextType} associated with upbeaString personalities.
     */
    public void setUpbeatType(TextType upbeatType)
    {
        this.upbeatTextType = upbeatType;
    }

    /**
     * Gets the {@link TextType} associated with timid personalities.
     * @return the {@link TextType}
     */
    public TextType getTimidType()
    {
        return timidTextType;
    }

    /**
     * Sets the {@link TextType} associated with timid personalities.
     */
    public void setTimidType(TextType timidType)
    {
        this.timidTextType = timidType;
    }

    /**
     * Gets the {@link TextType} associated with irritable personalities.
     * @return the {@link TextType}
     */
    public TextType getIrritableType()
    {
        return irritableTextType;
    }

    /**
     * Sets the {@link TextType} associated with irritable personalities.
     */
    public void setIrritableType(TextType irritableType)
    {
        this.irritableTextType = irritableType;
    }

    /**
     * Gets the {@link TextType} associated with gloomy personalities.
     * @return the {@link TextType}
     */
    public TextType getGloomyType()
    {
        return gloomyTextType;
    }

    /**
     * Sets the {@link TextType} associated with gloomy personalities.
     */
    public void setGloomyType(TextType gloomyType)
    {
        this.gloomyTextType = gloomyType;
    }
}
