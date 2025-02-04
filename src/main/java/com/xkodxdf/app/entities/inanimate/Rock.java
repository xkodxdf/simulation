package com.xkodxdf.app.entities.inanimate;

import com.xkodxdf.app.entities.base.Entity;

public class Rock extends Entity {

    private static Rock instance;

    private Rock() {
    }

    public static Rock getInstance() {
        if (instance == null) {
            instance = new Rock();
        }
        return instance;
    }
}
