package com.gamecopter.wongillademos.controllers;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.utils.ScriptInterpreter;

import java.util.Random;

/**
 * Created by Kevin Wong on 11/16/2014.
 */
public class PythonTestController implements SceneEventListener {

    Random random = new Random();

    ScriptInterpreter interp = null;

    public void JythonTest()
    {
        String result = "";
        String pythonCode ="";

        try {
            // Create an instance of the PythonInterpreter

            // The exec() method executes strings of code
            interp.exec("import sys");
            interp.exec("print sys");

            int value = (int)(random.nextFloat() * 10);
            pythonCode = "x = 2+2+" + value;

            // Set variable values within the PythonInterpreter instance
            interp.setInteger("a",42);
            interp.exec("print a");
            interp.exec(pythonCode);

            // Obtain the value of an object from the PythonInterpreter and store it
            // into a PyObject.
            Object x = interp.get("x");
            System.out.println("x: " + x);
            result = "x:" + x;
        }
        catch(Exception e)
        {
            pythonCode = e.getMessage();
        }

       if(l!=null)
       {
           l.setText(pythonCode + " => "  + result);
       }
    }

    @Override
    public void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {
    }

    Label l;
    @Override
    public void enterScene(UIScene scene, WongillaScript wongillaScript) {
        l = (Label) scene.findActor("info");
        interp = wongillaScript.getScriptInterpreter();
    }

    @Override
    public void exitScene(UIScene scene) {

    }

    @Override
    public void drawScene(Batch batch, float parentAlpha) {

    }
}

