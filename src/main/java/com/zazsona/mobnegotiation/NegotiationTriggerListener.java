package com.zazsona.mobnegotiation;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.*;

public class NegotiationTriggerListener implements Listener, NegotiationEventListener
{
    private HashMap<Player, NegotiationProcess> negotiations;
    private HashMap<Player, Long> playerToNegotiationCooldown;
    private Random rand;
    private TickClock tickClock;

    public NegotiationTriggerListener()
    {
        this.negotiations = new HashMap<>();
        this.playerToNegotiationCooldown = new HashMap<>();
        this.rand = new Random();
        this.tickClock = new TickClock(MobNegotiationPlugin.getInstance());
    }

    /**
     * Detects when a valid mob is hit, and if parameters are valid, begins a negotiation session.
     * @param e the event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobAttack(EntityDamageByEntityEvent e)
    {
        if (isNegotiationTriggerConditionsMet(e))
        {
            e.setDamage(0.0f);
            Player player = (Player) e.getDamager();
            Mob mob = (Mob) e.getEntity();
            NegotiationProcess negotiationProcess = new NegotiationProcess(player, mob);
            negotiationProcess.addEventListener(this);

            NegotiationEntityEventListener negotiationEntityListener = new NegotiationEntityEventListener(negotiationProcess);
            MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
            plugin.getServer().getPluginManager().registerEvents(negotiationEntityListener, plugin);

            negotiationProcess.start();
        }
    }

    /**
     * Checks to see if the parameters of the provided event meet the conditions for negotiation to occur
     * @param e an {@link EntityDamageByEntityEvent} featuring a player and entity to check if they can negotiate
     * @return true if valid parameters for negotiation
     */
    private boolean isNegotiationTriggerConditionsMet(EntityDamageByEntityEvent e)
    {
        double roll = (rand.nextDouble() * 100);
        if (PluginConfig.isPluginEnabled() && roll <= PluginConfig.getNegotiationRate())
        {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof Mob)
            {
                Player player = (Player) e.getDamager();
                Mob mob = (Mob) e.getEntity();
                if (!negotiations.containsKey(player) && !isPlayerNegotiationCooldownActive(player))
                {
                    int mobTargetingRange = 7;
                    boolean playerCanNegotiate = isEntityAbleToNegotiate(player);
                    boolean mobCanNegotiate = isEntityAbleToNegotiate(mob);
                    if (playerCanNegotiate && mobCanNegotiate) // While not required, this reduces wasteful O(n) searches.
                    {
                        boolean isPlayerTargeted = isEntityTargeted(player, mobTargetingRange, mob);
                        if (!isPlayerTargeted)  // While not required, this reduces wasteful wasteful O(n) searches.
                        {
                            boolean isMobTargeted = isEntityTargeted(mob, mobTargetingRange, player);
                            if (!isMobTargeted)
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if an entity's current status and position is suitable for negotiation
     * @param entity the entity to check
     * @return true if entity can negotiate
     */
    private boolean isEntityAbleToNegotiate(Entity entity)
    {
        if (entity instanceof Player && (((Player) entity).isFlying() || !isPlayerInNegotiatingGameMode((Player) entity)))
            return false;

        if (!entity.isInWater() && !entity.isInsideVehicle())
        {
            Location location = entity.getLocation();
            Block standingBlock = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
            Block jumpingBlock = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() - 2, location.getBlockZ());
            return (standingBlock.getType().isSolid() || jumpingBlock.getType().isSolid());
        }
        return false;
    }

    /**
     * Checks if the entity is currently being targeted
     * @param entity the entity
     * @param radius the radius to search for mobs from the entity's position
     * @param exclusions entities to ignore
     * @return true if targeted
     */
    private boolean isEntityTargeted(Entity entity, int radius, Entity... exclusions)
    {
        List<Entity> nearbyEntities = entity.getNearbyEntities(radius, radius, radius);
        for (Entity excludedEntity : exclusions)
            nearbyEntities.remove(excludedEntity);

        for (Entity nearbyEntity : nearbyEntities)
        {
            if (nearbyEntity instanceof Creature)
            {
                Creature creature = (Creature) nearbyEntity;
                if (creature.getTarget() == entity)
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if this player is in a gamemode suitable for negotiations
     * @param player the player to check
     * @return true if player not in creative or spectator mode
     */
    private boolean isPlayerInNegotiatingGameMode(Player player)
    {
        return !(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR);
    }

    /**
     * Checks to see if this player is in the negotiation cooldown
     * @param player the player to check
     * @return true if the player is still on the cooldown timer
     */
    private boolean isPlayerNegotiationCooldownActive(Player player)
    {
        if (playerToNegotiationCooldown.containsKey(player))
            return (tickClock.getTicks() - playerToNegotiationCooldown.get(player)) <= PluginConfig.getNegotiationCooldownTicks();
        return false;
    }

    /**
     * Removes entries from the cooldown map if their cooldown has expired.
     */
    private void removeExpiredNegotiationCooldowns()
    {
        playerToNegotiationCooldown.entrySet().removeIf(entry -> !isPlayerNegotiationCooldownActive(entry.getKey()));
    }

    /**
     * Maintains negotiations with active negotiations
     * @param negotiation the updated negotiation
     */
    @Override
    public void onNegotiationStateUpdate(NegotiationProcess negotiation)
    {
        NegotiationState state = negotiation.getState();
        if (state == NegotiationState.STARTED)
            negotiations.put(negotiation.getPlayer(), negotiation);
        else if (state.isTerminating)
        {
            if (!tickClock.isRunning())
            {
                MobNegotiationPlugin plugin = MobNegotiationPlugin.getInstance();
                tickClock.start();
                int ticksPerMinute = 20 * 60;
                plugin.getServer().getScheduler().runTaskTimer(plugin, this::removeExpiredNegotiationCooldowns, ticksPerMinute, ticksPerMinute);
            }
            negotiations.remove(negotiation.getPlayer());
            playerToNegotiationCooldown.put(negotiation.getPlayer(), tickClock.getTicks());
        }
    }
}
