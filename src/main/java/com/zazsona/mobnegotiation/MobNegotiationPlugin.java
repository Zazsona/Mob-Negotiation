package com.zazsona.mobnegotiation;

import com.zazsona.mobnegotiation.controller.NegotiationController;
import com.zazsona.mobnegotiation.model.NegotiationEntityEligibilityChecker;
import com.zazsona.mobnegotiation.model.PluginConfig;
import com.zazsona.mobnegotiation.repository.CooldownRespository;
import com.zazsona.mobnegotiation.repository.NegotiationRepository;
import com.zazsona.mobnegotiation.repository.PersonalityNamesRepository;
import com.zazsona.mobnegotiation.repository.TalkSoundsRepository;
import com.zazsona.mobnegotiation.view.MobNegotiationCommand;
import com.zazsona.mobnegotiation.view.NegotiationViewInteractionExecutor;
import com.zazsona.mobnegotiation.view.TextButtonSelectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.nio.file.Path;
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
        String dbPath = Path.of(this.getDataFolder().getAbsolutePath(), "script.db").toString();
        String dbUrl = String.format("jdbc:sqlite:%s", dbPath);
        Flyway flyway = Flyway.configure().dataSource(dbUrl, null, null).load();
        flyway.migrate();


        NegotiationViewInteractionExecutor responseCommand = NegotiationViewInteractionExecutor.getInstance();
        getCommand(NegotiationViewInteractionExecutor.COMMAND_KEY).setExecutor(responseCommand);
        MobNegotiationCommand mobNegotiationCommand = new MobNegotiationCommand();
        getCommand(MobNegotiationCommand.COMMAND_KEY).setExecutor(mobNegotiationCommand);
        NegotiationRepository negotiationRepository = new NegotiationRepository();
        CooldownRespository cooldownRespository = new CooldownRespository();
        PersonalityNamesRepository personalityNamesRepository = new PersonalityNamesRepository();
        TalkSoundsRepository talkSoundsRepository = new TalkSoundsRepository();
        NegotiationEntityEligibilityChecker negotiationEntityEligibilityChecker = new NegotiationEntityEligibilityChecker(negotiationRepository, cooldownRespository);
        NegotiationController negotiationController = new NegotiationController(negotiationRepository, cooldownRespository, personalityNamesRepository, talkSoundsRepository, negotiationEntityEligibilityChecker, responseCommand);
        TextButtonSelectionListener textButtonSelectionListener = new TextButtonSelectionListener(negotiationController);
        getServer().getPluginManager().registerEvents(negotiationController, this);
        getServer().getPluginManager().registerEvents(textButtonSelectionListener, this);
    }


    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}
