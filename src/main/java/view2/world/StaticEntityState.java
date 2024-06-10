package view2.world;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;

// TODO: A class that takes an entity and renders it immobile
// This will then be wrapped into a "NegotiationEntityState" class to manage both the player & mob, with any negotiation specific customisations

public class StaticEntityState {

    private Entity entity;

    private Location entityLocation;
    private BukkitTask tickMovementTask;

    // Prior state storage
    private Location playerLocation;
    private float playerWalkSpeed;
    private boolean playerInvisibilityState;
    private boolean mobAwareState;
    private int creeperMaxFuseTicks;

    public StaticEntityState(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Renders this state onto the entity
     */
    public void render() {
        this.tickMovementTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::onNonPlayerEntityMove, 1, 1);

        if (entity instanceof Creature)
            ((Creature) entity).setTarget(null);
        if (entity instanceof Player)
        {
            Player player = (Player) entity;
            playerLocation = player.getLocation();
            playerWalkSpeed = player.getWalkSpeed();
            playerInvisibilityState = player.isInvisible();
            player.setWalkSpeed(0);
        }
        if (entity instanceof Mob)
        {
            Mob mob = (Mob) entity;
            mobAwareState = mob.isAware();
            mob.setAware(false);
        }
        if (entity instanceof Creeper)
        {
            Creeper creeper = (Creeper) entity;
            int ticksPerDay = 20 * 60 * 60 * 24; // Arbitrary high amount
            creeperMaxFuseTicks = creeper.getMaxFuseTicks();
            creeper.setMaxFuseTicks(ticksPerDay);
            creeper.setFuseTicks(0);
        }
    }

    /**
     * Removes this state from the entity
     */
    public void destroy() {
        if (tickMovementTask != null)
            tickMovementTask.cancel();

        if (entity instanceof Player)
        {
            Player player = (Player) entity;
            player.setWalkSpeed(playerWalkSpeed);
            player.setInvisible(playerInvisibilityState);
            player.teleport(playerLocation);
        }
        if (entity instanceof Mob)
        {
            Mob mob = ((Mob) entity);
            mob.setAware(mobAwareState);
        }
        if (entity instanceof Creeper)
        {
            Creeper creeper = (Creeper) entity;
            creeper.setMaxFuseTicks(creeperMaxFuseTicks);
            creeper.setFuseTicks(0);
        }
    }
}
