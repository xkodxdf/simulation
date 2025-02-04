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

    public Predator(WorldMapManage mapManager) {
        this(
                new CharacteristicsBuilder()
                        .setHealthPointsInRange(80, 112)
                        .setViewRadiusInRange(2, 5)
                        .setMetabolicRateInRange(4, 8)
                        .setHungerThresholdInRange(24, 36)
                        .setStarvationThresholdInRange(100, 112)
                        .setSatiateHungerDecreaseInRange(24, 32)
                        .setSatiateHealthIncreaseInRange(18, 28)
                        .setAttackStrengthInRange(24, 48)
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
        return (entity instanceof Corpse)
                && hungerLevel >= characteristics.getHungerThreshold();
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
        if (hungerLevel < 100) {
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
