package com.gamecopter.wongillalib.entities.plugins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.entities.EntityState;

/**
 * Created by Kevin Wong on 7/24/2014.
 */
public class FallState extends EntityState {

    public FallState() {
        super();
    }


    protected float VELOCITY_X = 5f;

    protected float MAX_GRAVITY = 15f;
    protected float gravity = 0f;


    @Override
    public void update(EntityBase entity, float deltaTime) {

        if(gravity ==0)
              gravity = entity.getGraivty();

        float vx = 0; float vy = 0;

        // if a keyboard is applied
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vx = -entity.getVelocityX();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vx = entity.getVelocityX();

        if(entity.isGravityEnabled()) {
            vy += gravity;
            gravity = entity.getGraivty() * 1.5f;
        }

        /*
        if (Gdx.input.isKeyPressed(Input.Keys.Z) ) {

                    entity.setState(new JumpState());
                    return;
        }*/



        vx = vx * deltaTime;
        vy = vy * deltaTime;

        float futureX = entity.getX() + vx;
        float futureY = entity.getY() + vy;

        if(vy < 0)
            entity.setGrounded(false);

        if (entity.isMapCollisionDetectionEnabled()) {
            // adjust futureX and futureY based on map collision result.

            if (vx != 0 && entity.isCollidedWithMap(0, futureX, entity.getY()) ) {
                // if it collides horizontally, resolve the future position.
                if (vx > 0) {
                    int tilePosition = (int) (futureX + entity.getUnitWidth());

                    futureX = tilePosition - entity.getUnitWidth();


                } else {
                    futureX = (int) (futureX + 1);
                }
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

                vy = 0;
            }

        }

        entity.setPosition(futureX, futureY);

        if(entity.isGrounded())
        {
            entity.setState(new WalkState());
        }
    }
}
