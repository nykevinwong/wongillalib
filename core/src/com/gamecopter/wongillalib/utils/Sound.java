package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Kevin Wong on 7/25/2014.
 */
public class Sound {

    public static com.badlogic.gdx.audio.Sound hit;
    public static com.badlogic.gdx.audio.Sound jump;

    public static void load() {
        hit = load("sounds/hit.wav");
        jump = load("sounds/jump.wav");
    }

    private static com.badlogic.gdx.audio.Sound load(String name) {
        return Gdx.audio.newSound(Gdx.files.internal(name));
    }
}

