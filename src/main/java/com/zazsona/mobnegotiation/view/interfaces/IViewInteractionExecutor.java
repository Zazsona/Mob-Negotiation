package com.zazsona.mobnegotiation.view.interfaces;

import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;

public interface IViewInteractionExecutor
{
    void addView(INegotiationView view);

    INegotiationView removeView(INegotiationView view);

    INegotiationView removeView(String id);
}
