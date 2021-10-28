package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.controller.NegotiationController;
import com.zazsona.mobnegotiation.model.NegotiationEntityEligibilityChecker;
import com.zazsona.mobnegotiation.model.PluginConfig;
import com.zazsona.mobnegotiation.repository.CooldownRespository;
import com.zazsona.mobnegotiation.repository.NegotiationRepository;
import com.zazsona.mobnegotiation.repository.PersonalityNamesRepository;
import com.zazsona.mobnegotiation.repository.TalkSoundsRepository;
import com.zazsona.mobnegotiation.view.NegotiationViewInteractionExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

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
        try
        {
            super.onLoad();
            pluginName = getDescription().getName();
            PluginConfig.initialiseConfigs(this);
        }
        catch (IOException e)
        {
            getLogger().log(Level.SEVERE, "Failed to initialise configs: ", e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        NegotiationViewInteractionExecutor responseCommand = NegotiationViewInteractionExecutor.getInstance();
        getCommand(NegotiationViewInteractionExecutor.COMMAND_KEY).setExecutor(responseCommand);
        NegotiationRepository negotiationRepository = new NegotiationRepository();
        CooldownRespository cooldownRespository = new CooldownRespository();
        PersonalityNamesRepository personalityNamesRepository = new PersonalityNamesRepository();
        TalkSoundsRepository talkSoundsRepository = new TalkSoundsRepository();
        NegotiationEntityEligibilityChecker negotiationEntityEligibilityChecker = new NegotiationEntityEligibilityChecker(negotiationRepository, cooldownRespository);
        NegotiationController negotiationController = new NegotiationController(negotiationRepository, cooldownRespository, personalityNamesRepository, talkSoundsRepository, negotiationEntityEligibilityChecker, responseCommand);
        getServer().getPluginManager().registerEvents(negotiationController, this);
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
