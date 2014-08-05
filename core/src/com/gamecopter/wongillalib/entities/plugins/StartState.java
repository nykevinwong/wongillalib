package com.gamecopter.wongillalib.entities.plugins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gamecopter.wongillalib.entities.EntityBase;
import com.gamecopter.wongillalib.entities.EntityState;

/**
 * Created by Kevin Wong on 8/5/2014.
 */
public class StartState extends EntityState
{
    @Override
    public void update(EntityBase entity, float deltaTime) {

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
        {
            entity.enableGravity(true);
            entity.setState(new WalkState());
        }

    }
}
