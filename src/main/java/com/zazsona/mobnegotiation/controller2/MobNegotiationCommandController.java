package com.zazsona.mobnegotiation.controller2;

import com.zazsona.mobnegotiation.model.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MobNegotiationCommandController implements CommandExecutor {

    public static final String COMMAND_KEY = "mobnegotiation";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        PluginConfig.setPluginEnabled(!PluginConfig.isPluginEnabled());
        ChatColor chatColour = (PluginConfig.isPluginEnabled()) ? ChatColor.GREEN : ChatColor.RED;
        String state = (PluginConfig.isPluginEnabled()) ? "enabled" : "disabled";
        sender.sendMessage(chatColour + "Mob Negotiations are now " + state + ".");
        return true;
    }
}
