package com.gamecopter.wongillalib.entities.states;

import com.gamecopter.wongillalib.entities.EntityBase;

/**
 * Created by Kevin Wong on 7/24/2014.
 */
public abstract class EntityState {
    private boolean enabled;
    public boolean jumpButton = false;

    public EntityState()
    {
        this.enabled = true;
    }

    public abstract void update(EntityBase entity, float deltaTime);


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

