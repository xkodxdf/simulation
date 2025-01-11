package com.xkodxdf.app.actions;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMap;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Placement extends Actions {

    private int rocksFillingPercentage = 6;
    private int treesFillingPercentage = 8;
    private int grassFillingPercentage = 10;
    private int herbivoreFillingPercentage = 12;
    private int predatorFillingPercentage = 6;
    protected final WorldMap worldMap;


    public Placement(WorldMap worldMap) {
        Objects.requireNonNull(worldMap, WorldMap.class.getSimpleName() + Messages.mustNotBeNull);
        this.worldMap = worldMap;
    }

    public Placement(WorldMap worldMap, int rocksFillingPercentage, int treesFillingPercentage,
                     int grassFillingPercentage, int herbivoreFillingPercentage, int predatorFillingPercentage)
            throws InvalidParametersException {

        if (!isFillingPercentagesValid(rocksFillingPercentage, treesFillingPercentage, grassFillingPercentage,
                herbivoreFillingPercentage, predatorFillingPercentage)) {
            throw new InvalidParametersException(Messages.inanimateFillingPercentageErr);
        }

        this.worldMap = worldMap;
        this.rocksFillingPercentage = rocksFillingPercentage;
        this.treesFillingPercentage = treesFillingPercentage;
        this.grassFillingPercentage = grassFillingPercentage;
        this.herbivoreFillingPercentage = herbivoreFillingPercentage;
        this.predatorFillingPercentage = predatorFillingPercentage;

    }


    public void initPlacement() throws InvalidCoordinatesException {
        placeEntities(new Rock(), rocksFillingPercentage);
        placeEntities(new Tree(), treesFillingPercentage);
        placeEntities(new Grass(), grassFillingPercentage);
        placeEntities(new Herbivore(), herbivoreFillingPercentage);
        placeEntities(new Predator(), predatorFillingPercentage);
    }

    private boolean isFillingPercentagesValid(int rocksFillingPercentage, int treesFillingPercentage,
                                              int grassFillingPercentage, int herbivoreFillingPercentage,
                                              int predatorFillingPercentage) {
        int maxPercentageForInanimateEntities = 60;
        int maxPercentageForAnimateEntities = 20;

        return ((rocksFillingPercentage >= 0 && treesFillingPercentage >= 0 && grassFillingPercentage >= 0
                && herbivoreFillingPercentage >= 0 && predatorFillingPercentage >= 0)
                && ((rocksFillingPercentage + treesFillingPercentage + grassFillingPercentage)
                <= maxPercentageForInanimateEntities)
                && (herbivoreFillingPercentage + predatorFillingPercentage <= maxPercentageForAnimateEntities));
    }

    private void placeEntities(Entity entity, int fillingPercentage) throws InvalidCoordinatesException {
        int squaresAvailableForEntity = (int) (Math.ceil(worldMap.getSize() / 100D * fillingPercentage));
        for (int i = 0; i < squaresAvailableForEntity; i++) {
            Coordinates coordinates = getRandomUnoccupiedCoordinate(worldMap.getUnoccupiedCoordinates());
            worldMap.setEntity(coordinates, entity);
        }
    }

    private Coordinates getRandomUnoccupiedCoordinate(Set<Coordinates> unoccupiedCoordinates) {
        Coordinates[] coordinates = unoccupiedCoordinates.toArray(new Coordinates[0]);
        int randomBound = coordinates.length;

        return coordinates[ThreadLocalRandom.current().nextInt(randomBound)];
    }
}
