package com.firsttest.game.somegame.service;

public class Position {
    protected int x, y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Position unit) {
        return Math.sqrt(Math.pow(unit.x - x, 2) + Math.pow(unit.y - y, 2));
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void setX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y += y;
    }
}