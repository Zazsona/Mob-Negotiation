package view2.world.entity;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class StaticCreatureState extends StaticMobState {

    public StaticCreatureState(Plugin plugin, Creature creature) {
        super(plugin, creature);
    }

    @Override
    public void render() {
        super.render();

        Entity entity = getEntity();
        if (entity instanceof Creature)
            ((Creature) entity).setTarget(null);
    }
}
