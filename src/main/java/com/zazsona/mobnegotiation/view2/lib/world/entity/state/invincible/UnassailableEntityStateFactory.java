package com.zazsona.mobnegotiation.view2.lib.world.entity.state.invincible;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class UnassailableEntityStateFactory {

    public UnassailableEntityState create(Plugin plugin, Entity entity)
    {
        return new UnassailableEntityState(plugin, entity);
    }
}
