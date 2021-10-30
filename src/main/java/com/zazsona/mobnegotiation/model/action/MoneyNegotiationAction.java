package com.zazsona.mobnegotiation.model.action;

import com.zazsona.mobnegotiation.model.MoneyOffer;
import com.zazsona.mobnegotiation.model.PluginConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.security.InvalidParameterException;
import java.util.Random;

public class MoneyNegotiationAction extends Action
{
    private Economy economy;
    private boolean active;
    private Random random;
    private MoneyOffer offer;
    private double currentAmount;

    public MoneyNegotiationAction(Player player, Mob mob, Economy economy)
    {
        super(player, mob);
        this.economy = economy;
    }

    @Override
    public void execute()
    {
        if (active)
            throw new IllegalCallerException("This action is already active.");
        EntityType entity = mob.getType();
        MoneyOffer offer = PluginConfig.getMoneyOffer(entity);
        if (offer == null)
            throw new InvalidParameterException(String.format("There is no registered money offer for %s.", entity));

        this.random = new Random();
        this.offer = offer;
        this.currentAmount = offer.getInitialOfferValue();
        this.active = true;
        runOnStartListeners();
        runOfferUpdatedListeners(OfferState.PENDING, currentAmount);
    }

    /**
     * Accepts the offer, giving the player the money.
     */
    public void acceptOffer()
    {
        economy.depositPlayer(player, currentAmount);
        runOfferUpdatedListeners(OfferState.ACCEPTED, currentAmount);
        stop();
    }

    /**
     * Denies the offer. The entity may decide to make a follow-up offer, otherwise the action is stopped.
     * @return true on follow-up offer made, false otherwise.
     */
    public boolean denyOffer()
    {
        runOfferUpdatedListeners(OfferState.DENIED, currentAmount);
        double followUpSuccessRate = PluginConfig.getMoneyDemandSuccessRate() / 100.0f;
        boolean followUp = random.nextDouble() < followUpSuccessRate;
        if (followUp)
        {
            this.currentAmount += offer.getIncreaseAmount();
            runOfferUpdatedListeners(OfferState.PENDING, currentAmount);
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

    public MoneyOffer getMoneyOffer()
    {
        return offer;
    }

    public double getCurrentOfferAmount()
    {
        return currentAmount;
    }

    /**
     * Runs all listeners for alerting when a new offer is made.
     */
    protected void runOfferUpdatedListeners(OfferState offerState, double amount)
    {
        for (int i = listeners.size() - 1; i > -1; i--)
        {
            IActionListener listener = listeners.get(i);
            if (listener instanceof IMoneyNegotiationActionListener)
            {
                ((IMoneyNegotiationActionListener) listener).onOfferUpdated(this, offerState, amount);
            }
        }
    }
}
