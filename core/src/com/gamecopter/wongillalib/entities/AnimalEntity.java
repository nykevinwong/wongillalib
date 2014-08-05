package com.gamecopter.wongillalib.entities;

import com.badlogic.gdx.Gdx;
import com.gamecopter.wongillalib.entities.plugins.StartState;
import com.gamecopter.wongillalib.entities.plugins.WalkState;

/**
 * Created by Kevin Wong on 7/22/2014.
 */
public class AnimalEntity extends EntityBase {

    public AnimalEntity() {
        this.setGraivty(6f);
        this.setVelocity(12f, 12f);
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

