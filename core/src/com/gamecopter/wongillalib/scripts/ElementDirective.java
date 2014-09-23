package com.gamecopter.wongillalib.scripts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 8/8/2014.
 */
public abstract class ElementDirective implements IDirective {
    private String name;
    private int priority;
    private boolean applyCommonAttribute = true;
    private ArrayList<AttributeDirective> attributes;

    public ElementDirective(String name)
    {
        this.name = name;
    }

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

        compileTemplate(element);
        Actor a = createInstance(element);
        updateInstance(a, element);

        return a;
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

    public void add(AttributeDirective d)
    {
        if (attributes == null)
            attributes = new ArrayList<AttributeDirective>();

        attributes.add(d);
    }

    public boolean isApplyCommonAttribute() {
        return applyCommonAttribute;
    }


    public void setApplyCommonAttribute(boolean applyCommonAttribute) {
        this.applyCommonAttribute = applyCommonAttribute;
    }
}
