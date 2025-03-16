package com.zazsona.mobnegotiation.model2;

public enum ResponseType
{
    PARLEY(0),
    ATTACK(1),
    CANCEL(2);

    private int id;

    ResponseType(int id)
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
     * @return the {@link ResponseType} with a matching ID, or null if no match found.
     */
    public static ResponseType fromId(int id) {
        for (ResponseType responseType : ResponseType.values()) {
            if (responseType.getId() == id)
                return responseType;
        }
        return null;
    }
}
