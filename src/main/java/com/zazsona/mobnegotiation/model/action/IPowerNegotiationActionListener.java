package com.zazsona.mobnegotiation.model.action;

import com.zazsona.mobnegotiation.model.script.NegotiationScriptNode;

public interface IPowerNegotiationActionListener extends IActionListener
{
    void onNodeLoaded(NegotiationScriptNode node);
}
