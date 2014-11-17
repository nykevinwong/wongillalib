package com.gamecopter.wongillalib.desktop;

import com.gamecopter.wongillalib.utils.ScriptInterpreter;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;

/**
 * Created by Kevin Wong on 11/16/2014.
 */
public class DesktopPythonInterpreter implements ScriptInterpreter {
    PythonInterpreter interp = null;

    public DesktopPythonInterpreter()
    {
        interp =  new PythonInterpreter();
    }


    @Override
    public void set(String name, Object value) {
        interp.set(name,  value);
    }

    @Override
    public void setInteger(String name, int value) {
        interp.set(name,  new PyInteger(value));
    }

    @Override
    public void exec(String s) {
        interp.exec(s);
    }

    @Override
    public Object get(String name) {
        return interp.get(name);
    }
}
