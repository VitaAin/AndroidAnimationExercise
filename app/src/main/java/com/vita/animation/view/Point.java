package com.vita.animation.view;

/**
 * @FileName: com.vita.animation.view.Point.java
 * @Author: Vita
 * @Date: 2018-01-26 10:45
 * @Usage:
 */
public class Point {
    private float x, y;
    private float width, height;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(float radius) {
        this.width = radius * 2;
        this.height = radius * 2;
    }

    public Point(float radius, float x, float y) {
        this.width = radius * 2;
        this.height = radius * 2;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
