package com.example.eliasskilje.surfaceviewexample;

public class FlashlightCone {
    private int x;
    private int y;
    private int radius;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void update(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public FlashlightCone(int viewWidth, int viewHeight) {
        x = viewWidth / 2;
        y = viewHeight / 2;
        radius = ((viewWidth <= viewHeight) ? x / 3 : y / 3);
    }
}
