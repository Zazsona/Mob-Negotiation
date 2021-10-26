package com.zazsona.mobnegotiation.repository;

import com.zazsona.mobnegotiation.model.PersonalityType;

import java.util.List;

public interface IPersonalityNamesRepository
{
    List<String> getNames(PersonalityType personalityType);

    void addName(PersonalityType personalityType, String name);

    void removeName(PersonalityType personalityType, String name);
}
