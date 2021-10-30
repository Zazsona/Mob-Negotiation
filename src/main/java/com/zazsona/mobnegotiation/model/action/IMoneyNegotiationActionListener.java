package com.zazsona.mobnegotiation.model.action;

import org.bukkit.inventory.ItemStack;

public interface IMoneyNegotiationActionListener extends IActionListener
{
    void onOfferUpdated(MoneyNegotiationAction action, OfferState state, double offeredAmount);
}
