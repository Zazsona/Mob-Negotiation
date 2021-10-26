package com.zazsona.mobnegotiation.model;

import com.zazsona.mobnegotiation.MobNegotiationPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PluginConfig
{
    public static final String CFG_VERSION_KEY = "version";
    public static final String CFG_PLUGIN_ENABLED_KEY = "plugin-enabled";
    public static final String CFG_NEGOTIATION_RATE_KEY = "negotiation-rate";
    public static final String CFG_POWER_DURATION_KEY = "power-effect-duration-ticks";
    public static final String CFG_NEGOTIATION_IDLE_TIMEOUT_KEY = "negotiation-idle-timeout-ticks";
    public static final String CFG_NEGOTIATION_COOLDOWN_KEY = "negotiation-cooldown-ticks";
    public static final String CFG_NEGOTIATION_DMG_GRACE_KEY = "negotiation-damage-grace-ticks";
    public static final String CFG_MOB_CHAT_TAG_KEY = "mob-chat-tag";
    public static final String CFG_ALERT_MSGS_KEY = "alert-messages";

    private static final String PWR_FILE = "powers.yml";
    public static final String PWR_VERSION_KEY = "version";
    private static final HashMap<EntityType, PotionEffectType> entityPowersMap = new HashMap<>();

    /**
     * Crates and loads configs as required.
     * @param plugin the context
     */
    public static void initialiseConfigs(Plugin plugin) throws IOException
    {
        plugin.getConfig().options().copyDefaults(true);
        plugin.getConfig().options().copyHeader(true);
        plugin.saveConfig();

        File powerFileDir = new File(plugin.getDataFolder().getAbsolutePath()+"/"+PWR_FILE);
        updateConfigFile(PWR_FILE, PWR_VERSION_KEY, powerFileDir, PWR_VERSION_KEY);
        loadEntityPowers(plugin.getLogger(), powerFileDir, PWR_VERSION_KEY);
    }

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
        plugin.getConfig().set(CFG_VERSION_KEY, version);
        save();
    }

    /**
     * Gets the version that last updated the config
     * @return the version string
     */
    public static String getConfigVersion()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getString(CFG_VERSION_KEY);
    }

    /**
     * Sets if the plugin functionality should be active
     * @param newEnabled enabled state to set
     */
    public static void setPluginEnabled(boolean newEnabled)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_PLUGIN_ENABLED_KEY, newEnabled);
        save();
    }

    /**
     * Gets if plugin functionality is enabled
     * @return boolean on enabled
     */
    public static boolean isPluginEnabled()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getBoolean(CFG_PLUGIN_ENABLED_KEY);
    }

    /**
     * Sets the rate, as a percentage, that negotiations should occur
     * @param percentage the chance of a negotiation occurring
     */
    public static void setNegotiationRate(double percentage)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_NEGOTIATION_RATE_KEY, percentage);
        save();
    }

    /**
     * Gets the rate, as a percentage, that negotiations should occur
     * @return the chance of a negotiation occurring
     */
    public static double getNegotiationRate() // TODO: Remember not to leave this at 100%.
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getDouble(CFG_NEGOTIATION_RATE_KEY);
    }

    /**
     * Sets the number of ticks a PotionEffect bestowed as a "power" by a mob lasts.
     * @param ticks the ticks to last
     */
    public static void setPowerDurationTicks(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_POWER_DURATION_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks a PotionEffect bestowed as a "power" by a mob lasts.
     * @return the ticks to last
     */
    public static int getPowerDurationTicks()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(CFG_POWER_DURATION_KEY);
    }

    /**
     * Sets the number of ticks a player has to respond in before a negotiation is cancelled
     * @param ticks the ticks to allow idling for
     */
    public static void setNegotiationTimeoutTicks(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_NEGOTIATION_IDLE_TIMEOUT_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks a player has to respond in before a negotiation is cancelled
     * @return the ticks to allow idling for
     */
    public static int getNegotiationTimeoutTicks()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(CFG_NEGOTIATION_IDLE_TIMEOUT_KEY);
    }

    /**
     * Sets the number of ticks that must occur after a negotiation before the player can encounter another
     * @param ticks the ticks to wait
     */
    public static void setNegotiationCooldownTicks(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_NEGOTIATION_COOLDOWN_KEY, ticks);
        save();
    }

    /**
     * Gets the number of ticks that must occur after a negotiation before the player can encounter another
     * @return the ticks to wait
     */
    public static int getNegotiationCooldownTicks()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getInt(CFG_NEGOTIATION_COOLDOWN_KEY);
    }

    /**
     * Sets the number of ticks that a player is invincible for following a negotiation.
     * This allows them to safely understand their surroundings before mobs continue their attack.
     * @param ticks the ticks to prevent damage for
     */
    public static void setNegotiationDmgGracePeriod(int ticks)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_NEGOTIATION_DMG_GRACE_KEY, ticks);
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
        return plugin.getConfig().getInt(CFG_NEGOTIATION_DMG_GRACE_KEY);
    }

    /**
     * Sets how the mob's name and personality are displayed
     * @param chatTag the display style
     */
    public static void setMobChatTag(String chatTag)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_MOB_CHAT_TAG_KEY, chatTag);
        save();
    }

    /**
     * Gets how the mob's name and personality are displayed
     * @return the display style, with %PERSONALITY% and %NAME% as placeholders.
     */
    public static String getMobChatTag()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getString(CFG_MOB_CHAT_TAG_KEY);
    }

    /**
     * Sets the messages displayed on the player's screen when a negotiation begins
     * @param messages the messages to display
     */
    public static void setNegotiationAlertMessages(List<String> messages)
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        plugin.getConfig().set(CFG_ALERT_MSGS_KEY, messages);
        save();
    }

    /**
     * Gets the messages displayed on the player's screen when a negotiation begins
     * @return the messages to display
     */
    public static List<String> getNegotiationAlertMessages()
    {
        Plugin plugin = MobNegotiationPlugin.getInstance();
        return plugin.getConfig().getStringList(CFG_ALERT_MSGS_KEY);
    }

    /**
     * Gets the power type offered by this entity
     * @param entity the entity to get the paired power for
     * @return the power as a potion effect type, or null if none is assigned.
     */
    public static PotionEffectType getOfferedPower(EntityType entity)
    {
        return entityPowersMap.getOrDefault(entity, null);
    }

    /**
     * Creates or updates a config file, preserving existing settings.
     * @param internalDir the location of the config file in resources
     * @param internalVersionKey the YAML key for identifying the current internal file version
     * @param targetDir the location to create the file
     * @param targetVersionKey the YAML key for identifying the target file version, ignored if file does not exist
     * @throws IOException error creating file or accessing source file
     */
    private static void updateConfigFile(String internalDir, String internalVersionKey, File targetDir, String targetVersionKey) throws IOException
    {
        boolean writeRequired = false;
        InputStream inputStream = PluginConfig.class.getClassLoader().getResourceAsStream(internalDir);
        InputStreamReader reader = new InputStreamReader(inputStream);
        YamlConfiguration powersConfig = YamlConfiguration.loadConfiguration(reader);
        String internalVersion = powersConfig.getString(internalVersionKey);

        if (targetDir.exists())
        {
            YamlConfiguration existingConfig = YamlConfiguration.loadConfiguration(targetDir);
            String existingVersion = existingConfig.getString(targetVersionKey);
            if (existingVersion == null || internalVersion.compareTo(existingVersion) > 0)
            {
                writeRequired = true;
                for (String key : existingConfig.getKeys(true))
                {
                    if (!existingConfig.isConfigurationSection(key) && !key.equals(targetVersionKey))
                    {
                        Object value = existingConfig.get(key); // Maintain existing entries as to not remove user settings
                        powersConfig.set(key, value);
                    }
                }
            }
        }
        else
            writeRequired = true;

        if (writeRequired)
            powersConfig.save(targetDir);
    }

    /**
     * Clears any existing entries and loads the entity effect pairings offered during power negotiation
     * @param logger logger for error output
     * @param powersDir the powers file
     */
    private static void loadEntityPowers(Logger logger, File powersDir, String versionKey)
    {
        if (entityPowersMap.size() > 0)
            entityPowersMap.clear();

        YamlConfiguration powersConfig = YamlConfiguration.loadConfiguration(powersDir);
        for (String key : powersConfig.getKeys(false))
        {
            try
            {
                if (!key.equals(versionKey))
                {
                    String mobName = key.toUpperCase();
                    String effectName = powersConfig.getString(key);
                    EntityType entityType = EntityType.valueOf(mobName);
                    PotionEffectType effectType = PotionEffectType.getByName(effectName); // Doesn't throw IAException.
                    if (effectType != null)                                               // So we'll make our own!
                        entityPowersMap.put(entityType, effectType);
                    else
                        throw new IllegalArgumentException();
                }
            }
            catch (IllegalArgumentException e)
            {
                logger.warning(String.format("Invalid power entry: %s. Are the mob and effect names valid?", key));
            }
        }
    }
}
