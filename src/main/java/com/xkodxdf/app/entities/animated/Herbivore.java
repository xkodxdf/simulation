package com.xkodxdf.app.entities.animated;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManager;

public class Herbivore extends Creature {

    public Herbivore(WorldMapManager mapManager) {
        this(100, 2, mapManager);
    }

    public Herbivore(int healthPoints, int viewRadius, WorldMapManager mapManager) {
        super(healthPoints, viewRadius, mapManager);
    }

    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food)
            throws InvalidCoordinatesException {
        mapManager.removeEntity(currentCoordinates);
        mapManager.setEntity(foodCoordinates, this);
    }

    @Override
    protected void satiate(Entity food) {
        int satiateHungerDecrease = 16;
        int satiateHealthIncrease = 25;
        hunger -= satiateHungerDecrease;
        healthPoints += satiateHealthIncrease;
    }
}
