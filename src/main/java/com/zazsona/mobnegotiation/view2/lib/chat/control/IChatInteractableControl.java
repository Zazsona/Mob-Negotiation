package com.zazsona.mobnegotiation.view2.lib.chat.control;

import org.bukkit.entity.Player;

public interface IChatInteractableControl extends IChatControl {

    /**
     * Allows the provided Player to interact with this control
     * @param player
     */
    void addInteractablePlayer(Player player);

    /**
     * Removes the provided Player from interacting with this control
     * @param player
     */
    void removeInteractablePlayer(Player player);

    /**
     * Removes all Players from interacting with this control
     */
    void clearInteractablePlayers();

    /**
     * Allows all players to interact with this control
     */
    void setUniversallyInteractable(boolean isUniversallyInteractable);

    /**
     * Gets whether this control can be interacted with by anyone, regardless of whether they received it
     */
    boolean isUniversallyInteractable();
}
