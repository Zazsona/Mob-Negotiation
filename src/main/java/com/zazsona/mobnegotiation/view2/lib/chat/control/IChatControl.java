package com.zazsona.mobnegotiation.view2.lib.chat.control;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public interface IChatControl {

    /**
     * Gets the unique ID for this UI control instance
     * @return
     */
    UUID getUUID();

    /**
     * Creates the render of this UI control
     */
    BaseComponent render();

    /**
     * Destroys this control and all its children
     */
    void destroy();
}
