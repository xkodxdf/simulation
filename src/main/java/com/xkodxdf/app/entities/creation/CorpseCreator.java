package com.xkodxdf.app.entities.creation;

import com.xkodxdf.app.entities.inanimate.Corpse;

public interface CorpseCreator {

    default Corpse getCorpse() {
        return new Corpse();
    }
}
