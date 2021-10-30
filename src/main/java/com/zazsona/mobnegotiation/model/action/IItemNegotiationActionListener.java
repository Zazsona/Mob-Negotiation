package com.zazsona.mobnegotiation.model.action;

import org.bukkit.inventory.ItemStack;

public interface IItemNegotiationActionListener extends IActionListener
{
    void onOfferUpdated(ItemNegotiationAction action, OfferState state, ItemStack offeredStack);
}
