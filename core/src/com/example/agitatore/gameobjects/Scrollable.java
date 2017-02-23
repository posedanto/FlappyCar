package com.example.agitatore.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Agitatore on 20.02.2017.
 */

public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // Если объект Scrollable более не виден:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
    }

    // Reset: Нужно переопределять в дочернем классе, если необходимо описать
    // другое поведение
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    public float getTailX() {
        return position.x + width;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }
}
