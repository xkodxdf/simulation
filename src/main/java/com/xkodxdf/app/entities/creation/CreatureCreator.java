package com.xkodxdf.app.entities.creation;

import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.animate.Predator;
import com.xkodxdf.app.map.WorldMapManage;

public interface CreatureCreator {

    default Herbivore getHerbivore(WorldMapManage mapManager) {
        return new Herbivore(mapManager);
    }

    default Predator getPredator(WorldMapManage mapManager) {
        return new Predator(mapManager);
    }
}
