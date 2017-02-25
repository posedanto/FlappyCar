package com.example.agitatore.fchelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class AssetLoader {
    public static Texture texture, logoTexture;
    public static TextureRegion bg, grass, logo, fcLogo, playButtonUp, playButtonDown;
    public static Animation carAnimation;
    public static TextureRegion car, carDown, carUp;
    public static TextureRegion tube, tubeDown, tubeUp;

    public  static Sound dead, flap, coin;

    public static BitmapFont font, shadow;

    public static Preferences prefs;

    public static void load() {
        prefs = Gdx.app.getPreferences("FlappyCar");
        if (!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

        texture = new Texture(Gdx.files.internal("texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        fcLogo = new TextureRegion(texture, 0, 55, 135, 24);
        fcLogo.flip(false, true);

        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        carDown = new TextureRegion(texture, 136, 0, 17, 12);
        carDown.flip(false, true);

        car = new TextureRegion(texture, 153, 0, 17, 12);
        car.flip(false, true);

        carUp = new TextureRegion(texture, 170, 0, 17, 12);
        carUp.flip(false, true);

        TextureRegion[] cars = { carDown, car, carUp };
        carAnimation = new Animation(0.06f, cars);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        tubeUp = new TextureRegion(texture, 192, 0, 24, 14);
        tubeDown = new TextureRegion(tubeUp);
        tubeDown.flip(false, true);

        tube = new TextureRegion(texture, 136, 16, 22, 3);
        tube.flip(false, true);

        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        //font.setScale(.25f, -.25f);
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        //shadow.setScale(.25f, -.25f);
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
        logoTexture.dispose();
        texture.dispose();
        dead.dispose();
        flap.dispose();
        coin.dispose();
        font.dispose();
        shadow.dispose();
    }
}
