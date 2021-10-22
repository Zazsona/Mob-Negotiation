package com.zazsona.mobnegotiation.model.entitystate;

import org.bukkit.entity.Entity;

public interface EntityInvalidatedEventListener
{
    void onEntityInvalidated(Entity entity);
}
