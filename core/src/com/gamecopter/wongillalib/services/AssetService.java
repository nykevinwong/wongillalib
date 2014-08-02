package com.gamecopter.wongillalib.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.gamecopter.wongillalib.resources.Resources;

/**
 * Created by Kevin Wong on 6/25/2014.
 */
public class AssetService implements Disposable {

    private Skin deafaultSkin;
    private String currentSkinPath;

    protected static Resources resources = new Resources();


    public void loadAssets() {
        this.loadDeafaultSkin();
    }

    public Skin getDeafaultUISkin() {
        return deafaultSkin;
    }

    public void loadDeafaultSkin() {
        String defaultSkinPath = "skin/default/uiskin.json";

        if (currentSkinPath == null)
            currentSkinPath = defaultSkinPath;

        if (this.deafaultSkin != null)
            this.deafaultSkin.dispose();

        this.deafaultSkin = new Skin(Gdx.files.internal(currentSkinPath));
    }

    public String getCurrentSkinPath() {
        return currentSkinPath;
    }

    public void setCurrentSkinPath(String currentSkinPath) {
        this.currentSkinPath = currentSkinPath;
    }

    public boolean addResource(String name, String srcPath, Resources.ResourceType type) {
        return resources.addResource(name, srcPath, type);
    }

    public Object getResourceBySource(String src, Resources.ResourceType type) {
        return resources.getResourceBySource(src, type);
    }

    @Override
    public void dispose() {
        resources.dispose();
    }
}

