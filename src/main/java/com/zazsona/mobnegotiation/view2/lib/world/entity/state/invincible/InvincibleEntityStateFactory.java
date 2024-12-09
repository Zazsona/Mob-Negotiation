package com.zazsona.mobnegotiation.view2.lib.world.entity.state.invincible;

import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class InvincibleEntityStateFactory {

    public InvincibleEntityState create(Plugin plugin, Entity entity)
    {
        return new InvincibleEntityState(plugin, entity);
    }
}
