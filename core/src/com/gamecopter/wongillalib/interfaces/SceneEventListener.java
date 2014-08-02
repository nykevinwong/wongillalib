package com.gamecopter.wongillalib.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.WongillaScript;

/**
 * Created by Kevin Wong on 7/25/2014.
 */
public interface SceneEventListener {

    void update(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService);

    void sceneCreated(UIScene scene);

    void draw(Batch batch, float parentAlpha);

}
