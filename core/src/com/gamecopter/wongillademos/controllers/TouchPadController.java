package com.gamecopter.wongillademos.controllers;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;

/**
 * Created by Kevin Wong on 6/29/2014.
 */
public class TouchPadController implements SceneEventListener {
    Actor image1;
    Touchpad touchpad;

    @Override
    public void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {
            int blockSpeed = 3;
            //Move blockSprite with TouchPad
            float x = image1.getX() + touchpad.getKnobPercentX() * blockSpeed;
            float y = image1.getY() + touchpad.getKnobPercentY() * blockSpeed;


            image1.setX(x);
            image1.setY(y);
    }

    @Override
    public void enterScene(UIScene scene, WongillaScript wongillaScript) {
        image1 = scene.findActor("image1");
        touchpad = (Touchpad) scene.findActor("touchpad");
    }

    @Override
    public void exitScene(UIScene scene) {

    }

    @Override
    public void drawScene(Batch batch, float parentAlpha) {

    }
}
