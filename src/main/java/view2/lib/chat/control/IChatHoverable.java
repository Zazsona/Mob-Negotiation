package view2.lib.chat.control;

import net.md_5.bungee.api.chat.HoverEvent;

public interface IChatHoverable {

    /**
     * The action to perform when this control (and child controls) are hovered over
     * @return
     */
    HoverEvent getHoverEvent();

    /**
     * The action to perform when this control (and child controls) are hovered over
     * @param hoverEvent
     */
    void setHoverEvent(HoverEvent hoverEvent);
}
