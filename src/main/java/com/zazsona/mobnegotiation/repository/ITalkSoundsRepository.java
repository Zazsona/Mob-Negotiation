package com.zazsona.mobnegotiation.repository;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public interface ITalkSoundsRepository
{
    Sound getSound(EntityType entity);

    void setSound(EntityType entity, Sound sound);

    void removeSound(EntityType entity);
}
