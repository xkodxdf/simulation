package com.xkodxdf.app.entities.animate;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Predator extends Creature {

    private static final int MIN_HP = 80;
    private static final int MAX_HP = 112;
    private static final int MIN_VIEW_RADIUS = 2;
    private static final int MAX_VIEW_RADIUS = 5;
    private static final int MIN_METABOLIC_RATE = 4;
    private static final int MAX_METABOLIC_RATE = 8;
    private static final int MIN_HUNGER_THRESHOLD = 24;
    private static final int MAX_HUNGER_THRESHOLD = 36;
    private static final int MIN_STARVATION_THRESHOLD = 100;
    private static final int MAX_STARVATION_THRESHOLD = 112;
    private static final int MIN_HUNGER_DECREASE = 24;
    private static final int MAX_HUNGER_DECREASE = 32;
    private static final int MIN_HEALTH_INCREASE = 18;
    private static final int MAX_HEALTH_INCREASE = 28;
    private static final int MIN_ATTACK_STRENGTH = 32;
    private static final int MAX_ATTACK_STRENGTH = 52;

    public Predator(WorldMapManage mapManager) {
        this(
                new CharacteristicsBuilder()
                        .setHealthPointsInRange(MIN_HP, MAX_HP)
                        .setViewRadiusInRange(MIN_VIEW_RADIUS, MAX_VIEW_RADIUS)
                        .setMetabolicRateInRange(MIN_METABOLIC_RATE, MAX_METABOLIC_RATE)
                        .setHungerThresholdInRange(MIN_HUNGER_THRESHOLD, MAX_HUNGER_THRESHOLD)
                        .setStarvationThresholdInRange(MIN_STARVATION_THRESHOLD, MAX_STARVATION_THRESHOLD)
                        .setSatiateHungerDecreaseInRange(MIN_HUNGER_DECREASE, MAX_HUNGER_DECREASE)
                        .setSatiateHealthIncreaseInRange(MIN_HEALTH_INCREASE, MAX_HEALTH_INCREASE)
                        .setAttackStrengthInRange(MIN_ATTACK_STRENGTH, MAX_ATTACK_STRENGTH)
                        .build(),
                mapManager
        );
    }

    public Predator(Characteristics characteristics, WorldMapManage mapManager) {
        super(characteristics, mapManager);
    }

    @Override
    protected boolean isFood(Entity entity) {
        if (entity instanceof Herbivore) {
            return true;
        }
        return (entity instanceof Corpse) && (hungerLevel >= characteristics.getHungerThreshold());
    }

    @Override
    protected void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food)
            throws InvalidCoordinatesException {
        if (food instanceof Herbivore) {
            Herbivore herb = (Herbivore) food;
            herb.takeDamage(characteristics.getAttackStrength());
        } else if (food instanceof Corpse) {
            mapManager.removeEntity(currentCoordinates);
            mapManager.setEntity(foodCoordinates, this);
        }
    }

    @Override
    protected void satiate(Entity food) {
        int satiateHungerDecrease = characteristics.getSatiateHungerDecrease();
        int satiateHealthIncrease = characteristics.getSatiateHealthIncrease();
        if ((food instanceof Herbivore) && (((Herbivore) food).getCurrentHealthPoints() <= 0)) {
            hungerLevel -= satiateHungerDecrease;
            currentHealthPoints += satiateHealthIncrease;
        } else if (food instanceof Corpse) {
            int penaltyForEatingCarrion = 10;
            int corpseSatiateHungerDecrease = satiateHungerDecrease / penaltyForEatingCarrion;
            int baseSatiateHealthIncrease = satiateHealthIncrease / penaltyForEatingCarrion;
            int decayIndicator = ((Corpse) food).getDecayCounter();
            hungerLevel = hungerLevel - (corpseSatiateHungerDecrease * decayIndicator);
            currentHealthPoints = currentHealthPoints + (baseSatiateHealthIncrease * decayIndicator);
        }
    }

    @Override
    protected Optional<Coordinates> selectFood(List<Entity> aroundFood) {
        List<Corpse> corpses = selectCorpses(aroundFood);
        List<Creature> creatures = selectCreatures(aroundFood);
        if ((corpses.isEmpty()) && (creatures.isEmpty())) {
            return Optional.empty();
        }
        if (!creatures.isEmpty()) {
            return selectCreatureOrCorpse(creatures, corpses);
        } else {
            return mapManager.getEntityCoordinate(getFreshestCorpse(corpses));
        }
    }

    private List<Corpse> selectCorpses(List<Entity> food) {
        return food.stream()
                .filter(entity -> entity instanceof Corpse)
                .map(entity -> (Corpse) entity)
                .collect(Collectors.toList());
    }

    private List<Creature> selectCreatures(List<Entity> food) {
        return food.stream()
                .filter(entity -> entity instanceof Creature)
                .map(entity -> (Creature) entity)
                .collect(Collectors.toList());
    }

    private Optional<Coordinates> selectCreatureOrCorpse(List<Creature> creatures, List<Corpse> corpses) {
        Creature creatureWithMinHealth = getCreatureWithMinHealth(creatures);
        if (hungerLevel < characteristics.getHungerThreshold()) {
            return mapManager.getEntityCoordinate(creatureWithMinHealth);
        } else if ((canOneHitKill(creatureWithMinHealth)) || (corpses.isEmpty())) {
            return mapManager.getEntityCoordinate(creatureWithMinHealth);
        } else {
            return mapManager.getEntityCoordinate(getFreshestCorpse(corpses));
        }
    }

    private Creature getCreatureWithMinHealth(List<Creature> creatures) {
        return creatures.stream()
                .min(Comparator.comparing(Creature::getCurrentHealthPoints))
                .get();
    }

    private Corpse getFreshestCorpse(List<Corpse> corpses) {
        return corpses.stream()
                .max(Comparator.comparing(Corpse::getDecayCounter))
                .get();
    }

    private boolean canOneHitKill(Creature creature) {
        return creature.getCurrentHealthPoints() <= characteristics.getAttackStrength();
    }
}
