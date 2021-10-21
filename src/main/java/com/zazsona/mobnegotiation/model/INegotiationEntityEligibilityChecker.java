package com.zazsona.mobnegotiation.model;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

public interface INegotiationEntityEligibilityChecker
{
    boolean canEntitiesNegotiate(Player player, Mob mob);
}
