package com.example.agitatore.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Agitatore on 23.02.2017.
 */

public class SimpleButton {
    private float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public SimpleButton(float x, float y, float width, float height,
                        TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);

    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(buttonDown, x, y, width, height);
        } else {
            batch.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {

        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {

        // Мы будем учитывать только touchUp в нажатом состоянии.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }

        // Когда палец с кнопки уберут, мы очистим флаг, что кнопка нажатая.
        isPressed = false;
        return false;
    }
}