package com.xkodxdf.app.entities;

public enum CreatureState {

    ROAM,
    FORAGE,
    IN_DANGER,
    DEATH;


    public boolean isRoaming() {
        return this == ROAM;
    }

    public boolean isForaging() {
        return this == FORAGE;
    }

    public boolean isInDanger() {
        return this == IN_DANGER;
    }

    public boolean isAlive() {
        return this != DEATH;
    }
}
