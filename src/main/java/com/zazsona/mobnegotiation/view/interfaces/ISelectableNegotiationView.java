package com.zazsona.mobnegotiation.view.interfaces;

import com.zazsona.mobnegotiation.view.NegotiationButtonSelectListener;

public interface ISelectableNegotiationView extends INegotiationView
{
    /**
     * Called when this view is selected.
     */
    void select();

    /**
     * Adds a listener that gets fired when this button is selected
     * @param listener the listener to add
     */
    void addListener(NegotiationButtonSelectListener listener);

    /**
     * Removes a listener that gets fired when this button is selected
     * @param listener the listener to remove
     * @return boolean on removed
     */
    boolean removeListener(NegotiationButtonSelectListener listener);

    /**
     * Removes all listeners
     * @return the number of listeners removed
     */
    int clearListeners();
}
