package view2.lib.chat.interact;

import org.bukkit.entity.Player;
import view2.lib.chat.control.IChatControl;

public class TextInputPlayerTriggerEvent extends PlayerTriggerEvent {

    private String text;

    public TextInputPlayerTriggerEvent(IChatControl triggeredControl, Player triggeringPlayer, String text) {
        super(triggeredControl, triggeringPlayer);
        this.text = text;
    }

    /**
     * Gets the message text sent by the Player
     * @return
     */
    public String getText() {
        return text;
    }
}
