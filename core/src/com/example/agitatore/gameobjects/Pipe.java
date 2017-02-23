package com.example.agitatore.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Agitatore on 20.02.2017.
 */

public class Pipe extends Scrollable {

    private Random r;

    private Rectangle tubeUp, tubeDown, barUp, barDown;

    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;

    private float groundY;
    private boolean isScored;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        tubeUp = new Rectangle(); // головы
        tubeDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // Ширина черепа 24 пикселя. Ширина трубы всего 22 пикселя. Так что череп
        // должен быть смещен на 1 пиксель влево (так что череп будет отцентрирован
        // относительно трубы).

        // Смещение равнозначно: (SKULL_WIDTH - width) / 2
        tubeUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
                - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        tubeDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
                SKULL_WIDTH, SKULL_HEIGHT);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public void setScored(boolean b) {
        isScored = b;
    }

    public boolean collides(Car car) {
        if (position.x < car.getPosition().x + car.getWidth()) {
            return (Intersector.overlaps(car.getBoundingCircle(), barUp)
                    || Intersector.overlaps(car.getBoundingCircle(), barDown)
                    || Intersector.overlaps(car.getBoundingCircle(), tubeUp) || Intersector
                    .overlaps(car.getBoundingCircle(), tubeDown));
        }
        return false;
    }

    public Rectangle getTubeUp() {
        return tubeUp;
    }

    public Rectangle getTubeDown() {
        return tubeDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    public boolean isScored() {
        return isScored;
    }
}
