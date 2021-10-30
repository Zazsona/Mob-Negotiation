package com.zazsona.mobnegotiation.model;

public class MoneyOffer
{
    private double initialAmount;
    private double increaseAmount;

    public MoneyOffer(double initialAmount, double increaseAmount)
    {
        this.initialAmount = initialAmount;
        this.increaseAmount = increaseAmount;
    }

    /**
     * Gets initialOffer
     *
     * @return initialOffer
     */
    public double getInitialOfferValue()
    {
        return initialAmount;
    }

    public void setInitialOfferValue(int initialOffer)
    {
        this.initialAmount = initialOffer;
    }

    /**
     * Gets the amount to increase the offer by every time a subsequent offer is made.
     * @return amount to increase the initial offer value by.
     */
    public double getIncreaseAmount()
    {
        return increaseAmount;
    }

    public void setIncreaseAmount(int increaseAmount)
    {
        this.increaseAmount = increaseAmount;
    }
}
