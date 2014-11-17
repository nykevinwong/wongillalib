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

    void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService);

    void enterScene(UIScene scene, WongillaScript wongillaScript);

    void exitScene(UIScene scene);

    void drawScene(Batch batch, float parentAlpha);

}
