package com.xkodxdf.app.entities.creation;

import com.xkodxdf.app.entities.inanimate.Grass;

public interface GrassCreator {

    default Grass getGrass() {
        return new Grass();
    }
}
