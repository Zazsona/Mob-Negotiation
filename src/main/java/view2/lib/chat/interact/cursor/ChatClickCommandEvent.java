package view2.lib.chat.interact.cursor;

import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ChatClickCommandEvent {

    private UUID uiControlId;
    private CommandSender commandSender;

    private String[] clickArgs;

    public ChatClickCommandEvent(UUID uiControlId, CommandSender commandSender, String[] clickArgs) {
        this.uiControlId = uiControlId;
        this.commandSender = commandSender;
        this.clickArgs = clickArgs;
    }

    /**
     * Gets the ID of the intended recipient control of the command
     * @return
     */
    public UUID getControlId() {
        return uiControlId;
    }

    /**
     * Gets the entity that issued the command
     * @return
     */
    public CommandSender getCommandSender() {
        return commandSender;
    }

    /**
     * Gets the arguments for the click provided by the command
     * @return
     */
    public String[] getCommandArgs() {
        return clickArgs;
    }
}
