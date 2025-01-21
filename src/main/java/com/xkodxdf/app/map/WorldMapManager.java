package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.map.worldmap.WorldMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WorldMapManager {

    private final WorldMap<Coordinates, Entity> map;


    public WorldMapManager(Config config, WorldMap<Coordinates, Entity> worldMap) {
        this.map = worldMap;
    }


    public int getSize() {
        return map.size();
    }

    public void recreateMap(int width, int height) {
        map.recreateMap(width, height);
    }

    public void setEntity(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        map.setValue(coordinates, entity);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        return map.getValue(coordinates);
    }

    public void removeEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        map.removeValue(coordinates);
    }

    public List<Entity> getAroundEntities(Set<Coordinates> aroundCoordinates) throws InvalidCoordinatesException {
        List<Entity> result = new ArrayList<>();
        for (Coordinates coordinate : aroundCoordinates) {
            Optional<Entity> entity = map.getValue(coordinate);
            entity.ifPresent(result::add);
        }

        return result;
    }

    public List<Creature> getCreatures() {
        return map.getValuesWithCoordinatesCopy().values().stream()
                .filter(entity -> entity instanceof Creature).map(entity -> (Creature) entity)
                .collect(Collectors.toList());
    }

    public Set<Map.Entry<Coordinates, Creature>> getCreaturesWithCoordinates() {
        return map.getValuesWithCoordinatesCopy().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Creature)
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), (Creature) entry.getValue()))
                .collect(Collectors.toSet());
    }

    public Set<Map.Entry<Coordinates, Predator>> getPredatorsWithCoordinates() {
        return map.getValuesWithCoordinatesCopy().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Predator)
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), (Predator) entry.getValue()))
                .collect(Collectors.toSet());
    }

    public Set<Coordinates> getFreeCoordinates() {
        return map.getFreeCoordinatesCopy();
    }

    public Coordinates getOneRandomFreeCoordinates() {
        Coordinates[] coordinates = map.getFreeCoordinatesCopy().toArray(new Coordinates[0]);
        int randomBound = coordinates.length;

        return coordinates[ThreadLocalRandom.current().nextInt(randomBound)];
    }

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

    public Set<Coordinates> getAroundFreeCoordinates(Coordinates target, int radius) {
        Set<Coordinates> result = getAroundCoordinates(target, radius);
        result.retainAll(map.getFreeCoordinatesCopy());

        return result;
    }

    public Optional<Coordinates> getEntityCoordinate(Entity entity) {
        Optional<Coordinates> result = Optional.empty();
        for (Map.Entry<Coordinates, Entity> entityEntry : map.getValuesWithCoordinatesCopy().entrySet()) {
            if (entityEntry.getValue().equals(entity)) {
                result = Optional.ofNullable(entityEntry.getKey());
            }
        }

        return result;
    }


    private boolean isCoordinatesValid(Coordinates coordinates) {
        try {
            map.validateCoordinates(coordinates);
        } catch (InvalidCoordinatesException e) {
            return false;
        }

        return true;
    }
}
