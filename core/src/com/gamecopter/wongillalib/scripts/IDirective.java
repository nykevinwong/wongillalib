package com.gamecopter.wongillalib.scripts;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader;

/**
 * Created by Kevin Wong on 8/8/2014.
 */
public interface IDirective {

    public String getName();
    public int getPriority();
    public void setPriority(int priority);

    public Actor processDirective(Actor actor, XmlReader.Element element);
}
