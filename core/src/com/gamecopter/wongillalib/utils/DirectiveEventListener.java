package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;


/**
 * Created by Kevin Wong on 6/20/2014.
 */
public class DirectiveEventListener {
    // compile function is used to manipulate XML element.
    // you can add/remove/clone XML elements based on your need.
    public void compileTemplate(XmlReader.Element tElement, boolean transclude) {

    }

    // createInstance creates an object or actor based on XML element.
    public Actor createInstance(XmlReader.Element iElement) {
        return null;
    }

    public void updateInstance(Actor a, XmlReader.Element iElement) {
    }

}
