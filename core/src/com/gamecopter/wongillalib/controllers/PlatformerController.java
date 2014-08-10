package com.gamecopter.wongillalib.controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.entities.AnimalEntity;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.entities.plugins.StartState;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.ui.Animator;
import com.gamecopter.wongillalib.ui.TiledMapRender;


/**
 * Created by Kevin Wong on 7/7/2014.
 */
public class PlatformerController implements SceneEventListener {

    protected EntityBase entity;

    public PlatformerController() {
        entity = new AnimalEntity();
        resetEntity();
    }


    public void resetEntity() {

        entity.setX(3);
        entity.setY(3);
        entity.enableGravity(false);
        entity.setState(new StartState());
    }

    public void enableGravity() {
        entity.enableGravity(!entity.isGravityEnabled());
    }

    public void enableCollisionDetection() {

        entity.enableMapCollisionDetection(!entity.isMapCollisionDetectionEnabled());
    }

    TiledMapRender gameMap;
    Touchpad touchpad;
    Animator player;
    Label l;

    @Override
    public void sceneCreated(UIScene scene) {
        gameMap = (TiledMapRender) scene.findActor("gameMap");
        touchpad = (Touchpad) scene.findActor("touchpad");
        player = (Animator) scene.findActor("kenny");
        l = (Label) scene.findActor("info");

        if (gameMap != null) {

            Label mapInfo = (Label) scene.findActor("mapInfo");

            gameMap.setDebug(mapInfo);

            int tileSize = gameMap.getDisplayTileWidth();
            // make the touch pad transparent
            Color c = touchpad.getColor();
            Color nc = new Color(c.r, c.g, c.b, 0.8f);
            touchpad.setColor(nc);

            entity.setRequiredComponents(gameMap, player, tileSize);
            entity.setTouchpad(touchpad); // enable touchpad;

            if(Gdx.app.getType()== Application.ApplicationType.Android) {
                touchpad.setVisible(true);
            }

            if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
              touchpad.setVisible(false);
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void update(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {

            entity.update();
            entity.draw();

            Vector2 currentPos = new Vector2(player.getX(), Gdx.graphics.getHeight() - player.getY());

            int x = (int) entity.getX();
            int y = (int) entity.getY();
            int index = gameMap.getLayerMapIndex(0, x, y + 1); // get the tile under the feet.
            String message = "";

            message = "(" + entity.getX() + "," + entity.getY() + ") - (" + (int) currentPos.x + "," + (int) currentPos.y + ") = [" + index + "] - ";
            message += gameMap.getMapName();

            scopeService.updateScopeVariable("IsGrounded", entity.isGrounded());
            scopeService.updateScopeVariable("IsGravity", entity.isGravityEnabled());

            l.setText(message);

    }


}
