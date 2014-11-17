package com.gamecopter.wongillalib.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamecopter.wongillalib.WongillalibTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 640;
        config.height = 480;

		new LwjglApplication(new WongillalibTest(new DesktopPythonInterpreter()), config);
	}
}
