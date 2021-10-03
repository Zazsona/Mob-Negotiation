package com.zazsona.mobnegotiation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NegotiationResponseCommandExecutor implements CommandExecutor
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
                MobNegotiationPlugin.getInstance().getLogger().info("Received response "+args[0]+" "+args[1]+" from "+player.getName());
            }
        }
        return true;
    }
}
