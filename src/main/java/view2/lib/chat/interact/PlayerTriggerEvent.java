package view2.lib.chat.interact;

import org.bukkit.entity.Player;
import view2.lib.chat.control.IChatControl;

public class PlayerTriggerEvent extends TriggerEvent {

    private Player triggeringPlayer;

    public PlayerTriggerEvent(IChatControl triggeredControl, Player triggeringPlayer) {
        super(triggeredControl);
        this.triggeringPlayer = triggeringPlayer;
    }

    /**
     * Gets the player that caused the Chat UI element to be triggered
     * @return the player that performed the trigger action
     */
    public Player getTriggeringPlayer() {
        return triggeringPlayer;
    }
}
