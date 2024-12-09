package com.zazsona.mobnegotiation.view2.lib.chat.interact.cursor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.zazsona.mobnegotiation.view2.lib.chat.control.IChatControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ChatClickCommandEventEmitter implements IChatClickCommandEventEmittable, CommandExecutor {

    private final ArrayList<IChatClickCommandListener> clickCommandListeners;

    private String commandKey;

    public ChatClickCommandEventEmitter(String commandKey) {
        this.clickCommandListeners = new ArrayList<>();
        this.commandKey = commandKey;
    }

    @Override
    public String getFormattedCommand(IChatControl control) {
        return String.format("/%s %s", commandKey, control.getUUID().toString());
    }

    @Override
    public void addListener(IChatClickCommandListener listener) {
        this.clickCommandListeners.add(listener);
    }

    @Override
    public boolean removeListener(IChatClickCommandListener listener) {
        return this.clickCommandListeners.remove(listener);
    }

    @Override
    public int clearListeners() {
        int listenerCount = clickCommandListeners.size();
        clickCommandListeners.clear();
        return listenerCount;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandKey, String[] commandArgs) {
        // Even in cases where command misuse has occurred, we will still return true ("command success")
        // This is because false will print out the command usage instructions to the player, but as this command is
        // run programmatically in the background on their behalf, any misuse is not on the player's part.

        if (commandArgs.length == 0) {
            Bukkit.getServer().getLogger().warning(String.format("Invalid usage of %s: No Control ID was provided.", commandKey));
            return true;
        }

        UUID controlId;
        try {
            controlId = UUID.fromString(commandArgs[0]);
        }
        catch (IllegalArgumentException e) {
            Bukkit.getServer().getLogger().warning(String.format("Invalid usage of %s: Invalid Control ID was provided.", commandKey));
            return true;
        }

        String[] eventArgs = Arrays.copyOfRange(commandArgs, 1, commandArgs.length); // Exclude first element as that's the control ID
        ChatClickCommandEvent clickCommandEvent = new ChatClickCommandEvent(controlId, commandSender, eventArgs);

        for (IChatClickCommandListener listener : clickCommandListeners) {
            listener.onClickCommand(clickCommandEvent);
        }

        return true;
    }
}
