package com.zazsona.mobnegotiation.view2.lib.world.entity.state;

import org.bukkit.entity.Entity;

public interface IEntityState {

    boolean isRendered();

    Entity getEntity();

    void setEntity(Entity entity);

    void render();

    void destroy();
}
