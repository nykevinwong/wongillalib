package com.gamecopter.wongillalib.scripts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by Kevin Wong on 8/8/2014.
 */
public abstract class AttributeDirective implements IDirective {
    String name;
    int priority;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Actor processDirective(Actor actor, XmlReader.Element element) {

        updateInstance(actor, element);

        return actor;
    }

    // compile function is used to manipulate XML element.
    // you can add/remove/clone XML elements based on your need.
    public void compileTemplate(XmlReader.Element tElement)
    {

    }

    // createInstance creates an object or actor based on XML element.
    public Actor createInstance(XmlReader.Element iElement)
    {
        return null;
    }

    public void updateInstance(Actor a, XmlReader.Element iElement)
    {

    }

}
