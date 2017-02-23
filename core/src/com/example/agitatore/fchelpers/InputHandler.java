package com.example.agitatore.fchelpers;

import com.badlogic.gdx.InputProcessor;
import com.example.agitatore.gameobjects.Car;
import com.example.agitatore.gameworld.GameWorld;

/**
 * Created by Agitatore on 19.02.2017.
 */

public class InputHandler implements InputProcessor {

    private GameWorld myWorld;
    private Car myCar;

    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
        myCar = myWorld.getCar();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (myWorld.isReady()) {
            myWorld.start();
        }

        myCar.onClick();

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            myWorld.restart();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
