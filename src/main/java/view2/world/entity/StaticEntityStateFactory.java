package view2.world.entity;

import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class StaticEntityStateFactory {

    public static StaticEntityState create(Plugin plugin, Entity entity)
    {
        if (entity instanceof Player)
            return new StaticPlayerState(plugin, (Player) entity);
        if (entity instanceof Creeper)
            return new StaticCreeperState(plugin, (Creeper) entity);
        if (entity instanceof Creature)
            return new StaticCreatureState(plugin, (Creature) entity);
        if (entity instanceof Mob)
            return new StaticMobState(plugin, (Mob) entity);

        return new StaticEntityState(plugin, entity);
    }
}
