package com.zazsona.mobnegotiation;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Permissions
{
    public static String NEGOTIATION_POWER = "mobnegotiation.power";
    public static String NEGOTIATION_MONEY = "mobnegotiation.money";
    public static String NEGOTIATION_ITEMS = "mobnegotiation.items";
    public static String NEGOTIATION_ATTACK = "mobnegotiation.attack";

    /**
     * Returns if this player has at least one permission enabling them to perform a negotiation action
     * @param player the player to check
     * @return true if they have at least one negotiation permission
     */
    public static boolean hasNegotiationPermission(Player player)
    {
        return (player.hasPermission(NEGOTIATION_POWER) || player.hasPermission(NEGOTIATION_MONEY) || player.hasPermission(NEGOTIATION_ITEMS) || player.hasPermission(NEGOTIATION_ATTACK));
    }
}
