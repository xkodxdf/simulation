package com.xkodxdf.app.entities.animate;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManager;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Predator extends Creature {

    private final int attackStrength;

    public Predator(WorldMapManager mapManager) {
        this(120, 3, 34, mapManager);
    }

    public Predator(int healthPoints, int viewRadius, int attackStrength, WorldMapManager mapManager) {
        super(healthPoints, viewRadius, mapManager);
        this.attackStrength = attackStrength;
    }

    @Override
    protected boolean isFood(Entity entity) {
        if (entity instanceof Herbivore) {
            return true;
        }
        return (entity instanceof Corpse) && hunger >= 100;
    }

    @Override
    protected void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food)
            throws InvalidCoordinatesException {
        if (food instanceof Herbivore) {
            Herbivore herb = (Herbivore) food;
            herb.takeDamage(attackStrength);
        } else if (food instanceof Corpse) {
            mapManager.removeEntity(currentCoordinates);
            mapManager.setEntity(foodCoordinates, this);
        }
    }

    @Override
    protected void satiate(Entity food) {
        if ((food instanceof Herbivore) && (((Herbivore) food).getHealthPoints() <= 0)) {
            int satiateHungerDecrease = 20;
            int satiateHealthIncrease = 10;
            hunger -= satiateHungerDecrease;
            healthPoints += satiateHealthIncrease;
        } else if (food instanceof Corpse) {
            int baseSatiateHungerDecrease = 3;
            int baseSatiateHealthIncrease = 1;
            int decayIndicator = ((Corpse) food).getDecayCounter();
            hunger = hunger - (baseSatiateHungerDecrease * decayIndicator);
            healthPoints = healthPoints + (baseSatiateHealthIncrease * decayIndicator);
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
        if (hunger < 100) {
            return mapManager.getEntityCoordinate(creatureWithMinHealth);
        } else if ((canOneHitKill(creatureWithMinHealth)) || (corpses.isEmpty())) {
            return mapManager.getEntityCoordinate(creatureWithMinHealth);
        } else {
            return mapManager.getEntityCoordinate(getFreshestCorpse(corpses));
        }
    }

    private Creature getCreatureWithMinHealth(List<Creature> creatures) {
        return creatures.stream()
                .min(Comparator.comparing(Creature::getHealthPoints))
                .get();
    }

    private Corpse getFreshestCorpse(List<Corpse> corpses) {
        return corpses.stream()
                .max(Comparator.comparing(Corpse::getDecayCounter))
                .get();
    }

    private boolean canOneHitKill(Creature creature) {
        return creature.getHealthPoints() <= attackStrength;
    }
}
