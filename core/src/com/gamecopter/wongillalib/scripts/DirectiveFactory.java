package com.gamecopter.wongillalib.scripts;

import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 8/20/2014.
 */
public abstract class DirectiveFactory<T extends IDirective> {
    protected AssetService assetService;
    protected ScopeService scopeService;

    public DirectiveFactory(ScopeService scopeService, AssetService assetService)
    {
        this.scopeService = scopeService;
        this.assetService = assetService;
    }

    public abstract ArrayList<T> createDirectives();


}
