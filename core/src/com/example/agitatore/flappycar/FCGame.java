package com.example.agitatore.flappycar;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.example.agitatore.fchelpers.AssetLoader;
import com.example.agitatore.screens.GameScreen;

public class FCGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
		Gdx.app.log("Game", "created");
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
