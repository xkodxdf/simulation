package com.xkodxdf.app.entities.inanimate;

import com.xkodxdf.app.entities.base.Entity;

public class Tree extends Entity {

    private static Tree instance;

    private Tree() {
    }

    public static Tree getInstance() {
        if (instance == null) {
            instance = new Tree();
        }
        return instance;
    }
}
