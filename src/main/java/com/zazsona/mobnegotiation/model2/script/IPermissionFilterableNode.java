package com.zazsona.mobnegotiation.model2.script;

import java.util.Set;

public interface IPermissionFilterableNode {

    Set<String> getPermissionKeyAllowList();

    boolean isPermissionInAllowList(String permissionKey);
}
