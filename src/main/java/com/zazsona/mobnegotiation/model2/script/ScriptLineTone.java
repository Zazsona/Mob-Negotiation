package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.PersonalityType;

public enum ScriptLineTone
{
    NEUTRAL(0),
    AGGRESSIVE(1),
    POSITIVE(2);

    private int id;

    ScriptLineTone(int id)
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
     * @return the {@link ScriptLineTone} with a matching ID, or null if no match found.
     */
    public static ScriptLineTone fromId(int id) {
        for (ScriptLineTone scriptLineTone : ScriptLineTone.values()) {
            if (scriptLineTone.getId() == id)
                return scriptLineTone;
        }
        return null;
    }
}
