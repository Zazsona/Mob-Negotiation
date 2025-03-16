package com.zazsona.mobnegotiation.model2.script;

public enum ScriptLineType
{
    DIALOGUE(0),
    ACTION(1);

    private int id;

    ScriptLineType(int id)
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
     * @return the {@link ScriptLineType} with a matching ID, or null if no match found.
     */
    public static ScriptLineType fromId(int id) {
        for (ScriptLineType scriptLineType : ScriptLineType.values()) {
            if (scriptLineType.getId() == id)
                return scriptLineType;
        }
        return null;
    }
}
