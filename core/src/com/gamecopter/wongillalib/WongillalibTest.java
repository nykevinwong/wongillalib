package com.gamecopter.wongillalib;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gamecopter.wongillalib.controllers.DefaultController;
import com.gamecopter.wongillalib.controllers.PlatformerController;
import com.gamecopter.wongillalib.controllers.TouchPadController;
import com.gamecopter.wongillalib.controllers.TouchPadOnMapController;
import com.gamecopter.wongillalib.utils.Sound;

public class WongillalibTest extends ApplicationAdapter {
    Stage stage;
    WongillaScript wongillaScript;

    public void exit() {
        Gdx.app.exit();
    }

    @Override
    public void create() {
        stage = new Stage();

        wongillaScript = new WongillaScript(stage, this);
        wongillaScript.addController("DefaultController", new DefaultController());
        wongillaScript.addController("TouchPadOnMapController", new TouchPadOnMapController());
        wongillaScript.addController("TouchPadController", new TouchPadController());
        wongillaScript.addController("PlatformerController", new PlatformerController());

        Sound.load();
        wongillaScript.LoadStage();


        // process event handler such as click event handler
        Gdx.input.setInputProcessor(stage);
    }

    public void LoadStage() {
        wongillaScript.LoadStage();
    }

    float accum = 0;
    @Override
    public void render() {

        DefaultController defaultController = (DefaultController) wongillaScript.getController("DefaultController");

        if (defaultController.IsWhiteBackground())
            Gdx.gl.glClearColor(1, 1, 1, 1);
        else
            Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        accum += delta;

        float Tick = 1.0f / 60.0f;

        while (accum > Tick) {

            wongillaScript.update();

            accum -= Tick;
        }


        wongillaScript.draw();

        // scene2d debug render has huge memory leak.
        //  wongillaScript.drawDebug();

    }


    @Override
    public void resize(int width, int height) {
        // this code handles collision detection for buttons or UI actors when the screen size is changed.
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {
        Gdx.app.log("ExitTest", "paused");
    }

    @Override
    public void dispose() {
        Gdx.app.log("ExitTest", "disposed");
        wongillaScript.dispose();
    }
}
