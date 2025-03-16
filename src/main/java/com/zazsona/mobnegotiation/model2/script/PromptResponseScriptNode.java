package com.zazsona.mobnegotiation.model2.script;

import com.zazsona.mobnegotiation.model2.ResponseType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.Set;

public class PromptResponseScriptNode extends ScriptNode implements IPermissionFilterableNode, IPluginFilterableNode
{
    private ResponseType responseType;
    private Set<String> pluginKeyAllowList;
    private Set<String> permissionKeyAllowList;

    public PromptResponseScriptNode()
    {
        this(null, ResponseType.PARLEY);
    }

    public PromptResponseScriptNode(ScriptLine scriptLine, ResponseType responseType)
    {
        this(scriptLine, responseType, new HashSet<>(), new HashSet<>());
    }

    public PromptResponseScriptNode(ScriptLine scriptLine, ResponseType responseType, @NonNull Set<String> pluginKeyAllowList, @NonNull Set<String> permissionKeyAllowList)
    {
        super(scriptLine);
        this.responseType = responseType;
        this.pluginKeyAllowList = pluginKeyAllowList;
        this.permissionKeyAllowList = permissionKeyAllowList;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }


    @Override
    public Set<String> getPermissionKeyAllowList() {
        return permissionKeyAllowList;
    }

    @Override
    public boolean isPermissionInAllowList(String permissionKey) {
        return permissionKeyAllowList.contains(permissionKey);
    }

    @Override
    public Set<String> getPluginKeyAllowList() {
        return pluginKeyAllowList;
    }

    @Override
    public boolean isPluginInAllowList(String pluginKey) {
        return pluginKeyAllowList.contains(pluginKey);
    }
}
