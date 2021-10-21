package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.repository.CooldownRespository;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import com.zazsona.mobnegotiation.repository.INegotiationRepository;
import com.zazsona.mobnegotiation.repository.NegotiationRepository;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptLoader;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.*;

public class NegotiationEntityEligibilityChecker implements INegotiationEntityEligibilityChecker
{
    private INegotiationRepository negotiationRepository;
    private ICooldownRespository cooldownRespository;

    public NegotiationEntityEligibilityChecker()
    {
        this.negotiationRepository = NegotiationRepository.getInstance();
        this.cooldownRespository = CooldownRespository.getInstance();
    }

    /**
     * Checks to see if the parameters meet the conditions for negotiation to occur
     * @param player the {@link Player} receiving the negotiation
     * @param mob the {@link Mob} sending the negotiation
     * @return true if valid parameters for negotiation
     */
    @Override
    public boolean canEntitiesNegotiate(Player player, Mob mob)
    {
        if (PluginConfig.isPluginEnabled())
        {
            if (!negotiationRepository.hasNegotiationForPlayer(player) && !cooldownRespository.isPlayerInCooldown(player))
            {
                int mobTargetingRange = 7;
                boolean playerCanNegotiate = isEntityAbleToNegotiate(player);
                boolean mobCanNegotiate = isEntityAbleToNegotiate(mob);
                if (playerCanNegotiate && mobCanNegotiate) // While not required, this reduces wasteful O(n) searches.
                {
                    boolean isPlayerTargeted = isEntityTargeted(player, mobTargetingRange, mob);
                    if (!isPlayerTargeted)  // While not required, this reduces wasteful O(n) searches.
                    {
                        boolean isMobTargeted = isEntityTargeted(mob, mobTargetingRange, player);
                        if (!isMobTargeted)
                            return true;
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

        if (!(entity instanceof Player) && !NegotiationScriptLoader.isEntityScripted(entity.getType()))
            return false;

        if (!entity.isInWater() && !entity.isInsideVehicle() && !entity.isVisualFire() && entity.getFireTicks() <= 0 && entity.getPassengers().size() == 0)
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

}
