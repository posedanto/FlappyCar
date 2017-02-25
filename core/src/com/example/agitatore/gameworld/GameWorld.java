package com.example.agitatore.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.example.agitatore.fchelpers.AssetLoader;
import com.example.agitatore.gameobjects.Car;
import com.example.agitatore.gameobjects.ScrollHandler;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class GameWorld {

    private Car car;
    private ScrollHandler scroller;

    private Rectangle ground;

    private int score = 0;
    public int midPointY;
    private float runTime = 0;

    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public GameWorld(int midPointY) {
        currentState = GameState.READY;
        this.midPointY = midPointY;
        car = new Car(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11); //137-136?
    }

    public void update(float delta) {
        runTime += delta;
        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    public void updateReady(float delta) {
        car.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        car.update(delta);
        scroller.update(delta);

        if (scroller.collides(car) && car.isAlive()) {
            scroller.stop();
            car.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(car.getBoundingCircle(), ground)) {
            scroller.stop();
            car.die();
            car.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }

        Gdx.app.log("GameWorld", "updateRunning");
    }

    public void addScore(int increment) {
        score += increment;
    }

    public Car getCar() {
        return car;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart() {
        //currentState = GameState.READY;
        score = 0;
        car.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }
}
