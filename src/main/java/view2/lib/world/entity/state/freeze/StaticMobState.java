package view2.lib.world.entity.state.freeze;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

public class StaticMobState extends StaticEntityState {

    protected boolean mobAwareState;

    public StaticMobState(Plugin plugin, Mob mob) {
        super(plugin, mob);
    }

    @Override
    public void render() {
        super.render();

        Entity entity = getEntity();
        if (entity instanceof Mob)
        {
            Mob mob = (Mob) entity;
            mobAwareState = mob.isAware();
            mob.setAware(false);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        Entity entity = getEntity();
        if (entity instanceof Mob)
        {
            Mob mob = ((Mob) entity);
            mob.setAware(mobAwareState);
        }
    }
}
