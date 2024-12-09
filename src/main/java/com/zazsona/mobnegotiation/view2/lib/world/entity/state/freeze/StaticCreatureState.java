package com.zazsona.mobnegotiation.view2.lib.world.entity.state.freeze;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class StaticCreatureState extends StaticMobState {

    public StaticCreatureState(Plugin plugin, Creature creature) {
        super(plugin, creature);
    }

    public StaticCreatureState(Plugin plugin, Creature creature, Location targetLocation) {
        super(plugin, creature, targetLocation);
    }

    @Override
    public void render() {
        super.render();

        Entity entity = getEntity();
        if (entity instanceof Creature)
            ((Creature) entity).setTarget(null);
    }
}
