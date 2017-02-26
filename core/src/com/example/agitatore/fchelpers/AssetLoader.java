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
    public static Texture temp1, temp2, temp3, temp4, temp5, temp6;
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

        temp1 = new Texture(Gdx.files.internal("data/background.png"));
        temp1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        temp2 = new Texture(Gdx.files.internal("data/car1.png"));
        temp2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        temp3 = new Texture(Gdx.files.internal("data/road.png"));
        temp3.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        temp4 = new Texture(Gdx.files.internal("data/light2.png"));
        temp4.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        temp5 = new Texture(Gdx.files.internal("data/light1.png"));
        temp5.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        temp6 = new Texture(Gdx.files.internal("data/pipe.png"));
        temp6.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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

        //bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg = new TextureRegion(temp1, 0, 0, 621, 273);
        bg.flip(false, true);

        //grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass = new TextureRegion(temp3, 0, 0, 165, 11);
        grass.flip(false, true);

        //carDown = new TextureRegion(texture, 136, 0, 17, 12);
        carDown = new TextureRegion(temp2, 0, 0, 510, 214);
        carDown.flip(false, true);

        //car = new TextureRegion(texture, 153, 0, 17, 12);
        car = new TextureRegion(temp2, 0, 0, 510, 214);
        car.flip(false, true);

        //carUp = new TextureRegion(texture, 170, 0, 17, 12);
        carUp = new TextureRegion(temp2, 0, 0, 510, 214);
        carUp.flip(false, true);

        TextureRegion[] cars = { carDown, car, carUp };
        carAnimation = new Animation(0.06f, cars);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //tubeUp = new TextureRegion(texture, 192, 0, 24, 14);
        //tubeDown = new TextureRegion(tubeUp);
        tubeUp = new TextureRegion(temp5, 0, 0, 56, 72);
        tubeUp.flip(false, true);
        tubeDown = new TextureRegion(temp4, 0, 0, 100, 191);
        tubeDown.flip(false, true);

        //tube = new TextureRegion(texture, 136, 16, 22, 3);
        tube = new TextureRegion(temp6, 0, 0, 10, 3);
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

        temp1.dispose();
        temp2.dispose();
        temp3.dispose();
        temp4.dispose();
        temp5.dispose();
        temp6.dispose();
    }
}
