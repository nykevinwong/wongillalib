package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 6/20/2014.
 */
public class ElementDirective implements IDirective {

    @Override
    public String getDirectiveName() {
        return directiveName;
    }

    protected RestrictType directiveType;
    private int priority = 3000;
    protected String directiveName;
    protected String requireDirectiveName;
    protected boolean replace = false;
    protected boolean transclude = false;
    private boolean applyCommonAttribute = true;

    protected ArrayList<DirectiveEventListener> EventListeners = new ArrayList<DirectiveEventListener>();

    public ElementDirective(String directiveName) {
        this.directiveName = directiveName;
        this.directiveType = RestrictType.Element;
    }


    @Override
    public void addEventListener(DirectiveEventListener listener) {
        // only one event is allowed to be in the list.
        EventListeners.add(listener);
    }

    @Override
    public void removeEventListener(DirectiveEventListener listener) {
        EventListeners.remove(listener);
    }


    @Override
    public Actor CompositeDirective(Actor actor, XmlReader.Element element) {
        DirectiveEventListener l = EventListeners.get(0);

        l.compileTemplate(element, this.transclude);

        Actor a = l.createInstance(element);

        if (this.directiveType == RestrictType.Attribute) {
            l.updateInstance(actor, element);
            return actor;
        } else {
            l.updateInstance(a, element);
        }

        return a;
    }


    public boolean isApplyCommonAttribute() {
        return applyCommonAttribute;
    }

    public void setApplyCommonAttribute(boolean applyCommonAttribute) {
        this.applyCommonAttribute = applyCommonAttribute;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

