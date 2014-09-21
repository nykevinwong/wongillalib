package com.gamecopter.wongillalib.entities.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.interfaces.TileCollisionEventListener;
import com.gamecopter.wongillalib.utils.Sound;

/**
 * Created by Kevin Wong on 7/24/2014.
 */
public class JumpState extends EntityState {

    public JumpState() {
        super();
        JUMPING_VELOCITY = JUMPING_VELOCITY_INIT;
        Sound.jump.play();
    }

    protected float JUMPING_VELOCITY = 0f;

    private float JUMPING_VELOCITY_INIT = 42f;

    @Override
    public void update(EntityBase entity, float deltaTime) {

        float vx = 0; float vy = 0;

        // if a keyboard is applied
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vx = -entity.getVelocityX();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vx = entity.getVelocityX();

        Touchpad touchpad = entity.getTouchpad();
        if (touchpad != null) // if a touchpad is applied
        {
            float knobPercentX = touchpad.getKnobPercentX();

            if (knobPercentX != 0)
                vx = knobPercentX * entity.getVelocityX();


        }

        if(entity.isGravityEnabled())
            vy+= entity.getGraivty();


        if(JUMPING_VELOCITY > 0)
        {
            vy-= JUMPING_VELOCITY;
            JUMPING_VELOCITY *= 0.9;
        }

        vx = vx * deltaTime;
        vy = vy * deltaTime;

        float futureX = entity.getX() + vx;
        float futureY = entity.getY() + vy;

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
                    TileCollisionEventListener l = entity.getGameMap().getTileCollisonEventListener();

                    if(l!=null) {
                        int lastCollidedTileIndex = l.getLastCollidedTileIndex();

                        if (lastCollidedTileIndex == 132) // hit the question mark and turn the question mark tile into a disabled question mark tile.
                        {
                            try {
                                entity.getGameMap().replaceTile(0, (int) futureX, (int) futureY, 131);
                                Sound.hit.play();
                            } catch (Exception ex) {

                            }
                        }
                    }

                    futureY = (int) (futureY + 1);

                    if(entity.isGravityEnabled()) {
                        JUMPING_VELOCITY = 0;
                        entity.setState(new FallState());
                    }
                }

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
