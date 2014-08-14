package com.gamecopter.wongillalib.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.ui.Animator;
import com.gamecopter.wongillalib.ui.TiledMapRender;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 7/20/2014.
 */
public abstract class EntityBase {

    protected float x;
    protected float y;
    protected float vx;
    protected float vy;

    private EntityState state;

    private boolean grounded;
    private boolean gravityEnabled;
    private float graivty = 0.2f;
    private float displayWidth;
    private float displayHeight;
    private float tileSize;
    protected Animator player;

    private boolean dampingXEnabled;
    private boolean dampingYEnabled;

    private boolean YScrollingEnable;


    private boolean enableMapCollisionDetection = true;
    private TiledMapRender gameMap;

    public EntityBase() {

    }

    public abstract void update();

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


    public float getVelocityX() {
        return vx;
    }

    public void setVelocityX(float vx) {
        this.vx = vx;
    }

    public float getVelocityY() {
        return vy;
    }

    public void setVelocityY(float vy) {
        this.vy = vy;
    }

    public float getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(float displayWidth) {
        this.displayWidth = displayWidth;
    }

    public float getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(float displayHeight) {
        this.displayHeight = displayHeight;
    }

    public float getUnitWidth() {
        return this.displayWidth / tileSize;
    }

    public float getUnitHeight() {
        return this.displayHeight / tileSize;
    }


    public float getTileSize() {
        return tileSize;
    }

    public void setTileSize(float tileSize) {
        this.tileSize = tileSize;
    }

    public float getGraivty() {
        return graivty;
    }

    public void setGraivty(float graivty) {
        this.graivty = graivty;
    }

    public boolean isGravityEnabled() {
        return gravityEnabled;
    }

    public void enableGravity(boolean enableGravity) {
        this.gravityEnabled = enableGravity;
    }

    public boolean isMapCollisionDetectionEnabled() {
        return enableMapCollisionDetection;
    }

    public void enableMapCollisionDetection(boolean enableMapCollisionDetection) {
        this.enableMapCollisionDetection = enableMapCollisionDetection;
    }



    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isDampingXEnabled() {
        return dampingXEnabled;
    }

    public void setDampingXEnabled(boolean dampingXEnabled) {
        this.dampingXEnabled = dampingXEnabled;
    }

    public boolean isDampingYEnabled() {
        return dampingYEnabled;
    }

    public void setDampingYEnabled(boolean dampingYEnabled) {
        this.dampingYEnabled = dampingYEnabled;
    }

    private Touchpad touchpad;


    public void setRequiredComponents(TiledMapRender gameMap, Animator animator, int tileSize) {
        this.gameMap = gameMap;
        this.setTileSize(tileSize);
        this.setDisplayWidth(animator.getWidth());
        this.setDisplayHeight(animator.getHeight());
        this.player = animator;
    }

    float DAMPING = 0.87f;

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void setTouchpad(Touchpad touchpad) {
        this.touchpad = touchpad;
    }

    public boolean isCollidedWithMap(float layerIndex, float x, float y) {
        if (gameMap != null) {
            return gameMap.isCollided(0, x, y, this.getUnitWidth(), this.getUnitHeight());
        }

        return false;
    }


    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public void draw() {
        Vector2 stagedCoord = Vector2.Zero;

        if (player != null &&  gameMap != null  ) {
            // convert to lbgdx stage Coordination for drawing position
            int DisplayX = (int) (this.getX() * tileSize);
            float OffsetX = DisplayX - (int) (Gdx.app.getGraphics().getWidth() / 2);


            if (OffsetX > 0) {
                float lastEdge = gameMap.getLayerWidth(0) * tileSize - Gdx.graphics.getWidth();

                if (OffsetX > lastEdge) {
                    OffsetX = lastEdge;  // make sure the last tile on the row always display fixed on the right-most of the screen.
                    DisplayX -= OffsetX; // fix the disappear issue when the sprite passes over last Edge.
                } else {
                    // make the sprite stay at the center while moving the background of the screen when passing over the half width of the screen
                    DisplayX = (int) (Gdx.app.getGraphics().getWidth() / 2);
                }
            } else {
                OffsetX = 0;
            }

            int DisplayY = (int) ((this.getY() + 1) * tileSize);
            float OffsetY = 0;

            if(this.isYScrollingEnable()) {
                OffsetY = DisplayY - (int) (Gdx.app.getGraphics().getHeight() / 2);
                if (OffsetY > 0) {
                    float lastEdge = gameMap.getLayerHeight(0) * tileSize - Gdx.graphics.getHeight();

                    if (OffsetY > lastEdge) {
                        OffsetY = lastEdge;
                        DisplayY -= OffsetY;
                    } else {
                        DisplayY = (int) (Gdx.app.getGraphics().getHeight() / 2);
                    }
                } else {
                    OffsetY = 0;
                }
            }


            gameMap.setDrawOffset(OffsetX, OffsetY);

            stagedCoord = new Vector2(DisplayX, Gdx.graphics.getHeight() - DisplayY);
            player.setPosition(stagedCoord.x, stagedCoord.y);

        }

    }

    public TiledMapRender getGameMap() {
        return gameMap;
    }

    public void setGameMap(TiledMapRender gameMap) {
        this.gameMap = gameMap;
    }


    public boolean isYScrollingEnable() {
        return YScrollingEnable;
    }

    public void enalbeYScrolling(boolean YScrollingEnable) {
        this.YScrollingEnable = YScrollingEnable;
    }
}
