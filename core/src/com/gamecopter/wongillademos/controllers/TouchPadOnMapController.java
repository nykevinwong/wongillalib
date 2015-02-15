package com.gamecopter.wongillademos.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.ui.TiledMapRender;

/**
 * Created by Kevin Wong on 7/7/2014.
 */
public class TouchPadOnMapController implements SceneEventListener {
    TiledMapRender gameMap;
    Touchpad touchpad;

    private void makeTransparent(Actor a) {
        // make the touch pad transparent
        Color c = a.getColor();
        Color nc = new Color(c.r, c.g, c.b, 0.8f);
        a.setColor(nc);
    }

    @Override
    public void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {


            int blockSpeed = 3;
            //Move blockSprite with TouchPad
            float x = gameMap.getDrawOffsetX() + touchpad.getKnobPercentX() * 2;
            float y = gameMap.getDrawOffsetY() + touchpad.getKnobPercentY() * -2;

            gameMap.setDrawOffset(x, y);
    }

    @Override
    public void enterScene(UIScene scene, WongillaScript wongillaScript) {
        gameMap = (TiledMapRender) scene.findActor("gameMap");
        touchpad = (Touchpad) scene.findActor("touchpad");

        makeTransparent(touchpad);

    }

    @Override
    public void exitScene(UIScene scene) {

    }

    @Override
    public void drawScene(Batch batch, float parentAlpha) {

    }


}
