package com.gamecopter.wongillalib.entities;

import com.badlogic.gdx.Gdx;
import com.gamecopter.wongillalib.entities.plugins.WalkState;

/**
 * Created by Kevin Wong on 7/22/2014.
 */
public class AnimalEntity extends EntityBase {

    public AnimalEntity() {
        this.setState(new WalkState());
        this.setGraivty(8f);
        this.setVelocity(12f, 12f);
        this.enableGravity(true);
    }


    @Override
    public void update() {

        float detalTime = Gdx.graphics.getDeltaTime();

        EntityState state = this.getState();

        if (state != null && state.isEnabled())
            state.update(this, detalTime);

    }

}

