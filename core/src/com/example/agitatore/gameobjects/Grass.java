package com.example.agitatore.gameobjects;

/**
 * Created by Agitatore on 20.02.2017.
 */

public class Grass extends  Scrollable {

    public Grass(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }

}
