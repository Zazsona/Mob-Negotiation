package com.zazsona.mobnegotiation.repository;

import com.zazsona.mobnegotiation.model.PersonalityType;

import java.util.*;

public class PersonalityNamesRepository implements IPersonalityNamesRepository
{
    private HashMap<PersonalityType, ArrayList<String>> personalityNamesMap;

    public PersonalityNamesRepository()
    {
        ArrayList<String> upbeatNames = new ArrayList<>();
        upbeatNames.add("Upbeat");
        upbeatNames.add("Blithesome");
        upbeatNames.add("Hopeful");
        upbeatNames.add("Cheery");
        upbeatNames.add("Sanguine");
        upbeatNames.add("Jolly");
        upbeatNames.add("Excitable");
        upbeatNames.add("Snickering");
        ArrayList<String> timidNames = new ArrayList<>();
        timidNames.add("Timid");
        timidNames.add("Diffident");
        timidNames.add("Cowering");
        timidNames.add("Trembling");
        timidNames.add("Nervous");
        timidNames.add("Quivering");
        ArrayList<String> irritableNames = new ArrayList<>();
        irritableNames.add("Irritable");
        irritableNames.add("Irascible");
        irritableNames.add("Grouchy");
        irritableNames.add("Moody");
        irritableNames.add("Fractious");
        irritableNames.add("Irked ");
        ArrayList<String> gloomyNames = new ArrayList<>();
        gloomyNames.add("Gloomy");
        gloomyNames.add("Downcast");
        gloomyNames.add("Forlorn");
        gloomyNames.add("Glum");
        gloomyNames.add("Sad");
        gloomyNames.add("Solemn");

        personalityNamesMap = new HashMap<>();
        personalityNamesMap.put(PersonalityType.UPBEAT, upbeatNames);
        personalityNamesMap.put(PersonalityType.TIMID, timidNames);
        personalityNamesMap.put(PersonalityType.IRRITABLE, irritableNames);
        personalityNamesMap.put(PersonalityType.GLOOMY, gloomyNames);
    }

    @Override
    public List<String> getNames(PersonalityType personalityType)
    {
        return Collections.unmodifiableList(personalityNamesMap.get(personalityType));
    }

    @Override
    public void addName(PersonalityType personalityType, String name)
    {
        personalityNamesMap.get(personalityType).add(name);
    }

    @Override
    public void removeName(PersonalityType personalityType, String name)
    {
        personalityNamesMap.get(personalityType).remove(name);
    }
}
