package com.xkodxdf.app.entities.creation;

import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;

public interface EntityCreator extends CreatureCreator, CorpseCreator, GrassCreator {

    default Rock getRock() {
        return Rock.getInstance();
    }

    default Tree getTree() {
        return Tree.getInstance();
    }
}
