package com.zazsona.mobnegotiation.model.action;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

public interface IAction
{
    void execute();

    void addListener(IActionListener listener);

    boolean removeListener(IActionListener listener);

    void stop();

    boolean isActive();

    Player getPlayer();

    Mob getMob();
}
