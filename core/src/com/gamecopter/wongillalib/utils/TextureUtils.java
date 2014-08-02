package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Kevin Wong on 7/10/2014.
 */
public class TextureUtils {

    public static TextureRegion[][] split(String name, int width, int height) {
        return TextureUtils.split(name, width, height, false, false);
    }

    public static TextureRegion[][] split(String name, int width, int height, boolean flipX, boolean flipY) {
        Texture texture = new Texture(Gdx.files.internal(name));
        int xSlices = texture.getWidth() / width;
        int ySlices = texture.getHeight() / height;
        TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
        for (int x = 0; x < xSlices; x++) {
            for (int y = 0; y < ySlices; y++) {
                res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
                res[x][y].flip(flipX, flipY);
            }
        }
        return res;
    }

    /*
        public static TextureRegion[][] splitWithCellSpacing (String texturePath, int xSlices, int ySlices, int width, int height, int margin, int cellSpacing, boolean flipX, boolean flipY) {
            Texture texture = new Texture(Gdx.files.internal(texturePath));

            TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
            for (int x = 0; x < xSlices; x++) {
                int cellXSpacingTotal = x * cellSpacing + margin;
                for (int y = 0; y < ySlices; y++) {
                    int cellYSpacingTotal = y * cellSpacing + margin;
                    res[x][y] = new TextureRegion(texture, x * width + cellXSpacingTotal, y * height + cellYSpacingTotal, width, height);
                    res[x][y].flip(flipX, flipY);
                }
            }
            return res;
        }
    */
    public static TextureRegion[][] splitWithCellSpacing(Texture texture, int xSlices, int ySlices, int width, int height, int margin, int cellSpacing, boolean flipX, boolean flipY) {

        TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
        for (int x = 0; x < xSlices; x++) {
            int cellXSpacingTotal = x * cellSpacing + margin;
            for (int y = 0; y < ySlices; y++) {
                int cellYSpacingTotal = y * cellSpacing + margin;
                res[x][y] = new TextureRegion(texture, x * width + cellXSpacingTotal, y * height + cellYSpacingTotal, width, height);
                res[x][y].flip(flipX, flipY);
            }
        }
        return res;
    }

}
