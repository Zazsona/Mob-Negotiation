package com.zazsona.mobnegotiation.command;

import com.zazsona.mobnegotiation.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NegotiationResponseCommand implements CommandExecutor
{
    public static final String COMMAND_KEY = "__negotiation_response__";
    private List<NegotiationSelectionListener> listeners;

    public NegotiationResponseCommand()
    {
        this.listeners = new ArrayList<>();
    }

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
                            for (int i = listeners.size() - 1; i > -1; i--)
                                listeners.get(i).onNegotiationItemSelected(negotiationProcess, currentPrompt, item);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Adds a listener that gets fired when a player selects a valid prompt item.
     * @param listener the listener to add
     */
    public void addListener(NegotiationSelectionListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a listener that gets fired when a player selects a valid prompt item.
     * @param listener the listener to remove
     * @return boolean on removed
     */
    public boolean removeListener(NegotiationSelectionListener listener)
    {
        return listeners.remove(listener);
    }
}
