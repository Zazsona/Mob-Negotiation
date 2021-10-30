package com.zazsona.mobnegotiation.model;

import org.bukkit.Material;

public class ItemOffer
{
    private Material item;
    private int initialQuantity;
    private int increaseStep;

    public ItemOffer(Material item, int initialQuantity, int increaseStep)
    {
        this.item = item;
        this.initialQuantity = initialQuantity;
        this.increaseStep = increaseStep;
    }

    /**
     * Gets item
     *
     * @return item
     */
    public Material getItem()
    {
        return item;
    }

    public void setItem(Material item)
    {
        this.item = item;
    }

    /**
     * Gets initialQuantity
     *
     * @return initialQuantity
     */
    public int getInitialQuantity()
    {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity)
    {
        this.initialQuantity = initialQuantity;
    }

    /**
     * Gets the amount to increase the quantity by every time a subsequent offer is made.
     * @return increaseStep
     */
    public int getIncreaseStep()
    {
        return increaseStep;
    }

    public void setIncreaseStep(int increaseStep)
    {
        this.increaseStep = increaseStep;
    }
}
