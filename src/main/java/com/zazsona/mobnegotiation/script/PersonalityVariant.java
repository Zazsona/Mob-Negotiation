package com.zazsona.mobnegotiation.script;

import com.zazsona.mobnegotiation.PersonalityType;

/**
 * A class representing an object with alternatives for each {@link PersonalityType}
 */
public class PersonalityVariant<T>
{
    private T upbeat;
    private T timid;
    private T irritable;
    private T gloomy;

    public PersonalityVariant(T upbeat, T timid, T irritable, T gloomy)
    {
        this.upbeat = upbeat;
        this.timid = timid;
        this.irritable = irritable;
        this.gloomy = gloomy;
    }

    /**
     * Gets the {@link T} associated with this personality type.
     * @param personalityType the personality type
     * @return the {@link T}
     */
    public T getVariant(PersonalityType personalityType)
    {
        return switch (personalityType)
        {
            case UPBEAT -> upbeat;
            case TIMID -> timid;
            case IRRITABLE -> irritable;
            case GLOOMY -> gloomy;

        };
    }

    /**
     * Gets the {@link T} associated with upbeat personalities.
     * @return the {@link T}
     */
    public T getUpbeat()
    {
        return upbeat;
    }

    /**
     * Sets the {@link T} associated with upbeat personalities.
     */
    public void setUpbeat(T upbeat)
    {
        this.upbeat = upbeat;
    }

    /**
     * Gets the {@link T} associated with timid personalities.
     * @return the {@link T}
     */
    public T getTimid()
    {
        return timid;
    }

    /**
     * Sets the {@link T} associated with timid personalities.
     */
    public void setTimid(T timid)
    {
        this.timid = timid;
    }

    /**
     * Gets the {@link T} associated with irritable personalities.
     * @return the {@link T}
     */
    public T getIrritable()
    {
        return irritable;
    }

    /**
     * Sets the {@link T} associated with irritable personalities.
     */
    public void setIrritable(T irritable)
    {
        this.irritable = irritable;
    }

    /**
     * Gets the {@link T} associated with gloomy personalities.
     * @return the {@link T}
     */
    public T getGloomy()
    {
        return gloomy;
    }

    /**
     * Sets the {@link T} associated with gloomy personalities.
     */
    public void setGloomy(T gloomy)
    {
        this.gloomy = gloomy;
    }
}
