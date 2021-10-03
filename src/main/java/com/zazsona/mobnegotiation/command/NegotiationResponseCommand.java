package com.zazsona.mobnegotiation.command;

import com.zazsona.mobnegotiation.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NegotiationResponseCommand implements CommandExecutor
{
    public static final String COMMAND_KEY = "__negotiation_response__";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            NegotiationSessionsHolder negotiationSessionsHolder = NegotiationSessionsHolder.getInstance();
            if (negotiationSessionsHolder.hasNegotiationForPlayer(player))
            {
                NegotiationProcess negotiationProcess = negotiationSessionsHolder.getNegotiationForPlayer(player);
                if (negotiationProcess.getNegotiationId().equals(args[0]))
                {
                    NegotiationPrompt currentPrompt = negotiationProcess.getCurrentPrompt();
                    if (currentPrompt != null && currentPrompt.getPromptId().equals(args[1]))
                    {
                        NegotiationPromptItem item = currentPrompt.getItem(args[2]);
                        if (item != null && item.getItemId().equals(args[2]))
                        {
                            MobNegotiationPlugin.getInstance().getLogger().info(String.format("%s selected \"%s\".", player.getName(), item.getText()));
                        }
                    }
                }
            }
        }
        return true;
    }
}
