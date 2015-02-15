package com.gamecopter.wongillademos.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.states.EntityState;

/**
 * Created by Kevin Wong on 7/24/2014.
 */
public class WalkState  extends EntityState {

    public WalkState()
    {
        super();
    }

    protected static boolean JumpPressed = false;

    float vx = 0;
    float vy = 0;

    @Override
    public void update(EntityBase entity, float deltaTime) {


        if (Gdx.input.isKeyPressed(Input.Keys.Z) || this.jumpButton == true )
        {
            this.jumpButton = false; // this is  a button press..

            if(JumpPressed==false) {
                JumpPressed = true;



                if (entity.isGrounded()) {
                    entity.setGrounded(false);
                    entity.setState(new JumpState());
                    return;
                }
            }
        }
    else
    {
        JumpPressed = false;
    }

        Touchpad touchpad = entity.getTouchpad();
        if (touchpad != null) // if a touchpad is applied
        {
            float knobPercentX = touchpad.getKnobPercentX();

            if (knobPercentX != 0)
                vx = knobPercentX * entity.getVelocityX();
        }

        // if a keyboard is applied
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vx = -entity.getVelocityX();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vx = entity.getVelocityX();


        if(entity.isGravityEnabled())
            vy+= entity.getGraivty();

        if(entity.isDampingXEnabled())
            vx *=  0.88;

        vx = vx * deltaTime;
        vy = vy * deltaTime;


        float futureX = entity.getX() + vx;
        float futureY = entity.getY() + vy;
        float unitWidth = entity.getUnitWidth();
        float unitHeight = entity.getUnitHeight();


        if (entity.isMapCollisionDetectionEnabled()) {
            // adjust futureX and futureY based on map collision result.


                if (vx != 0 && entity.isCollidedWithMap(0, futureX, entity.getY())) {
                    // if it collides horizontally, resolve the future position.
                    if (vx > 0) {
                        int tilePosition = (int) (futureX + entity.getUnitWidth());

                        futureX = tilePosition - entity.getUnitWidth();


                    } else {
                        futureX = (int) (futureX + 1);
                    }

                    vx = 0;

                }

            if (vy != 0 && entity.isCollidedWithMap(0, futureX, futureY)) {
                // if it collides vertically, resolve the future position.
                if (vy > 0) {
                    int tilePosition = (int) (futureY + entity.getUnitHeight());

                    futureY = tilePosition - entity.getUnitHeight();

                    if(entity.isGravityEnabled()) {
                        entity.setGrounded(true);
                    }

                } else {
                    futureY = (int) (futureY + 1);
                }

                vy=0;
            }
            else
            {
                // no tile below the entity and velocity > 0
                if(vy > 0) {
                    if (entity.isGravityEnabled()) {
                        entity.setGrounded(false);
                        entity.setState(new FallState());
                    }
                }

            }
        }


        entity.setPosition(futureX, futureY);
    }


}
