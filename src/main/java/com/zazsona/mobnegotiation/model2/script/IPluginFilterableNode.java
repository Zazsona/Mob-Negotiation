package com.zazsona.mobnegotiation.model2.script;

import java.util.Set;

public interface IPluginFilterableNode {

    Set<String> getPluginKeyAllowList();

    boolean isPluginInAllowList(String pluginKey);
}
