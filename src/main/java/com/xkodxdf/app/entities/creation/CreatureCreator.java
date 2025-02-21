package com.xkodxdf.app.entities.creation;

import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.animate.Predator;
import com.xkodxdf.app.worldmap.WorldMapManagement;

public interface CreatureCreator {

    default Herbivore getHerbivore(WorldMapManagement mapManager) {
        return new Herbivore(mapManager);
    }

    default Predator getPredator(WorldMapManagement mapManager) {
        return new Predator(mapManager);
    }
}
