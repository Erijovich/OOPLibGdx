package com.firsttest.game.somegame.service;

public enum SpellBook {
    heal1(2, -1), heal10 (8,-10), heal50(25, -50),
    ressurect (60, Float.NaN),
    fire1(2, 5), fire10 (8, 30), fire50(25, 100);


    private final int cost;
    private final float power;

    SpellBook(int cost, float power) {
        this.power = power;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public float getPower() {
        return power;
    }

}