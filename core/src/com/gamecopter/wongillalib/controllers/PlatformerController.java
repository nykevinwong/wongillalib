package com.gamecopter.wongillalib.controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.entities.AnimalEntity;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.entities.states.StartState;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.interfaces.TileCollisionEventListener;
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
    public void enterScene(UIScene scene) {
        gameMap = (TiledMapRender) scene.findActor("gameMap");
        touchpad = (Touchpad) scene.findActor("touchpad");
        player = (Animator) scene.findActor("kenny");
        l = (Label) scene.findActor("info");

        if (gameMap != null) {

            // attach debug information on this label
            Label mapInfo = (Label) scene.findActor("mapInfo");
            gameMap.attachDebugInfoLabel(mapInfo);

            // provide a collision detection implementation
            gameMap.setTileCollisonEventListener(loadDefaultTileCollisionEventListener());


            int tileSize = gameMap.getDisplayTileWidth();
            // make the touch pad transparent
            Color c = touchpad.getColor();
            Color nc = new Color(c.r, c.g, c.b, 0.8f);
            touchpad.setColor(nc);

            entity.setRequiredComponents(gameMap, player, tileSize);
            entity.setTouchpad(touchpad); // enable touchpad;

            // display touchpad only on android
            if(Gdx.app.getType()== Application.ApplicationType.Android) {
                touchpad.setVisible(true);
            }

            if(Gdx.app.getType()== Application.ApplicationType.Desktop) {
              touchpad.setVisible(false);
            }
        }

    }

    @Override
    public void exitScene(UIScene scene) {

    }

    @Override
    public void drawScene(Batch batch, float parentAlpha) {

    }

    @Override
    public void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {

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

    public TileCollisionEventListener loadDefaultTileCollisionEventListener()
    {
        TileCollisionEventListener defaultListener = new TileCollisionEventListener() {
            protected int lastCoolidedTileIndex = 0;

            @Override
            public boolean isCollidableTile(int index) {

                if (index == 192 || // box
                        index == 265 || // green smily
                        index == 131 || // question mark
                        index == 132) // question mark
                {
                    return true;
                }

                if (index <= (240)) {
                    int remainder = index % 30;

                    if (remainder <= 10)// most of tiles are collidable
                        return true;

                }

                return false;
            }

            @Override
            public int getLastCollidedTileIndex()
            {
                return lastCoolidedTileIndex;
            }

            @Override
            public boolean isCollided(TiledMapRender tiledMapRender, int layerIndex, float unitPosX, float unitPosY, float unitSpriteWidth, float unitSpriteHeight)
            {
                TiledMapTileLayer layer = (TiledMapTileLayer) tiledMapRender.getLayers().get(layerIndex);
                lastCoolidedTileIndex = 0;

                float tileWidth = layer.getTileWidth();
                float tileHeight = layer.getTileHeight();

                // convert game coordination to an unit coordiation system

                int startX = (int) unitPosX;
                int endX = (int) (unitPosX + unitSpriteWidth - (1 / tileWidth)); // - 1 pixel for a 32x32 tile, only check from 0 to 31. not 0 to 32.

                int startY = (int) unitPosY;
                int endY = (int) (unitPosY + unitSpriteHeight - (1 / tileHeight)); // - 1 pixel

                String hitMessage = "Checked Tiles: ";


                for (int x = startX; x <= endX; x++)
                    for (int y = startY; y <= endY; y++) {
                        int index = tiledMapRender.getLayerMapIndex(layerIndex, x, y);

                        hitMessage += ", (" + x + "," + y + ") = " + index;


                        if (index != -1 &&
                                isCollidableTile(index)) {
                            lastCoolidedTileIndex = index;
                            return true;
                        }

                    }


                return false;
            }

        };

        return defaultListener;
    }

}
