package com.zazsona.mobnegotiation;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobNegotiationPlugin extends JavaPlugin
{
    private static String pluginName;
    public static MobNegotiationPlugin getInstance()
    {
        if (pluginName != null)
            return (MobNegotiationPlugin) Bukkit.getPluginManager().getPlugin(pluginName);
        else
            throw new NullPointerException("The plugin has not yet initialised.");
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        this.pluginName = getDescription().getName();
        getLogger().info(String.format("Successfully loaded %s.", pluginName));
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        getLogger().info(String.format("Successfully disabled %s.", pluginName));
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        getLogger().info(String.format("Successfully enabled %s.", pluginName));
    }
}
