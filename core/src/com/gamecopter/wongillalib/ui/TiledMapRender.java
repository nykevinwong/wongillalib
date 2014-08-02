package com.gamecopter.wongillalib.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Kevin Wong on 7/17/2014.
 */
public class TiledMapRender extends Actor {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    int eWidth = 16;
    int eHeight = 16;

    public TiledMapRender(String tmxFilePath) {
        map = new TmxMapLoader().load(tmxFilePath);


    }

    public void setDebug(Label label) {
        final Label l = label;
        final TiledMapRender render = this;

        this.setWidth(Gdx.graphics.getWidth());
        this.setHeight(Gdx.graphics.getHeight());
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                float tmpX = x;
                float tmpY = Gdx.graphics.getHeight() - y;
                tmpX += render.getDrawOffsetX();
                tmpY += render.getDrawOffsetY();

                tmpX /= render.getDisplayTileWidth();
                tmpY /= render.getDisplayTileHeight();

                int index = render.getLayerMapIndex(0, (int) tmpX, (int) tmpY);

                String info = "Clicked at (" + (int) tmpX + "," + (int) tmpY + ") = map index: [" + index + "] : colliable:" + render.isCollidableTile(index);

                l.setText(info);

            }

        });
    }

    private float drawOffsetX;
    private float drawOffsetY;


    public void setDrawOffset(float x, float y) {
        this.setDrawOffsetX(x);
        this.setDrawOffsetY(y);
    }

    public void setTileSize(int width, int height) {
        this.eWidth = width;
        this.eHeight = height;
    }


    public int getDisplayTileWidth() {
        return this.eWidth;
    }

    public int getDisplayTileHeight() {
        return this.eHeight;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        int xo = 0;
        int yo = 0;
        int xr = 0;
        int yr = 0;

        int errorX = 0;
        int errorY = 0;

        if (drawOffsetX > 0) {
            // draw from xo to xr
            xo = (int) (drawOffsetX / (double) this.eWidth);
            xr = (int) (drawOffsetX % this.eWidth);
        }

        if (drawOffsetY > 0) {
            //draw from yo to yr
            yo = (int) (drawOffsetY / (double) this.eHeight);
            yr = (int) (drawOffsetY % this.eHeight);
        }

        try {

            MapLayers layers = map.getLayers();
            for (MapLayer l : layers) {

                TiledMapTileLayer layer = (TiledMapTileLayer) l;


                int width = layer.getWidth();
                int height = layer.getHeight();
                int viewBoundWidth = Gdx.graphics.getWidth() / eWidth;
                int viewBoundHeight = Gdx.graphics.getHeight() / eHeight;
                int viewBoundWidthRemain = Gdx.graphics.getWidth() % eWidth;
                int viewBoundHeightRemain = Gdx.graphics.getHeight() % eHeight;

                if (viewBoundWidthRemain > 0)
                    viewBoundWidth += 1;

                if (viewBoundHeightRemain > 0)
                    viewBoundHeight += 1;

                if ((drawOffsetX % eWidth) > 0) {
                    // we only draw partial of the first tile on the row, then we need to add 1 to viewBoundWidth
                    viewBoundWidth += 1;

                }

                if ((drawOffsetY % eHeight) > 0) {
                    // we only draw partial of the first tile on the column, then we need to add 1 to viewBoundHeight
                    viewBoundHeight += 1;

                }

                for (int x = xo; x <= xo + viewBoundWidth; x++) {
                    for (int y = yo; y <= yo + viewBoundHeight + 1; y++) {
                        errorX = x;
                        errorY = y;
                        if (x >= 0 && y >= 0 && x <= width && y <= height) {
                            TiledMapTileLayer.Cell cell = layer.getCell(x, height - y);

                            if (cell == null) continue;

                            int xp = (x - xo) * this.eWidth;
                            int yp = (y - yo) * this.eHeight;

                            TextureRegion region = cell.getTile().getTextureRegion();

                            // batch.draw(mapTexture[ximg][yimg], xp - xr, ( yp - yr)  , this.eWidth, this.eHeight);
                            // the position specified here is for lower-left corner.
                            batch.draw(region, xp - xr, Gdx.app.getGraphics().getHeight() - (yp - yr), eWidth, eHeight);

                        }
                    }
                }


            }
        } catch (Exception ex) {
            String error = ex.getMessage() + " at (" + errorX + "," + errorY + ")";

        }


    }

    public float getDrawOffsetX() {
        if (drawOffsetX < 0) {
            drawOffsetX = 0;
        }

        return drawOffsetX;
    }

    public void setDrawOffsetX(float drawOffsetX) {
        this.drawOffsetX = drawOffsetX;
    }

    public float getDrawOffsetY() {
        if (drawOffsetY < 0) {
            drawOffsetY = 0;
        }

        return drawOffsetY;
    }

    public void setDrawOffsetY(float drawOffsetY) {
        this.drawOffsetY = drawOffsetY;
    }

    public int getLayerWidth(int layerIndex) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        return layer.getWidth();
    }

    public int getLayerHeight(int layerIndex) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        return layer.getHeight();
    }

    public TiledMapTileLayer.Cell getLayerMapTile(int layerIndex, int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        int height = layer.getHeight() - 1; // bottom - 1 is the top tile position for tiled map.

        if (x >= 0 && y >= 0 && x <= layer.getWidth() && y <= layer.getHeight()) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, height - y);

            if (cell != null) {
                return cell;
            }

        }

        return null;
    }

    public int getLayerMapIndex(int layerIndex, int x, int y) {
        TiledMapTileLayer.Cell cell = getLayerMapTile(layerIndex, x, y);

        if (cell != null) {
            return cell.getTile().getId();
        }

        return -1;
    }

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

            if (remainder <= 10)
                return true;

        }

        return false;
    }

    private String lastMessage = "";
    private int lastCoolidedTileIndex = 0;

    public int getLastCollidedTileIndex() {
        return lastCoolidedTileIndex;
    }

    public void replaceTile(int layerIndex, int x, int y, int index) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);

        if (x >= 0 && y >= 0 && x <= layer.getWidth() && y <= layer.getHeight()) {
            TiledMapTile tile = (TiledMapTile) map.getTileSets().getTile(index);
            int height = layer.getHeight() - 1;
            layer.getCell(x, height - y).setTile(tile);
        }

    }

    public boolean isCollided(int layerIndex, float unitPosX, float unitPosY, float unitSpriteWidth, float unitSpriteHeight) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        lastCoolidedTileIndex = 0;

        float tileWidth = layer.getTileWidth();
        float tileHeight = layer.getTileHeight();

        int startX = (int) unitPosX;
        int endX = (int) (unitPosX + unitSpriteWidth - (1 / tileWidth)); // - 1 pixel, only check from 0 to 31. not 0 to 32.

        int startY = (int) unitPosY;
        int endY = (int) (unitPosY + unitSpriteHeight - (1 / tileHeight)); // - 1 pixel

        String hitMessage = "Checked Tiles: ";

        for (int x = startX; x <= endX; x++)
            for (int y = startY; y <= endY; y++) {
                int index = getLayerMapIndex(layerIndex, x, y);

                hitMessage += ", (" + x + "," + y + ") = " + index;


                if (index != -1 &&
                        isCollidableTile(index)) {
                    lastCoolidedTileIndex = index;
                    return true;
                }

            }


        return false;
    }


    public String getMapName() {
        return (String) map.getProperties().get("name");
    }

}


