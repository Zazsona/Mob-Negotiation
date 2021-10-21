package com.zazsona.mobnegotiation.view.interfaces;

import net.md_5.bungee.api.chat.BaseComponent;

public interface INegotiationView
{
    /**
     * Returns the parent of this view
     * @return the parent
     */
    INegotiationView getParent();

    /**
     * Creates a representation of this view as an array of BaseComponents for presentation in Minecraft
     * @return a {@link BaseComponent} array
     */
    BaseComponent[] getFormattedComponent();

    /**
     * Returns the unique identifier for this view
     * @return the id
     */
    String getId();
}
