package com.zazsona.mobnegotiation.view.interfaces;

import java.util.Collection;

public interface IContainerNegotiationView extends INegotiationView
{
    /**
     * Gets the child elements of this view
     * @return the children
     */
    Collection<INegotiationView> getChildren();

    /**
     * Gets the child of this view identified by the id
     * @param id child's id
     * @return the child, or null if not found
     */
    INegotiationView getChild(String id);

    /**
     * Gets if this view has a child identified by the id
     * @param id child's id
     * @return true if child has a matching id
     */
    boolean hasChild(String id);

    /**
     * Gets if this view has the provided child
     * @param view the view to check
     * @return true if view has child
     */
    boolean hasChild(INegotiationView view);

    /**
     * Adds a child to this view.
     * @param child the child to add
     * @return true is added
     */
    boolean addChild(INegotiationView child);

    /**
     * Removes a child from this view.
     * @param child the child to remove
     * @return true if removed
     */
    boolean removeChild(INegotiationView child);
}
