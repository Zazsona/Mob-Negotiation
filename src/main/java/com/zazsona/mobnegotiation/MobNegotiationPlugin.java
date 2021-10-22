package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.controller.NegotiationController;
import com.zazsona.mobnegotiation.model.NegotiationEntityEligibilityChecker;
import com.zazsona.mobnegotiation.repository.NegotiationRepository;
import com.zazsona.mobnegotiation.view.NegotiationViewInteractionExecutor;
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
        NegotiationViewInteractionExecutor responseCommand = NegotiationViewInteractionExecutor.getInstance();
        getCommand(NegotiationViewInteractionExecutor.COMMAND_KEY).setExecutor(responseCommand);
        NegotiationRepository negotiationRepository = NegotiationRepository.getInstance();
        NegotiationEntityEligibilityChecker negotiationEntityEligibilityChecker = new NegotiationEntityEligibilityChecker();
        NegotiationController negotiationController = new NegotiationController(negotiationRepository, negotiationEntityEligibilityChecker, responseCommand);
        getServer().getPluginManager().registerEvents(negotiationController, this);
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
