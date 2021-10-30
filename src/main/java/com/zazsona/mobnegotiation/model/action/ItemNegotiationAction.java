package com.zazsona.mobnegotiation.model.action;

import com.zazsona.mobnegotiation.model.ItemOffer;
import com.zazsona.mobnegotiation.model.PluginConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;

public class ItemNegotiationAction extends Action
{
    private boolean active;
    private Random random;
    private ItemOffer offer;
    private int currentQuantity;

    public ItemNegotiationAction(Player player, Mob mob)
    {
        super(player, mob);
    }

    @Override
    public void execute()
    {
        if (active)
            throw new IllegalCallerException("This action is already active.");
        EntityType entity = mob.getType();
        List<ItemOffer> possibleOffers = PluginConfig.getItemOffers(entity);
        if (possibleOffers.size() == 0)
            throw new InvalidParameterException(String.format("There are no registered item offers for %s.", entity));

        this.random = new Random();
        this.offer = possibleOffers.get(random.nextInt(possibleOffers.size()));
        this.currentQuantity = offer.getInitialQuantity();
        this.active = true;
        runOnStartListeners();
        runOfferUpdatedListeners(OfferState.PENDING, getOfferStack());
    }

    /**
     * Accepts the offer, having the mob drop the items.
     */
    public void acceptOffer()
    {
        ItemStack offerStack = getOfferStack();
        Location location = mob.getLocation();
        World world = location.getWorld();
        world.dropItemNaturally(location, offerStack);
        runOfferUpdatedListeners(OfferState.ACCEPTED, offerStack);
        stop();
    }

    /**
     * Denies the offer. The entity may decide to make a follow-up offer, otherwise the action is stopped.
     * @return true on follow-up offer made, false otherwise.
     */
    public boolean denyOffer()
    {
        runOfferUpdatedListeners(OfferState.DENIED, getOfferStack());
        double followUpSuccessRate = PluginConfig.getItemDemandSuccessRate() / 100.0f;
        boolean followUp = random.nextDouble() < followUpSuccessRate;
        if (followUp)
        {
            this.currentQuantity += offer.getIncreaseAmount();
            runOfferUpdatedListeners(OfferState.PENDING, getOfferStack());
            return true;
        }
        else
        {
            stop();
            return false;
        }
    }

    @Override
    public void stop()
    {
        if (active)
        {
            this.active = false;
            runOnCompleteListeners();
        }
    }

    @Override
    public boolean isActive()
    {
        return active;
    }

    public ItemOffer getItemOffer()
    {
        return offer;
    }

    public int getCurrentOfferQuantity()
    {
        return currentQuantity;
    }

    public ItemStack getOfferStack()
    {
        return new ItemStack(getItemOffer().getItem(), getCurrentOfferQuantity());
    }

    /**
     * Runs all listeners for alerting when a new offer is made.
     */
    protected void runOfferUpdatedListeners(OfferState offerState, ItemStack offerStack)
    {
        for (int i = listeners.size() - 1; i > -1; i--)
        {
            IActionListener listener = listeners.get(i);
            if (listener instanceof IItemNegotiationActionListener)
            {
                ((IItemNegotiationActionListener) listener).onOfferUpdated(this, offerState, offerStack);
            }
        }
    }
}
