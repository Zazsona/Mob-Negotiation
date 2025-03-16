package com.zazsona.mobnegotiation.model2;

import com.zazsona.mobnegotiation.model2.PersonalityType;

/**
 * A class representing an object with alternatives for each {@link PersonalityType}
 */
public class Personable<T> implements IPersonable<T>
{
    T upbeat;
    T timid;
    T irritable;
    T gloomy;

    public Personable()
    {

    }

    public Personable(T upbeat, T timid, T irritable, T gloomy)
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
            default -> throw new IllegalArgumentException(String.format("Cannot get value for %s Personality Type.", personalityType));
        };
    }

    @Override
    public void setVariant(PersonalityType personalityType, T value) {
        switch (personalityType)
        {
            case WILDCARD:
                this.upbeat = value;
                this.timid = value;
                this.gloomy = value;
                this.irritable = value;
                break;
            case UPBEAT:
                this.upbeat = value;
                break;
            case TIMID:
                this.timid = value;
                break;
            case IRRITABLE:
                this.irritable = value;
                break;
            case GLOOMY:
                this.gloomy = value;
                break;
            default:
                throw new IllegalArgumentException(String.format("Cannot set value for %s Personality Type.", personalityType));
        }
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
