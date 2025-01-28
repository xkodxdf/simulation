package com.xkodxdf.app.pathfinder;

import java.util.Set;

public interface PathFinder<C> {

    Set<C> getPath(C source, C target, Set<C> availableCoordinates);
}
