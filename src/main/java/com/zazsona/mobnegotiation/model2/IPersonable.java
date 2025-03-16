package com.zazsona.mobnegotiation.model2;

import com.zazsona.mobnegotiation.model2.PersonalityType;

/**
 * A class representing an object with alternatives for each {@link PersonalityType}
 */
public interface IPersonable<T>
{
    /**
     * Gets the {@link T} associated with this personality type.
     * @param personalityType the personality type
     * @return the {@link T}
     */
    T getVariant(PersonalityType personalityType);

    /**
     * Sets the {@link T} associated with this personality type.
     * @param personalityType the personality type
     * @param value the value to set
     * @return the {@link T}
     */
    void setVariant(PersonalityType personalityType, T value);

    /**
     * Gets the {@link T} associated with upbeat personalities.
     * @return the {@link T}
     */
    T getUpbeat();

    /**
     * Sets the {@link T} associated with upbeat personalities.
     */
    void setUpbeat(T upbeat);

    /**
     * Gets the {@link T} associated with timid personalities.
     * @return the {@link T}
     */
    T getTimid();

    /**
     * Sets the {@link T} associated with timid personalities.
     */
    void setTimid(T timid);

    /**
     * Gets the {@link T} associated with irritable personalities.
     * @return the {@link T}
     */
    T getIrritable();

    /**
     * Sets the {@link T} associated with irritable personalities.
     */
    void setIrritable(T irritable);

    /**
     * Gets the {@link T} associated with gloomy personalities.
     * @return the {@link T}
     */
    T getGloomy();

    /**
     * Sets the {@link T} associated with gloomy personalities.
     */
    void setGloomy(T gloomy);
}
