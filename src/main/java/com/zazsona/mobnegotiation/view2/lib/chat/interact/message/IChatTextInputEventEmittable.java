package com.zazsona.mobnegotiation.view2.lib.chat.interact.message;

public interface IChatTextInputEventEmittable {

    /**
     * Adds a listener that gets fired when text input is issued
     * @param listener the listener to add
     */
    void addListener(IChatTextInputListener listener);

    /**
     * Removes a listener that gets fired when text input is issued
     * @param listener the listener to remove
     * @return boolean on removed
     */
    boolean removeListener(IChatTextInputListener listener);

    /**
     * Removes all listeners
     * @return the # of removed listeners
     */
    int clearListeners();
}
