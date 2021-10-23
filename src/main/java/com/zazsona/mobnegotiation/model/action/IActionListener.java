package com.zazsona.mobnegotiation.model.action;

public interface IActionListener
{
    void onActionStart(IAction action);

    void onActionComplete(IAction action);
}
