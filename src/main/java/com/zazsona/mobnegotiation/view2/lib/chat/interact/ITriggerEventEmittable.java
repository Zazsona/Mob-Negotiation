package com.zazsona.mobnegotiation.view2.lib.chat.interact;

public interface ITriggerEventEmittable {

    /**
     * Adds a listener that gets fired when this button is selected
     * @param listener the listener to add
     */
    void addListener(ITriggerListener listener);

    /**
     * Removes a listener that gets fired when this button is selected
     * @param listener the listener to remove
     * @return boolean on removed
     */
    boolean removeListener(ITriggerListener listener);

    /**
     * Removes all listeners
     * @return the # of removed listeners
     */
    int clearListeners();
}
