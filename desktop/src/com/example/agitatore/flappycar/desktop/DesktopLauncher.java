package com.example.agitatore.flappycar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.example.agitatore.flappycar.FCGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Flappy Car";
		config.width = 272;
		config.height = 408;
		new LwjglApplication(new FCGame(), config);
	}
}
