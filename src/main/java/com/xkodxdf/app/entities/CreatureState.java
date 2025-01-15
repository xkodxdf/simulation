package com.xkodxdf.app.entities;

public enum CreatureState {

    ROAM,
    FORAGE,
    ESCAPE,
    DEATH;


    public boolean isRoaming() {
        return this == ROAM;
    }

    public boolean isForaging() {
        return this == FORAGE;
    }

    public boolean isEscaping() {
        return this == ESCAPE;
    }

    public boolean isAlive() {
        return this != DEATH;
    }
}
