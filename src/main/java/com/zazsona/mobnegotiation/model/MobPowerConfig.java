package com.zazsona.mobnegotiation.model;

import org.bukkit.potion.PotionEffectType;

public class MobPowerConfig
{
    private PotionEffectType effectType;
    private int durationTicks;

    public MobPowerConfig(PotionEffectType effect, int durationTicks)
    {
        this.effectType = effect;
        this.durationTicks = durationTicks;
    }

    /**
     * Gets effectType
     * @return effectType
     */
    public PotionEffectType getEffectType()
    {
        return effectType;
    }

    /**
     * Gets durationTicks
     * @return durationTicks
     */
    public int getDurationTicks()
    {
        return durationTicks;
    }
}
