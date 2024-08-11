package com.zazsona.mobnegotiation.controller2;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

public interface INegotiationEligibilityChecker
{
    boolean canEntitiesNegotiate(Player player, Mob mob);
}
