package com.gamecopter.wongillalib.entities;

import com.badlogic.gdx.Gdx;
import com.gamecopter.wongillalib.entities.states.EntityState;
import com.gamecopter.wongillalib.entities.states.StartState;

/**
 * Created by Kevin Wong on 7/22/2014.
 */
public class AnimalEntity extends EntityBase {

    public AnimalEntity() {
        this.setGraivty(7f);
        this.setVelocity(6f, 6f);
        this.setState(new StartState());
    }


    @Override
    public void update() {

        float detalTime = Gdx.graphics.getDeltaTime();

        EntityState state = this.getState();

        if (state != null && state.isEnabled())
            state.update(this, detalTime);

    }

}

