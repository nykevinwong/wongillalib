package com.gamecopter.wongillalib.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gamecopter.wongillalib.ui.TiledMapRender;

/**
 * Created by Kevin Wong on 9/21/2014.
 */
public interface TileCollisionEventListener {

    public boolean isCollidableTile(int tileIndex);
    public int getLastCollidedTileIndex();
    public boolean isCollided(TiledMapRender tiledMapRender, int layerIndex, float unitPosX, float unitPosY, float unitSpriteWidth, float unitSpriteHeight);
}
