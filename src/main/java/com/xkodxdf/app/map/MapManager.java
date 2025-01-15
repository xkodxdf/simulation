package com.xkodxdf.app.map;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.config.Config;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MapManager implements Manager {

    private final WorldMap map;
    private final Config config;


    public MapManager(Config config) {
        Objects.requireNonNull(config);
        this.config = config;
        this.map = new WorldMap(generateMapCoordinates());
    }


    @Override
    public int getWidth() {
        return config.getWidth();
    }

    @Override
    public int getHeight() {
        return config.getHeight();
    }

    @Override
    public int getSize() {
        return config.getWidth() * config.getHeight();
    }

    @Override
    public void setEntity(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        map.setEntity(coordinates, entity);
    }

    @Override
    public Entity getEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        return map.getEntity(coordinates);
    }

    @Override
    public Entity removeEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        return map.removeEntity(coordinates);
    }

    @Override
    public int getEntityMapFillingPercentage(EntityType entityType) throws InvalidParametersException {
        switch (entityType) {
            case ROCK:
                return config.getRocksMapFillingPercentage();
            case TREE:
                return config.getTreesMapFillingPercentage();
            case CORPSE:
                return 0;
            case GRASS:
                return config.getGrassMapFillingPercentage();
            case HERBIVORE:
                return config.getHerbivoreMapFillingPercentage();
            case PREDATOR:
                return config.getPredatorMapFillingPercentage();
            default:
                throw new InvalidParametersException("REPLACE!");

        }
    }

    @Override
    public Coordinates getEntityCoordinate(Entity entity) {
        for (Map.Entry<Coordinates, Entity> entityEntry : map.entities.entrySet()) {
            if (entityEntry.getValue().equals(entity)) {
                return entityEntry.getKey();
            }
        }

        return null;
    }

    @Override
    public Set<Coordinates> getUnoccupiedCoordinates() {
        return new HashSet<>(map.coordinates);
    }

    @Override
    public Coordinates getRandomUnoccupiedCoordinate() {
        Coordinates[] coordinates = map.coordinates.toArray(new Coordinates[0]);
        int randomBound = coordinates.length;

        return coordinates[ThreadLocalRandom.current().nextInt(randomBound)];
    }

    @Override
    public Set<Coordinates> getAroundCoordinates(Coordinates target, int radius) {
        int x = target.getX();
        int y = target.getY();
        Set<Coordinates> result = new HashSet<>();

        for (int row = -radius; row <= radius; row++) {
            for (int col = -radius; col <= radius; col++) {
                Coordinates coordinate = new Coordinates(x + col, y + row);
                if (!coordinate.equals(target) && isCoordinatesValid(coordinate)) {
                    result.add(coordinate);
                }
            }
        }

        return result;
    }

    @Override
    public Set<Coordinates> getAroundUnoccupiedCoordinates(Coordinates target, int radius) {
        Set<Coordinates> result = getAroundCoordinates(target, radius);
        result.retainAll(map.coordinates);

        return result;
    }

    @Override
    public List<Entity> getAroundEntities(Coordinates target, int radius) throws InvalidCoordinatesException {
        List<Entity> result = new ArrayList<>();
        for (Coordinates coordinate : getAroundCoordinates(target, radius)) {
            result.add(getEntity(coordinate));
        }

        return result;
    }


    @Override
    public Set<Map.Entry<Coordinates, Creature>> getCreaturesWithCoordinates() {
        return map.entities.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Creature)
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), (Creature) entry.getValue()))
                .collect(Collectors.toSet());
    }


    @Override
    public Set<Map.Entry<Coordinates, Predator>> getPredatorsWithCoordinates() {
        return map.entities.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Predator)
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), (Predator) entry.getValue()))
                .collect(Collectors.toSet());
    }


    @Override
    public List<Creature> getCreatures() {
        return map.entities.values().stream()
                .filter(entity -> entity instanceof Creature).map(entity -> (Creature) entity)
                .collect(Collectors.toList());
    }


    @Override
    public void validateCoordinates(Coordinates coordinates) throws InvalidCoordinatesException {
        if (!isCoordinatesValid(coordinates)) {
            throw new InvalidCoordinatesException(Messages.invalidCoordinatesMsg
                    + coordinates.getX() + ":" + coordinates.getY());
        }
    }


    private boolean isCoordinatesValid(Coordinates coordinates) {
        return map.coordinates.contains(coordinates);
    }


    private Set<Coordinates> generateMapCoordinates() {
        Set<Coordinates> result = new HashSet<>();
        for (int y = 0; y < config.getHeight(); y++) {
            for (int x = 0; x < config.getWidth(); x++) {
                result.add(new Coordinates(x, y));
            }
        }

        return result;
    }
}
