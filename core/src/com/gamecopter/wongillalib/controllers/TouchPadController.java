package com.gamecopter.wongillalib.controllers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.WongillaScript;

/**
 * Created by Kevin Wong on 6/29/2014.
 */
public class TouchPadController implements SceneEventListener {
    @Override
    public void update(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {
        Actor image1 = scene.findActor("image1");

        if (image1 != null) {
            Touchpad touchpad = (Touchpad) scene.findActor("touchpad");
            int blockSpeed = 3;
            //Move blockSprite with TouchPad
            float x = image1.getX() + touchpad.getKnobPercentX() * blockSpeed;
            float y = image1.getY() + touchpad.getKnobPercentY() * blockSpeed;


            image1.setX(x);
            image1.setY(y);
        }
    }

    @Override
    public void sceneCreated(UIScene scene) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }
}
