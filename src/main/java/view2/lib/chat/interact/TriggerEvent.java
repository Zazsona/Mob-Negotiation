package view2.lib.chat.interact;

import view2.lib.chat.control.IChatControl;

public class TriggerEvent {

    private IChatControl triggeredControl;
    public TriggerEvent(IChatControl triggeredControl) {
        this.triggeredControl = triggeredControl;
    }

    /**
     * Gets the Chat UI element that was triggered
     * @return the Chat UI element
     */
    public IChatControl getTriggeredControl() {
        return triggeredControl;
    }
}
