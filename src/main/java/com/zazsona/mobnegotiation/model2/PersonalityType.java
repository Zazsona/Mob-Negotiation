package com.zazsona.mobnegotiation.model2;

public enum PersonalityType
{
    WILDCARD(-1),
    NONE(0),
    UPBEAT(1),
    TIMID(2),
    IRRITABLE(3),
    GLOOMY(4);

    private int id;

    PersonalityType(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    /**
     * Gets the corresponding enum entry by id
     * @param id the ID to match
     * @return the {@link PersonalityType} with a matching ID, or null if no match found.
     */
    public static PersonalityType fromId(int id) {
        for (PersonalityType personalityType : PersonalityType.values()) {
            if (personalityType.getId() == id)
                return personalityType;
        }
        return null;
    }
}
