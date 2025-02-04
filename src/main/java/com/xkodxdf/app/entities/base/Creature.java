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
    }

    public boolean isHungry() {
        return hungerLevel >= characteristics.getHungerThreshold();
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public boolean isDead() {
        return currentHealthPoints <= 0;
    }

    public int getAge() {
        return age;
    }

    public Characteristics characteristics() {
        return characteristics;
    }

    public CreatureState getState() {
        return state;
    }

    public void setState(CreatureState state) {
        this.state = state;
    }

    public boolean isOnFullHp() {
        return characteristics.getHealthPoints() == currentHealthPoints;
    }

    public final void makeMove(PathFinder<Coordinates> pathFinder) throws InvalidParametersException {
        Optional<Coordinates> optionalCurrentCoordinates = mapManager.getEntityCoordinate(this);
        starve();
        if (currentHealthPoints <= 0 || optionalCurrentCoordinates.isEmpty()) {
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
        age++;
    }

    public void takeDamage(int damage) {
        currentHealthPoints -= damage;
    }

    private void starve() {
        hungerLevel += characteristics.getMetabolicRate();
        int starvationThreshold = characteristics.getStarvationThreshold();
        if (hungerLevel > starvationThreshold) {
            currentHealthPoints -= (hungerLevel - starvationThreshold) / 2;
        }
    }

    private void roam(Coordinates currentCoordinates) throws InvalidParametersException {
        int moveSquaresPerTurn = 1;
        Set<Coordinates> aroundFreeCoordinates = mapManager.getAroundFreeCoordinates(currentCoordinates, moveSquaresPerTurn);
        if (!aroundFreeCoordinates.isEmpty()) {
            Coordinates target = mapManager.getOneRandomFreeCoordinates(aroundFreeCoordinates).get();
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
        for (int i = 1; i <= characteristics.getViewRadius(); i++) {
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

    protected Optional<Coordinates> selectFood(List<Entity> aroundFood) {
        if (aroundFood.isEmpty()) {
            return Optional.empty();
        }
        int firstElement = 0;
        return mapManager.getEntityCoordinate(aroundFood.get(firstElement));
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
        hungerLevel = Math.max(hungerLevel, 0);
        currentHealthPoints = Math.min(currentHealthPoints, 100);
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

    protected abstract boolean isFood(Entity entity);

    protected abstract void satiate(Entity food);

    protected abstract void handleFood(Coordinates currentCoordinates, Coordinates foodCoordinates, Entity food)
            throws InvalidCoordinatesException;
}
