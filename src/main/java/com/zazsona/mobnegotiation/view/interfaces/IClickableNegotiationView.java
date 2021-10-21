package com.zazsona.mobnegotiation.view.interfaces;

import com.zazsona.mobnegotiation.view.NegotiationButtonClickListener;

public interface IClickableNegotiationView extends INegotiationView
{
    /**
     * Called when this view is clicked.
     */
    void click();

    /**
     * Adds a listener that gets fired when this button is clicked
     * @param listener the listener to add
     */
    void addListener(NegotiationButtonClickListener listener);

    /**
     * Removes a listener that gets fired when this button is clicked
     * @param listener the listener to remove
     * @return boolean on removed
     */
    boolean removeListener(NegotiationButtonClickListener listener);

    /**
     * Removes all listeners
     * @return the number of listeners removed
     */
    int clearListeners();
}
