package com.zazsona.mobnegotiation.view2.lib.chat.interact.cursor;

import com.zazsona.mobnegotiation.view2.lib.chat.control.IChatControl;

public interface IChatClickCommandEventEmittable {

    /**
     * Gets the command to execute to "click" the provided control
     * @return the formatted command
     */
    String getFormattedCommand(IChatControl control);

    /**
     * Adds a listener that gets fired when a UI click action command is issued
     * @param listener the listener to add
     */
    void addListener(IChatClickCommandListener listener);

    /**
     * Removes a listener that gets fired when a UI click action command is issued
     * @param listener the listener to remove
     * @return boolean on removed
     */
    boolean removeListener(IChatClickCommandListener listener);

    /**
     * Removes all listeners
     * @return the # of removed listeners
     */
    int clearListeners();
}
