package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.command.NegotiationResponseCommand;
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
        pluginName = getDescription().getName();
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        NegotiationResponseCommand responseCommand = new NegotiationResponseCommand();
        getCommand(NegotiationResponseCommand.COMMAND_KEY).setExecutor(responseCommand);
        NegotiationTriggerListener negotiationTriggerListener = new NegotiationTriggerListener(responseCommand);
        getServer().getPluginManager().registerEvents(negotiationTriggerListener, this);
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
