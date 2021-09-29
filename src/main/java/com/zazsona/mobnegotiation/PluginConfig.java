package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.plugin.Plugin;

public class PluginConfig
{
    public static final String VERSION_KEY = "version";
    public static final String PLUGIN_ENABLED_KEY = "plugin-enabled";
    public static final String NEGOTIATION_RATE_KEY = "negotiation-rate";
    public static final String NEGOTIATION_IDLE_TIMEOUT_KEY = "negotiation-idle-timeout-ticks";
    public static final String NEGOTIATION_COOLDOWN_KEY = "negotiation-cooldown-ticks";
    public static final String NEGOTIATION_DMG_GRACE_KEY = "negotiation-damage-grace-ticks";

    /**
     * Saves the config
     */
    public static void save()
    {
        MobNegotiationPlugin.getInstance().saveConfig();
    }

    /**
     * Sets the version stored in the config. Useful for maintaining compatibility with future versions
     * @param version the version to set, matching the current plugin version identity
     */
    public static void setVersion(String version)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(VERSION_KEY, version);
        save();
    }

    /**
     * Gets the version that last updated the config
     * @return the version string
     */
    public static String getConfigVersion()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getString(VERSION_KEY);
    }

    /**
     * Sets if the plugin functionality should be active
     * @param newEnabled enabled state to set
     */
    public static void setPluginEnabled(boolean newEnabled)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(PLUGIN_ENABLED_KEY, newEnabled);
        save();
    }

    /**
     * Gets if plugin functionality is enabled
     * @return boolean on enabled
     */
    public static boolean isPluginEnabled()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getBoolean(PLUGIN_ENABLED_KEY);
    }

    /**
     * Sets the rate, as a percentage, that negotiations should occur
     * @param percentage the chance of a negotiation occurring
     */
    public static void setNegotiationRate(double percentage)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(NEGOTIATION_RATE_KEY, percentage);
        save();
    }

    /**
     * Gets the rate, as a percentage, that negotiations should occur
     * @return the chance of a negotiation occurring
     */
    public static double getNegotiationRate()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getDouble(NEGOTIATION_RATE_KEY);
    }

    /**
     * Sets the number of ticks a player has to respond in before a negotiation is cancelled
     * @param ticks the ticks to allow idling for
     */
    public static void setNegotiationTimeoutTicks(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(NEGOTIATION_IDLE_TIMEOUT_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks a player has to respond in before a negotiation is cancelled
     * @return the ticks to allow idling for
     */
    public static int getNegotiationTimeoutTicks()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(NEGOTIATION_IDLE_TIMEOUT_KEY);
    }

    /**
     * Sets the number of ticks that must occur after a negotiation before the player can encounter another
     * @param ticks the ticks to wait
     */
    public static void setNegotiationCooldownTicks(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(NEGOTIATION_COOLDOWN_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks that must occur after a negotiation before the player can encounter another
     * @return the ticks to wait
     */
    public static int getNegotiationCooldownTicks()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(NEGOTIATION_COOLDOWN_KEY);
    }

    /**
     * Sets the number of ticks that a player is invincible for following a negotiation.
     * This allows them to safely understand their surroundings before mobs continue their attack.
     * @param ticks the ticks to prevent damage for
     */
    public static void setNegotiationDmgGracePeriod(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(NEGOTIATION_DMG_GRACE_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks that a player is invincible for following a negotiation.
     * This allows them to safely understand their surroundings before mobs continue their attack.
     * @return the ticks to prevent damage for
     */
    public static int getNegotiationDmgGracePeriod()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(NEGOTIATION_DMG_GRACE_KEY);
    }

}
