package com.gamecopter.wongillademos.controllers;

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

    public void OnDialogResult(Object result)
    {
        Boolean YesNo = (Boolean)result;

        if(YesNo)
        {
            isWhiteColor = true;
        }
        else
        {
            isWhiteColor = false;
        }

    }
}
