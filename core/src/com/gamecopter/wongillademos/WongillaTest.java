package com.gamecopter.wongillademos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gamecopter.wongillademos.controllers.*;
import com.gamecopter.wongillalib.WongillaScript;
import com.gamecopter.wongillalib.utils.Sound;

public class WongillaTest extends ApplicationAdapter {
	Stage stage;
	WongillaScript wongillaScript;


	public void exit() {
		Gdx.app.exit();
	}

	@Override
	public void create() {

		Matrix4 projection = new Matrix4();

		// actual game screen size, not display size
		projection.setToOrtho(0, 640, 480, 0, -1, 1);

		SpriteBatch spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(projection);

		stage = new Stage(new StretchViewport(640,480),spriteBatch);


		wongillaScript = new WongillaScript(stage, this);

		wongillaScript.loadLibraries();


		wongillaScript.addController("DefaultController", new DefaultController());
		wongillaScript.addController("TouchPadOnMapController", new TouchPadOnMapController());
		wongillaScript.addController("TouchPadController", new TouchPadController());
		wongillaScript.addController("PlatformerController", new PlatformerController());
		wongillaScript.addController("PythonTestController", new PythonTestController());


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
		stage.getViewport().update(width, height, false);
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
