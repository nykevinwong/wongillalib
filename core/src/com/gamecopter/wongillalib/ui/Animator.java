package com.gamecopter.wongillalib.ui;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


import java.util.ArrayList;

/**
 * Created by Kevin Wong on 6/9/2014.
 */
public class Animator extends Actor {
    Animation[] animations;
    String[] AnimationNames;

    float stateTime;
    int whichAnimation = 0;

    protected int displayWidth = 0;
    protected int displayHeight = 0;


    public Animator(TextureRegion[][] tmp, float parentDuration, ArrayList<UIAnimation> UIAnimations) {


        if (UIAnimations.size() == 0) {

        } else {
            animations = new Animation[UIAnimations.size()];

            int count = 0;
            AnimationNames = new String[UIAnimations.size()];

            for (UIAnimation uiAnimation : UIAnimations) {
                int[][] indexes = uiAnimation.get2DIndexes();
                TextureRegion[] frames = new TextureRegion[indexes.length];

                for (int i = 0; i < indexes.length; i++) {
                    int x = indexes[i][0];
                    int y = indexes[i][1];
                    frames[i] = tmp[x][y];
                }

                animations[count] = new Animation(uiAnimation.getDuration(), frames);
                AnimationNames[count] = uiAnimation.getName();
                count++;
            }
        }

        stateTime = 0f;
    }

    public void setTileSize(int w, int h) {
        this.displayWidth = w;
        this.displayHeight = h;
        this.setSize(w, h);
    }

    public void setCurrentAnimation(String name) {
        if (animations.length == 1) {
            whichAnimation = 0;
        } else {
            if (AnimationNames != null) {
                int count = 0;
                for (String AniName : AnimationNames) {
                    if (AniName.equalsIgnoreCase(name)) {
                        whichAnimation = count;
                        return;
                    }
                    count++;
                }
            }

        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();           // #15

        if (animations.length > 0) {
            TextureRegion currentFrame = animations[whichAnimation].getKeyFrame(stateTime, true);  // #16

            if (displayWidth > 0 && displayHeight > 0)
                batch.draw(currentFrame, this.getX(), this.getY(), displayWidth, displayHeight);             // #17\
            else
                batch.draw(currentFrame, this.getX(), this.getY());             // #17

        }
    }

    public Rectangle getCollisionRectangle() {
        Rectangle rectangle = new Rectangle();

        Vector2 pos = this.getStage().stageToScreenCoordinates(new Vector2(this.getX(), this.getY()));

        rectangle.set(pos.x, pos.y, this.getWidth(), this.getHeight());

        return rectangle;
    }
}
