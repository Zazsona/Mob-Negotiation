package view2.world.entity;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class StaticCreeperState extends StaticCreatureState {

    private int creeperMaxFuseTicks;

    public StaticCreeperState(Plugin plugin, Creeper creeper) {
        super(plugin, creeper);
    }

    @Override
    public void render() {
        super.render();

        Entity entity = getEntity();
        if (entity instanceof Creeper)
        {
            Creeper creeper = (Creeper) entity;
            int ticksPerDay = 20 * 60 * 60 * 24; // Arbitrary high amount
            creeperMaxFuseTicks = creeper.getMaxFuseTicks();
            creeper.setMaxFuseTicks(ticksPerDay);
            creeper.setFuseTicks(0);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        Entity entity = getEntity();
        if (entity instanceof Creeper)
        {
            Creeper creeper = (Creeper) entity;
            creeper.setMaxFuseTicks(creeperMaxFuseTicks);
            creeper.setFuseTicks(0);
        }
    }
}
