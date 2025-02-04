package com.xkodxdf.app.entities.inanimate;

import com.xkodxdf.app.entities.base.Entity;

public class Corpse extends Entity {

    private int decayCounter = 6;

    public int getDecayCounter() {
        return decayCounter;
    }

    public void decay() {
        if (decayCounter > 0) {
            decayCounter--;
        }
    }
}
