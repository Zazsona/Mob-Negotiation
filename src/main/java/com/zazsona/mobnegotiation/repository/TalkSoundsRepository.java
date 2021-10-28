package com.zazsona.mobnegotiation.repository;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class TalkSoundsRepository implements ITalkSoundsRepository
{
    private HashMap<EntityType, Sound> entitySoundMap;

    public TalkSoundsRepository()
    {
        entitySoundMap = new HashMap<>();
        setSound(EntityType.CREEPER, Sound.ENTITY_CREEPER_HURT);
        setSound(EntityType.SKELETON, Sound.ENTITY_SKELETON_AMBIENT);
        setSound(EntityType.ZOMBIE, Sound.ENTITY_ZOMBIE_AMBIENT);
    }

    /**
     * Returns the sound for this entity, or null if none is set.
     * @param entity the entity to get a sound for
     * @return the sound, or null if none set.
     */
    @Override
    public Sound getSound(EntityType entity)
    {
        return entitySoundMap.getOrDefault(entity, null);
    }

    /**
     * Sets the sound for an entity, overwriting any existing entry.
     * @param entity the entity to set a sound for
     * @param sound the sound to set
     */
    @Override
    public void setSound(EntityType entity, Sound sound)
    {
        entitySoundMap.put(entity, sound);
    }

    /**
     * Removes the sound for an entity
     * @param entity the entity to mute
     */
    @Override
    public void removeSound(EntityType entity)
    {
        entitySoundMap.remove(entity);
    }
}
