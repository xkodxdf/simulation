package com.xkodxdf.app.entities.animate;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

public class Herbivore extends Creature {

    public Herbivore(WorldMapManage mapManager) {
        this(
                new CharacteristicsBuilder()
                        .setHealthPointsInRange(94, 146)
                        .setViewRadiusInRange(2, 8)
                        .setMetabolicRateInRange(4, 12)
                        .setHungerThresholdInRange(18, 32)
                        .setStarvationThresholdInRange(88, 112)
                        .setSatiateHungerDecreaseInRange(18, 28)
                        .setSatiateHealthIncreaseInRange(22, 34)
                        .build(),
                mapManager
        );
    }

    public Herbivore(Characteristics characteristics, WorldMapManage mapManager) {
        super(characteristics, mapManager);
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
        hungerLevel -= characteristics.getSatiateHungerDecrease();
        currentHealthPoints += characteristics.getSatiateHealthIncrease();
    }
}
