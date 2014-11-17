package com.gamecopter.wongillalib.utils;

/**
 * Created by Kevin Wong on 11/16/2014.
 */
public interface ScriptInterpreter {


    void set(java.lang.String name, java.lang.Object value);
    void setInteger(java.lang.String name, int value);
    void exec(java.lang.String s);
    java.lang.Object get(java.lang.String name);

}
