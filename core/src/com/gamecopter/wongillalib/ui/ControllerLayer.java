package com.gamecopter.wongillalib.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;


/**
 * Created by Kevin Wong on 7/28/2014.
 */
public class ControllerLayer extends Actor {
    protected SceneEventListener sceneEventListener;

    public ControllerLayer(SceneEventListener sceneEventListener) {
        this.sceneEventListener = sceneEventListener;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (this.sceneEventListener != null)
            this.sceneEventListener.draw(batch, parentAlpha);

    }


}
