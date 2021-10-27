package com.zazsona.mobnegotiation.repository;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class CooldownRespository implements ICooldownRespository
{
    private final Object lock;
    private final HashMap<Player, Integer> playerCooldownMap;
    private final HashMap<Player, BukkitTask> playerCooldownTimerMap;

    public CooldownRespository()
    {
        this.lock = new Object();
        this.playerCooldownMap = new HashMap<>();
        this.playerCooldownTimerMap = new HashMap<>();
    }

    /**
     * Sets the cooldown timer for the player, overwriting any existing cooldowns.
     * @param player the player to set
     * @param ticks the ticks to wait
     */
    @Override
    public void setCooldown(Player player, int ticks)
    {
        removeCooldown(player);
        BukkitTask tickTask = Bukkit.getScheduler().runTaskTimerAsynchronously(MobNegotiationPlugin.getInstance(), () ->
        {
            synchronized (lock)
            {
                if (playerCooldownMap.containsKey(player))
                {
                    int tickCount = playerCooldownMap.get(player);
                    tickCount--;
                    if (tickCount > 0)
                        playerCooldownMap.put(player, tickCount);
                    else
                        removeCooldown(player);
                }
                else
                    removeCooldown(player);
            }
        }, 1, 1);

        synchronized (lock)
        {
            playerCooldownMap.put(player, ticks);
            playerCooldownTimerMap.put(player, tickTask);
        }
    }

    /**
     * Removes the cooldown for this player
     * @param player the player
     */
    @Override
    public void removeCooldown(Player player)
    {
        synchronized (lock)
        {
            playerCooldownMap.remove(player);
            BukkitTask timer = playerCooldownTimerMap.remove(player);
            if (timer != null)
                timer.cancel();
        }
    }

    /**
     * Gets the remaining ticks on the player's cooldown
     * @param player the player
     * @return the remaining ticks
     */
    @Override
    public int getCooldownTicks(Player player)
    {
        if (playerCooldownMap.containsKey(player))
            return playerCooldownMap.get(player);
        return 0;
    }

    /**
     * Gets if this player has a cooldown active
     * @param player the player
     * @return true if in cooldown
     */
    @Override
    public boolean isPlayerInCooldown(Player player)
    {
        return getCooldownTicks(player) != 0;
    }
}
