package com.gamecopter.wongillalib.android;

import com.gamecopter.wongillalib.utils.ScriptInterpreter;

/**
 * Created by Kevin Wong on 11/16/2014.
 */
public class AndroidPythonInterpreter implements ScriptInterpreter {
    @Override
    public void set(String name, Object value) {

    }

    @Override
    public void setInteger(String name, int value) {

    }

    @Override
    public void exec(String s) {

    }

    @Override
    public Object get(String name) {
        String s = "Android doesn't support Python Script.";
        return s;
    }
}
