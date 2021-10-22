package com.zazsona.mobnegotiation.repository;

import org.bukkit.entity.Player;

public interface ICooldownRespository
{
    void setCooldown(Player player, int ticks);

    void removeCooldown(Player player);

    int getCooldownTicks(Player player);

    boolean isPlayerInCooldown(Player player);
}
