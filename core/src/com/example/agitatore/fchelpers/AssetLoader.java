package com.example.agitatore.fchelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class AssetLoader {

    public static TextureAtlas atlas;
    public static TextureAtlas.AtlasRegion bg, car, road, lightUp, lightDown, pipe,
            lightRed, lightYellow, lightGreen, logoGame, playButtonUp, playButtonDown,
            ready, gameOver, highScore, scoreboard, retry;

    public static Texture logoTexture;
    public static TextureRegion logo;

    public static Animation carAnimation;

    public  static Sound dead, flap, coin, fall;

    public static BitmapFont font, shadow;

    public static Preferences prefs;

    public static void load() {
        prefs = Gdx.app.getPreferences("FlappyCar");
        if (!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        atlas = new TextureAtlas(Gdx.files.internal("data/textures.atlas"));

        bg = atlas.findRegion("background");
        bg.flip(false, true);

        car = atlas.findRegion("car");
        car.flip(false, true);

        road = atlas.findRegion("road");
        road.flip(false, true);

        lightUp = atlas.findRegion("light1");
        lightUp.flip(false, true);

        lightDown = atlas.findRegion("light2");
        lightDown.flip(false, true);

        pipe = atlas.findRegion("pipe");
        pipe.flip(false, true);

        lightRed = atlas.findRegion("lightred");
        lightRed.flip(false, true);
        lightYellow = atlas.findRegion("lightyellow");
        lightYellow.flip(false, true);
        lightGreen = atlas.findRegion("lightgreen");
        lightGreen.flip(false, true);

        logoGame = atlas.findRegion("fclogo");
        logoGame.flip(false, true);

        playButtonUp = atlas.findRegion("buttonup");
        playButtonUp.flip(false, true);
        playButtonDown = atlas.findRegion("buttondown");
        playButtonDown.flip(false, true);

        ready = atlas.findRegion("ready");
        ready.flip(false, true);

        gameOver = atlas.findRegion("gameover");
        gameOver.flip(false, true);

        highScore = atlas.findRegion("highscore");
        highScore.flip(false, true);

        scoreboard = atlas.findRegion("scoreboard");
        scoreboard.flip(false, true);

        retry = atlas.findRegion("retry");
        retry.flip(false, true);

        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 800, 600);

        TextureRegion[] cars = { car, car, car };
        carAnimation = new Animation(0.06f, cars);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        atlas.dispose();
        logoTexture.dispose();
        dead.dispose();
        flap.dispose();
        coin.dispose();
        fall.dispose();
        font.dispose();
        shadow.dispose();
    }
}
