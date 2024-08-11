package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.PluginConfig;
import com.zazsona.mobnegotiation.model.script.NegotiationScriptLoader;
import com.zazsona.mobnegotiation.repository.ICooldownRespository;
import com.zazsona.mobnegotiation.repository.INegotiationRepository;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.List;

public class NegotiationEligibilityChecker implements INegotiationEligibilityChecker
{
    private INegotiationRepository negotiationRepository;
    private ICooldownRespository cooldownRespository;

    public NegotiationEligibilityChecker(INegotiationRepository negotiationRepository, ICooldownRespository cooldownRespository)
    {
        this.negotiationRepository = negotiationRepository;
        this.cooldownRespository = cooldownRespository;
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
        if (!PluginConfig.isPluginEnabled())
            return false;
        if (negotiationRepository.hasNegotiationForPlayer(player))
            return false;
        if (cooldownRespository.isPlayerInCooldown(player))
            return false;

        int mobTargetingBlockRange = 7;
        // While not required, this below reduces wasteful O(n) searches.
        boolean playerCanNegotiate = isEntityAbleToNegotiate(player);
        boolean mobCanNegotiate = isEntityAbleToNegotiate(mob);
        if (!playerCanNegotiate || !mobCanNegotiate)
            return false;

        boolean isPlayerTargeted = isEntityTargeted(player, mobTargetingBlockRange, mob);
        if (isPlayerTargeted)
            return false;
        boolean isMobTargeted = isEntityTargeted(mob, mobTargetingBlockRange, player);
        if (isMobTargeted)
            return false;

        return true;
    }

    /**
     * Checks if an entity's current status and position is suitable for negotiation
     * @param entity the entity to check
     * @return true if entity can negotiate
     */
    private boolean isEntityAbleToNegotiate(Entity entity)
    {
        boolean isEntityOnFire = entity.isVisualFire() || entity.getFireTicks() > 0;
        boolean isEntityBeingRidden = !entity.getPassengers().isEmpty();
        if (entity.isInsideVehicle() || entity.isInWater() || isEntityOnFire || isEntityBeingRidden)
            return false;

        Location location = entity.getLocation();
        Block standingBlock = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
        Block jumpingBlock = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() - 2, location.getBlockZ());
        if (!standingBlock.getType().isSolid() || jumpingBlock.getType().isSolid())
            return false;

        boolean isEntityPlayer = entity instanceof Player;
        if (isEntityPlayer)
        {
            Player playerEntity = (Player) entity;
            if (playerEntity.isFlying() || !isPlayerInNegotiatingGameMode(playerEntity))
                return false;
        }
        else
        {
            if (!NegotiationScriptLoader.isEntityScripted(entity.getType()))
                return false;
        }

        return true;
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
