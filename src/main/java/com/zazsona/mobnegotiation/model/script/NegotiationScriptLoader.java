package com.zazsona.mobnegotiation.model.script;

import com.google.gson.Gson;
import org.bukkit.entity.EntityType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NegotiationScriptLoader
{
    private static final String SCRIPT_PATH = "scripts";
    private static final String SCRIPT_FORMAT = ".json";

    /**
     * Gets if there is a script for this entity
     * @param entity the entity to check
     * @return true on script available
     */
    public static boolean isEntityScripted(EntityType entity)
    {
        String entityName = entity.toString().toLowerCase();
        String scriptPath = String.format("/%s/%s%s", SCRIPT_PATH, entityName, SCRIPT_FORMAT);
        return (NegotiationScriptLoader.class.getResource(scriptPath) != null);
    }

    /**
     * Loads and deserializes the script for the specified entity
     * @param entity the type of entity to load a script for
     * @throws IOException unable to read script
     * @throws FileNotFoundException unable to find script for entity
     * @return the script
     */
    public static NegotiationScript loadScript(EntityType entity) throws IOException, FileNotFoundException
    {
        String entityName = entity.toString().toLowerCase();
        String scriptPath = String.format("/%s/%s%s", SCRIPT_PATH, entityName, SCRIPT_FORMAT);
        if (isEntityScripted(entity))
        {
            InputStream inputStream = NegotiationScriptLoader.class.getResourceAsStream(scriptPath);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            NegotiationScript script = gson.fromJson(reader, NegotiationScript.class);
            return script;
        }
        else
            throw new FileNotFoundException(String.format("Unknown script: %s. Is the mob \"%s\" supported?", scriptPath, entity));
    }
}
