package com.example.agitatore.flappycar;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.example.agitatore.fchelpers.AssetLoader;
import com.example.agitatore.screens.GameScreen;
import com.example.agitatore.screens.SplashScreen;

public class FCGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
