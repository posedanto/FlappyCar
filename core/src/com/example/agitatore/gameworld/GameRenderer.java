package com.example.agitatore.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.agitatore.fchelpers.AssetLoader;
import com.example.agitatore.gameobjects.Car;
import com.example.agitatore.gameobjects.Grass;
import com.example.agitatore.gameobjects.Pipe;
import com.example.agitatore.gameobjects.ScrollHandler;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private int gameHeight;
    private int midPointY;

    // Game Objects
    private Car car;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    // Game Assets
    private TextureRegion bg, grass;
    private Animation carAnimation;
    private TextureRegion carMid, carDown, carUp;
    private TextureRegion tubeUp, tubeDown, tube;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();
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
        grass = AssetLoader.grass;
        carAnimation = AssetLoader.carAnimation;
        carMid = AssetLoader.car;
        carDown = AssetLoader.carDown;
        carUp = AssetLoader.carUp;
        tubeUp = AssetLoader.tubeUp;
        tubeDown = AssetLoader.tubeDown;
        tube = AssetLoader.tube;
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Стартуем ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Отрисуем Background цвет
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Отрисуем Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Отрисуем Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // Заканчиваем ShapeRenderer
        shapeRenderer.end();

        // Стартуем SpriteBatch
        batch.begin();
        // Отменим прозрачность
        // Это хорошо для производительности, когда отрисовываем картинки без прозрачности
        batch.disableBlending();
        batch.draw(bg, 0, midPointY + 23, 136, 43);

        drawGrass();

        drawPipes();
        batch.enableBlending();

        drawSkulls();
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

        // ВРЕМЕННЫЙ КОД! Исправим чуть позже!

        if (myWorld.isReady()) {
            // Отрисуем сначала тень
            AssetLoader.shadow.draw(batch, "Touch me", (136 / 2) - (42), 76);
            // А теперь сам текст
            AssetLoader.font
                    .draw(batch, "Touch me", (136 / 2) - (42 - 1), 75);
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batch, "Game Over", 25, 56);
                    AssetLoader.font.draw(batch, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(batch, "High Score:", 23, 106);
                    AssetLoader.font.draw(batch, "High Score:", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    AssetLoader.shadow.draw(batch, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);
                    AssetLoader.font.draw(batch, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);
                } else {
                    AssetLoader.shadow.draw(batch, "High Score!", 19, 56);
                    AssetLoader.font.draw(batch, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(batch, "Try again?", 23, 76);
                AssetLoader.font.draw(batch, "Try again?", 24, 75);

                // Конвертируем integer в String
                String score = myWorld.getScore() + "";

                AssetLoader.shadow.draw(batch, score,
                        (136 / 2) - (3 * score.length()), 12);
                AssetLoader.font.draw(batch, score,
                        (136 / 2) - (3 * score.length() - 1), 11);

            }
        }

        String score = myWorld.getScore() + "";

        AssetLoader.shadow.draw(batch, "" + myWorld.getScore(), (136 / 2)
                - (3 * score.length()), 12);
        AssetLoader.font.draw(batch, "" + myWorld.getScore(), (136 / 2)
                - (3 * score.length() - 1), 11);

        batch.end();

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
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

        Gdx.app.log("GameRenderer", "render");
    }

    private void drawGrass() {
        // отрисуем траву
        batch.draw(grass, frontGrass.getPosition().x, frontGrass.getPosition().y,
                frontGrass.getWidth(), frontGrass.getHeight());
        batch.draw(grass, backGrass.getPosition().x, backGrass.getPosition().y,
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {
        // Временный код, извините за кашу :)
        // Мы это починим, как только закончим с Pipe классом.

        batch.draw(tubeUp, pipe1.getPosition().x - 1,
                pipe1.getPosition().y + pipe1.getHeight() - 14, 24, 14);
        batch.draw(tubeDown, pipe1.getPosition().x - 1,
                pipe1.getPosition().y + pipe1.getHeight() + 45, 24, 14);

        batch.draw(tubeUp, pipe2.getPosition().x - 1,
                pipe2.getPosition().y + pipe2.getHeight() - 14, 24, 14);
        batch.draw(tubeDown, pipe2.getPosition().x - 1,
                pipe2.getPosition().y + pipe2.getHeight() + 45, 24, 14);

        batch.draw(tubeUp, pipe3.getPosition().x - 1,
                pipe3.getPosition().y + pipe3.getHeight() - 14, 24, 14);
        batch.draw(tubeDown, pipe3.getPosition().x - 1,
                pipe3.getPosition().y + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        // Временный код, извините за кашу :)
        // Мы это починим, как только закончим с Pipe классом.
        batch.draw(tube, pipe1.getPosition().x, pipe1.getPosition().y, pipe1.getWidth(),
                pipe1.getHeight());
        batch.draw(tube, pipe1.getPosition().x, pipe1.getPosition().y + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batch.draw(tube, pipe2.getPosition().x, pipe2.getPosition().y, pipe2.getWidth(),
                pipe2.getHeight());
        batch.draw(tube, pipe2.getPosition().x, pipe2.getPosition().y + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batch.draw(tube, pipe3.getPosition().x, pipe3.getPosition().y, pipe3.getWidth(),
                pipe3.getHeight());
        batch.draw(tube, pipe3.getPosition().x, pipe3.getPosition().y + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }
}