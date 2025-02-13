package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.map.config.ConfigValidator;
import com.xkodxdf.app.map.worldmap.WorldArrayMap;
import com.xkodxdf.app.map.worldmap.WorldHashMap;
import com.xkodxdf.app.map.worldmap.WorldMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WorldMapManage {

    private final Config config;
    private WorldMap<Coordinates, Entity> map;

    public WorldMapManage(Config config) throws InvalidParametersException {
        ConfigValidator.validateConfig(config);
        this.config = config;
        selectWorldHashMapAsWorldMap();
    }

    public Config getConfig() {
        return config;
    }

    public void selectWorldHashMapAsWorldMap() {
        map = new WorldHashMap(config.getWidth(), config.getHeight());
    }

    public void selectWorldArrayMapAsWorldMap() {
        map = new WorldArrayMap(config.getWidth(), config.getHeight());
    }

    public int getSize() {
        return map.size();
    }

    public void recreateMap() {
        map.recreateMap(config.getWidth(), config.getHeight());
    }

    public void resetMapSizeToDefault() {
        config.resetMapSizeToDefault();
        map.recreateMap(config.getWidth(), config.getHeight());
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

    public List<Entity> getEntities() {
        return Arrays.asList(map.getValuesWithCoordinatesCopy().values().toArray(new Entity[0]));
    }

    public Map<Coordinates, Entity> getEntitiesWithCoordinates() {
        return map.getValuesWithCoordinatesCopy();
    }

    public List<Entity> getAroundEntities(Set<Coordinates> aroundCoordinates) throws InvalidCoordinatesException {
        List<Entity> result = new ArrayList<>();
        for (Coordinates coordinate : aroundCoordinates) {
            Optional<Entity> entity = map.getValue(coordinate);
            entity.ifPresent(result::add);
        }
        return result;
    }

    public <T> List<T> getEntitiesByType(Class<T> type) {
        return map.getValuesWithCoordinatesCopy().values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public Set<Coordinates> getFreeCoordinates() {
        return map.getFreeCoordinatesCopy();
    }

    public Set<Coordinates> getBorderCoordinates() {
        Set<Coordinates> borderCoordinates = new HashSet<>();
        int firstRowY = 0;
        int lastRowY = map.getHeight() - 1;
        for (int x = 0; x < map.getWidth(); x++) {
            borderCoordinates.add(new Coordinates(x, firstRowY));
            borderCoordinates.add(new Coordinates(x, lastRowY));
        }
        int firstColumn = 0;
        int lastColumn = map.getWidth() - 1;
        for (int y = 0; y < map.getHeight(); y++) {
            borderCoordinates.add(new Coordinates(firstColumn, y));
            borderCoordinates.add(new Coordinates(lastColumn, y));
        }
        return borderCoordinates;
    }

    public Set<Coordinates> getBorderFreeCoordinates() {
        Set<Coordinates> borderFreeCoordinates = new HashSet<>(getBorderCoordinates());
        borderFreeCoordinates.removeAll(map.getTakenCoordinatesCopy());
        return borderFreeCoordinates;
    }

    public Optional<Coordinates> getOneRandomFreeCoordinates() {
        if (map.getFreeCoordinatesCopy().isEmpty()) {
            return Optional.empty();
        }
        Coordinates[] coordinates = map.getFreeCoordinatesCopy().toArray(new Coordinates[0]);
        int randomBound = coordinates.length;
        return Optional.of(coordinates[ThreadLocalRandom.current().nextInt(randomBound)]);
    }

    public Optional<Coordinates> getOneRandomFreeCoordinates(Set<Coordinates> freeCoordinates) {
        if (freeCoordinates.isEmpty()) {
            return Optional.empty();
        }
        Coordinates[] coordinates = freeCoordinates.toArray(new Coordinates[0]);
        int randomBound = coordinates.length;
        return Optional.of(coordinates[ThreadLocalRandom.current().nextInt(randomBound)]);
    }


    public Set<Coordinates> getAroundCoordinates(Coordinates target, int radius) {
        int x = target.getX();
        int y = target.getY();
        int targetRow = 0;
        int targetCol = 0;
        Set<Coordinates> result = new HashSet<>();
        for (int row = -radius; row <= radius; row++) {
            for (int col = -radius; col <= radius; col++) {
                if (!(row == targetRow && col == targetCol)) {
                    Coordinates coordinate = new Coordinates(x + col, y + row);
                    if (isCoordinatesValid(coordinate)) {
                        result.add(coordinate);
                    }
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
