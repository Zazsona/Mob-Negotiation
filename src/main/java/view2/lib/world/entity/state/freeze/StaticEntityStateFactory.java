package view2.lib.world.entity.state.freeze;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class StaticEntityStateFactory {

    public StaticEntityState create(Plugin plugin, Entity entity)
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

    public StaticEntityState create(Plugin plugin, Entity entity, Location targetLocation)
    {
        if (entity instanceof Player)
            return new StaticPlayerState(plugin, (Player) entity, targetLocation);
        if (entity instanceof Creeper)
            return new StaticCreeperState(plugin, (Creeper) entity, targetLocation);
        if (entity instanceof Creature)
            return new StaticCreatureState(plugin, (Creature) entity, targetLocation);
        if (entity instanceof Mob)
            return new StaticMobState(plugin, (Mob) entity, targetLocation);

        return new StaticEntityState(plugin, entity, targetLocation);
    }
}
