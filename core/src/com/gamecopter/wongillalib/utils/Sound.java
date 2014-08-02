package com.gamecopter.wongillalib.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Kevin Wong on 7/25/2014.
 */
public class Sound {

    public static com.badlogic.gdx.audio.Sound boom;
    public static com.badlogic.gdx.audio.Sound hit;
    public static com.badlogic.gdx.audio.Sound splat;
    public static com.badlogic.gdx.audio.Sound launch;
    public static com.badlogic.gdx.audio.Sound pew;
    public static com.badlogic.gdx.audio.Sound oof;
    public static com.badlogic.gdx.audio.Sound gethat;
    public static com.badlogic.gdx.audio.Sound death;
    public static com.badlogic.gdx.audio.Sound startgame;
    public static com.badlogic.gdx.audio.Sound jump;
    public static com.badlogic.gdx.audio.Sound coin;
    public static com.badlogic.gdx.audio.Sound messageblock;

    public static void load() {
        boom = load("sounds/boom.wav");
        hit = load("sounds/hit.wav");
        splat = load("sounds/splat.wav");
        launch = load("sounds/launch.wav");
        pew = load("sounds/pew.wav");
        oof = load("sounds/oof.wav");
        gethat = load("sounds/gethat.wav");
        death = load("sounds/death.wav");
        startgame = load("sounds/startgame.wav");
        jump = load("sounds/jump.wav");
        coin = load("sounds/smw_coin.wav");
        messageblock = load("sounds/smw_message_block.wav");


    }

    private static com.badlogic.gdx.audio.Sound load(String name) {
        return Gdx.audio.newSound(Gdx.files.internal(name));
    }
}

