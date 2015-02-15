package com.gamecopter.wongillademos.entities;

import com.badlogic.gdx.Gdx;
import com.gamecopter.wongillademos.states.StartState;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.states.EntityState;


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

