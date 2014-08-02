package com.gamecopter.wongillalib.controllers;

/**
 * Created by Kevin Wong on 5/18/2014.
 */
public class DefaultController {
    protected boolean isWhiteColor = false;

    public void SwitchColor()
    {
        isWhiteColor = !isWhiteColor;
    }

    public boolean IsWhiteBackground()
    {
        return isWhiteColor;
    }

}
