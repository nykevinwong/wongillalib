package com.gamecopter.wongillalib.interfaces;

import com.gamecopter.wongillalib.scripts.Namespace;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;

/**
 * Created by Kevin Wong on 9/6/2014.
 */
public interface LoadLibraryEventListener {
    public String getNamespaceFullName();
    public void addLibrary(Namespace rootNamespace, ScopeService scopeService, AssetService assetService);
}
