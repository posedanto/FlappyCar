package com.example.agitatore.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.agitatore.TweenAccessors.Value;
import com.example.agitatore.TweenAccessors.ValueAccessor;
import com.example.agitatore.fchelpers.AssetLoader;
import com.example.agitatore.fchelpers.InputHandler;
import com.example.agitatore.gameobjects.Car;
import com.example.agitatore.gameobjects.Grass;
import com.example.agitatore.gameobjects.Pipe;
import com.example.agitatore.gameobjects.ScrollHandler;
import com.example.agitatore.ui.SimpleButton;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private int midPointY;

    // Game Objects
    private Car car;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    // Game Assets
    private TextureAtlas.AtlasRegion bg, carMid, road, lightUp, lightDown, pipe,
            lightRed, lightYellow, lightGreen, logoGame, ready, gameOver, highScore,
            scoreboard,  retry;;
    private Animation carAnimation;


    private TweenManager manager;
    private Value alpha = new Value();

    private List<SimpleButton> menuButtons;
    private Color transitionColor;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.midPointY = midPointY;

        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    private void initGameObjects() {
        car = myWorld.getCar();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        carMid = AssetLoader.car;
        road = AssetLoader.road;
        lightUp = AssetLoader.lightUp;
        lightDown = AssetLoader.lightDown;
        pipe = AssetLoader.pipe;
        lightRed = AssetLoader.lightRed;
        lightYellow = AssetLoader.lightYellow;
        lightGreen = AssetLoader.lightGreen;
        logoGame = AssetLoader.logoGame;

        carAnimation = AssetLoader.carAnimation;


        ready = AssetLoader.ready;
        gameOver = AssetLoader.gameOver;
        highScore = AssetLoader.highScore;
        scoreboard = AssetLoader.scoreboard;
        retry = AssetLoader.retry;
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // draw sky
        shapeRenderer.setColor(149 / 255.0f, 233 / 255.0f, 252 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // draw road
        shapeRenderer.setColor(158 / 255.0f, 146 / 255.0f, 141 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batch.begin();

        batch.disableBlending();
        //batch.draw(bg, 0, midPointY + 6, 136, 60);
        batch.draw(bg, 0, midPointY + 6, 136, 60);

        drawGrass();
        drawPipes();

        batch.enableBlending();

        drawLights();

        if (myWorld.isRunning()) {
            drawBird(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawBird(runTime);
            drawReady();
        } else if (myWorld.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawScoreboard();
            drawBird(runTime);
            drawGameOver();
            drawRetry();
        } else if (myWorld.isHighScore()) {
            drawScoreboard();
            drawBird(runTime);
            drawHighScore();
            drawRetry();
        }

        batch.end();
        drawTransition(delta);

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(car.getBoundingCircle().x, car.getBoundingCircle().y, car.getBoundingCircle().radius);

        // Верхний блок для труб 1, 2 и 3
        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
                pipe1.getBarUp().width, pipe1.getBarUp().height);
        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
                pipe2.getBarUp().width, pipe2.getBarUp().height);
        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
                pipe3.getBarUp().width, pipe3.getBarUp().height);

        // Нижний блок для труб 1, 2 и 3
        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
                pipe1.getBarDown().width, pipe1.getBarDown().height);
        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
                pipe2.getBarDown().width, pipe2.getBarDown().height);
        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
                pipe3.getBarDown().width, pipe3.getBarDown().height);

        // Черепа для верхних труб 1, 2 и 3
        shapeRenderer.rect(pipe1.getTubeUp().x, pipe1.getTubeUp().y,
                pipe1.getTubeUp().width, pipe1.getTubeUp().height);
        shapeRenderer.rect(pipe2.getTubeUp().x, pipe2.getTubeUp().y,
                pipe2.getTubeUp().width, pipe2.getTubeUp().height);
        shapeRenderer.rect(pipe3.getTubeUp().x, pipe3.getTubeUp().y,
                pipe3.getTubeUp().width, pipe3.getTubeUp().height);

        // Черепа для нижних труб 1, 2 and 3
        shapeRenderer.rect(pipe1.getTubeDown().x, pipe1.getTubeDown().y,
                pipe1.getTubeDown().width, pipe1.getTubeDown().height);
        shapeRenderer.rect(pipe2.getTubeDown().x, pipe2.getTubeDown().y,
                pipe2.getTubeDown().width, pipe2.getTubeDown().height);
        shapeRenderer.rect(pipe3.getTubeDown().x, pipe3.getTubeDown().y,
                pipe3.getTubeDown().width, pipe3.getTubeDown().height);
        shapeRenderer.end();*/
    }

    private void drawGrass() {
        batch.draw(road, frontGrass.getPosition().x, frontGrass.getPosition().y,
                frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(road, backGrass.getPosition().x, backGrass.getPosition().y,
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawLights() {
        batch.draw(lightUp, pipe1.getPosition().x - 5,
                pipe1.getPosition().y + pipe1.getHeight() - 36, 28, 36);
        batch.draw(lightDown, pipe1.getPosition().x,
                pipe1.getPosition().y + pipe1.getHeight() + 45, 18, 34);

        batch.draw(lightUp, pipe2.getPosition().x - 5,
                pipe2.getPosition().y + pipe2.getHeight() - 36, 28, 36);
        batch.draw(lightDown, pipe2.getPosition().x,
                pipe2.getPosition().y + pipe2.getHeight() + 45, 18, 34);

        batch.draw(lightUp, pipe3.getPosition().x - 5,
                pipe3.getPosition().y + pipe3.getHeight() - 36, 28, 36);
        batch.draw(lightDown, pipe3.getPosition().x,
                pipe3.getPosition().y + pipe3.getHeight() + 45, 18, 34);
    }

    private void drawPipes() {
        batch.draw(pipe, pipe1.getPosition().x + 4, pipe1.getPosition().y, pipe1.getWidth(),
                pipe1.getHeight());
        batch.draw(pipe, pipe1.getPosition().x + 4, pipe1.getPosition().y + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batch.draw(pipe, pipe2.getPosition().x + 4, pipe2.getPosition().y, pipe2.getWidth(),
                pipe2.getHeight());
        batch.draw(pipe, pipe2.getPosition().x + 4, pipe2.getPosition().y + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batch.draw(pipe, pipe3.getPosition().x + 4, pipe3.getPosition().y, pipe3.getWidth(),
                pipe3.getHeight());
        batch.draw(pipe, pipe3.getPosition().x + 4, pipe3.getPosition().y + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }

    private void drawBirdCentered(float runTime) {
        batch.draw((TextureRegion) carAnimation.getKeyFrame(runTime), 59, car.getPosition().y - 15,
                car.getWidth() / 2.0f, car.getHeight() / 2.0f,
                car.getWidth(), car.getHeight(), 1, 1, car.getRotation());
    }

    private void drawBird(float runTime) {

        if (car.shouldntFlap()) {
            batch.draw(carMid, car.getPosition().x, car.getPosition().y,
                    car.getWidth() / 2.0f, car.getHeight() / 2.0f,
                    car.getWidth(), car.getHeight(), 1, 1, car.getRotation());

        } else {
            batch.draw((TextureRegion) carAnimation.getKeyFrame(runTime), car.getPosition().x,
                    car.getPosition().y, car.getWidth() / 2.0f,
                    car.getHeight() / 2.0f, car.getWidth(), car.getHeight(),
                    1, 1, car.getRotation());
        }

    }

    private void drawMenuUI() {
        batch.draw(logoGame, 136 / 2 - 56, midPointY - 50,
                135 / 1.2f, 24 / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batch);
        }

    }

    private void drawScoreboard() {
        batch.draw(scoreboard, 22, midPointY - 30, 97, 37);

        if (myWorld.getScore() > 60)
            batch.draw(lightUp, 90, midPointY - 27, 24, 31);
        else if (myWorld.getScore() > 30)
            batch.draw(lightGreen, 90, midPointY - 27, 24, 31);
        else if (myWorld.getScore() > 15)
            batch.draw(lightYellow, 90, midPointY - 27, 24, 31);
        else
            batch.draw(lightRed, 90, midPointY - 27, 24, 31);



        /*batch.draw(noStar, 25, midPointY - 15, 10, 10);
        batch.draw(noStar, 37, midPointY - 15, 10, 10);
        batch.draw(noStar, 49, midPointY - 15, 10, 10);
        batch.draw(noStar, 61, midPointY - 15, 10, 10);
        batch.draw(noStar, 73, midPointY - 15, 10, 10);

        if (myWorld.getScore() > 2) {
            batch.draw(star, 73, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 17) {
            batch.draw(star, 61, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 50) {
            batch.draw(star, 49, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 80) {
            batch.draw(star, 37, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 120) {
            batch.draw(star, 25, midPointY - 15, 10, 10);
        }*/

        int length = ("" + myWorld.getScore()).length();

        AssetLoader.font.getData().setScale(.17f, -.17f);
        AssetLoader.font.draw(batch, "" + myWorld.getScore(),
                65 - (2 * length), midPointY - 27);

        int length2 = ("" + AssetLoader.getHighScore()).length();
        AssetLoader.font.draw(batch, "" + AssetLoader.getHighScore(),
                65 - (2.5f * length2), midPointY - 7);
        AssetLoader.font.getData().setScale(.25f, -.25f);

    }

    private void drawRetry() {
        batch.draw(retry, 36, midPointY + 10, 66, 14);
    }

    private void drawReady() {
        batch.draw(ready, 36, midPointY - 50, 68, 14);
    }

    private void drawGameOver() {
        batch.draw(gameOver, 24, midPointY - 50, 92, 14);
    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batch, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batch, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawHighScore() {
        batch.draw(highScore, 22, midPointY - 50, 96, 14);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
}
