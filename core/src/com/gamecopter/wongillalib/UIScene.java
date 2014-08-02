package com.gamecopter.wongillalib;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Kevin Wong on 6/4/2014.
 */
public class UIScene extends WidgetGroup {


    private String Controller;


    private Actor findActor(Group g, String name) {
        Array<Actor> actors = g.getChildren();

        for (Actor a : actors) {
            String actorName = a.getName();

            if (actorName != null && actorName.equalsIgnoreCase(name))
                return a;

            if (a instanceof Table) {
                Actor found = findActor((Group) a, name);

                if (found != null)
                    return found;
            }
        }

        return null;
    }

    public Actor findActor(String name) {
        return this.findActor(this, name);
    }

    public String getControllerName() {
        return Controller;
    }

    public void setControllerName(String controller) {
        Controller = controller;
    }
}
