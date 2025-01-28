package com.xkodxdf.app.entities;

public enum CreatureState {

    ROAM,
    FORAGE,
    DEATH;

    public boolean isAlive() {
        return this != DEATH;
    }
}
