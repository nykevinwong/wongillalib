package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by Kevin Wong on 6/24/2014.
 */
public interface IDirective {


    String getDirectiveName();

    void addEventListener(DirectiveEventListener listener);

    void removeEventListener(DirectiveEventListener listener);

    Actor CompositeDirective(Actor actor, XmlReader.Element element);

    public enum RestrictType {
        Element,
        Attribute
    }
}
