package com.xkodxdf.app.entities.animate;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.worldmap.Coordinates;
import com.xkodxdf.app.worldmap.WorldMapManagement;

public class Herbivore extends Creature {

    private static final int MIN_HP = 94;
    private static final int MAX_HP = 146;
    private static final int MIN_VIEW_RADIUS = 2;
    private static final int MAX_VIEW_RADIUS = 5;
    private static final int MIN_METABOLIC_RATE = 4;
    private static final int MAX_METABOLIC_RATE = 12;
    private static final int MIN_HUNGER_THRESHOLD = 18;
    private static final int MAX_HUNGER_THRESHOLD = 32;
    private static final int MIN_STARVATION_THRESHOLD = 88;
    private static final int MAX_STARVATION_THRESHOLD = 112;
    private static final int MIN_HUNGER_DECREASE = 18;
    private static final int MAX_HUNGER_DECREASE = 28;
    private static final int MIN_HEALTH_INCREASE = 22;
    private static final int MAX_HEALTH_INCREASE = 34;

    public Herbivore(WorldMapManagement mapManager) {
        this(
                new CharacteristicsBuilder()
                        .setHealthPointsInRange(MIN_HP, MAX_HP)
                        .setViewRadiusInRange(MIN_VIEW_RADIUS, MAX_VIEW_RADIUS)
                        .setMetabolicRateInRange(MIN_METABOLIC_RATE, MAX_METABOLIC_RATE)
                        .setHungerThresholdInRange(MIN_HUNGER_THRESHOLD, MAX_HUNGER_THRESHOLD)
                        .setStarvationThresholdInRange(MIN_STARVATION_THRESHOLD, MAX_STARVATION_THRESHOLD)
                        .setSatiateHungerDecreaseInRange(MIN_HUNGER_DECREASE, MAX_HUNGER_DECREASE)
                        .setSatiateHealthIncreaseInRange(MIN_HEALTH_INCREASE, MAX_HEALTH_INCREASE)
                        .build(),
                mapManager
        );
    }

    public Herbivore(Characteristics characteristics, WorldMapManagement mapManager) {
        super(characteristics, mapManager);
    }

    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food) {
        mapManager.removeEntity(currentCoordinates);
        mapManager.setEntity(foodCoordinates, this);
    }

    @Override
    protected void satiate(Entity food) {
        hungerLevel -= characteristics.getSatiateHungerDecrease();
        currentHealthPoints += characteristics.getSatiateHealthIncrease();
    }
}
