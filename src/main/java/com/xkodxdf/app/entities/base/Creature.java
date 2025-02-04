package com.xkodxdf.app.entities.base;

import com.xkodxdf.app.entities.CreatureState;
import com.xkodxdf.app.entities.animate.Characteristics;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.messages.Messages;
import com.xkodxdf.app.pathfinder.PathFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Creature extends Entity {

    protected int hungerLevel;
    protected int currentHealthPoints;
    private int age;
    private CreatureState state;
    protected final Characteristics characteristics;
    protected final WorldMapManage mapManager;

    public Creature(Characteristics characteristics, WorldMapManage mapManager) {
        this.hungerLevel = 0;
        this.currentHealthPoints = characteristics.getHealthPoints();
        this.age = 0;
        this.state = CreatureState.ROAM;
        this.characteristics = characteristics;
        this.mapManager = mapManager;
        this.hunger = 0;
        this.state = CreatureState.ROAM;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getHunger() {
        return hunger;
    }

    public CreatureState getState() {
        return state;
    }

    public void setState(CreatureState state) {
        this.state = state;
    }

    public final void makeMove(PathFinder<Coordinates> pathFinder) throws InvalidParametersException {
        Optional<Coordinates> optionalCurrentCoordinates = mapManager.getEntityCoordinate(this);
        starve();
        if (healthPoints <= 0 || optionalCurrentCoordinates.isEmpty()) {
            return;
        }
        switch (state) {
            case ROAM:
                roam(optionalCurrentCoordinates.get());
                break;
            case FORAGE:
                forage(optionalCurrentCoordinates.get(), pathFinder);
                break;
            default:
                throw new InvalidParametersException(Messages.INVALID_CREATURE_STATE + state.name());
        }
    }

    public void takeDamage(int damage) {
        healthPoints -= damage;
    }

    protected Optional<Coordinates> selectFood(List<Entity> aroundFood) {
        if (aroundFood.isEmpty()) {
            return Optional.empty();
        }
        int firstElement = 0;
        return Optional.of(mapManager.getEntityCoordinate(aroundFood.get(firstElement)).get());
    }

    private void starve() {
        int hungerIncreasePerTurn = 5;
        hunger += hungerIncreasePerTurn;
        int starvationThreshold = 100;
        if (hunger > starvationThreshold) {
            healthPoints -= (hunger - starvationThreshold) / 2;
        }
    }

    private void roam(Coordinates currentCoordinates) throws InvalidParametersException {
        int moveSquaresPerTurn = 1;
        Set<Coordinates> aroundFreeCoordinates = mapManager.getAroundFreeCoordinates(currentCoordinates, moveSquaresPerTurn);
        if (!aroundFreeCoordinates.isEmpty()) {
            Coordinates target = getRandomCoordinate(aroundFreeCoordinates);
            mapManager.removeEntity(currentCoordinates);
            mapManager.setEntity(target, this);
        }
    }

    private void forage(Coordinates currentCoordinates, PathFinder<Coordinates> pathFinder) throws InvalidParametersException {
        Optional<Coordinates> optionalFoodCoordinates = findNearestFoodCoordinatesInViewRadius(currentCoordinates);
        if (optionalFoodCoordinates.isEmpty()) {
            roam(currentCoordinates);
        } else if (canEat(currentCoordinates)) {
            eat(optionalFoodCoordinates.get());
        } else {
            goToFood(currentCoordinates, optionalFoodCoordinates.get(), pathFinder);
        }
    }

    private Optional<Coordinates> findNearestFoodCoordinatesInViewRadius(Coordinates currentCoordinates)
            throws InvalidParametersException {
        List<Entity> aroundEntities;
        Set<Coordinates> aroundCoordinates;
        Set<Coordinates> aroundCoordinatesPrevious = new HashSet<>();
        for (int i = 1; i <= viewRadius; i++) {
            aroundCoordinates = mapManager.getAroundCoordinates(currentCoordinates, i);
            aroundCoordinates.removeAll(aroundCoordinatesPrevious);
            aroundCoordinatesPrevious.addAll(aroundCoordinates);
            aroundEntities = mapManager.getAroundEntities(aroundCoordinates);
            List<Entity> aroundFood = aroundEntities.stream()
                    .filter(this::isFood)
                    .collect(Collectors.toList());
            if (!aroundFood.isEmpty()) {
                return selectFood(aroundFood);
            }
        }
        return Optional.empty();
    }

    private boolean canEat(Coordinates currentCoordinates) throws InvalidCoordinatesException {
        int reachRadius = 1;
        Set<Coordinates> aroundCoordinates = mapManager.getAroundCoordinates(currentCoordinates, reachRadius);
        List<Entity> aroundEntities = mapManager.getAroundEntities(aroundCoordinates);
        return aroundEntities.stream().anyMatch(this::isFood);
    }

    private void eat(Coordinates foodCoordinates) throws InvalidParametersException {
        Optional<Entity> optionalFood = mapManager.getEntity(foodCoordinates);
        Optional<Coordinates> optionalCurrentCoordinate = mapManager.getEntityCoordinate(this);
        if (optionalFood.isEmpty() || optionalCurrentCoordinate.isEmpty()) {
            return;
        }
        handleFood(optionalCurrentCoordinate.get(), foodCoordinates, optionalFood.get());
        satiate(optionalFood.get());
        avoidHpOrHungerOutOfBound();
    }

    private void avoidHpOrHungerOutOfBound() {
        hunger = Math.max(hunger, 0);
        healthPoints = Math.min(healthPoints, 100);
    }

    private void goToFood(Coordinates currentCoordinate, Coordinates foodCoordinate, PathFinder<Coordinates> pathFinder) throws InvalidCoordinatesException {
        Set<Coordinates> freeCoordinates = mapManager.getFreeCoordinates();
        freeCoordinates.add(foodCoordinate);
        Set<Coordinates> path = pathFinder.getPath(currentCoordinate, foodCoordinate, freeCoordinates);
        if (!path.isEmpty() && path.size() != 1) {
            Coordinates target = path.stream().findFirst().get();
            mapManager.removeEntity(currentCoordinate);
            mapManager.setEntity(target, this);
        }
    }

    private Coordinates getRandomCoordinate(Set<Coordinates> coordinates) {
        int randomBound = coordinates.size();
        return (Coordinates) coordinates.toArray()[ThreadLocalRandom.current().nextInt(randomBound)];
    }

    protected abstract boolean isFood(Entity entity);

    protected abstract void satiate(Entity food);

    protected abstract void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food)
            throws InvalidCoordinatesException;
}
