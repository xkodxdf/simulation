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

public class WorldMapManagement {

    private final Config config;
    private WorldMap<Entity> worldMap;

    public WorldMapManagement(Config config) throws InvalidParametersException {
        ConfigValidator.validateConfig(config);
        this.config = config;
        selectWorldHashMapAsWorldMap();
    }

    public Config getConfig() {
        return config;
    }

    public void selectWorldHashMapAsWorldMap() {
        worldMap = new WorldHashMap(config.getWidth(), config.getHeight());
    }

    public void selectWorldArrayMapAsWorldMap() {
        worldMap = new WorldArrayMap(config.getWidth(), config.getHeight());
    }

    public int getSize() {
        return worldMap.size();
    }

    public void recreateMap() {
        worldMap.recreateMap(config.getWidth(), config.getHeight());
    }

    public void resetMapSizeToDefault() {
        config.resetMapSizeToDefault();
        worldMap.recreateMap(config.getWidth(), config.getHeight());
    }

    public void setEntity(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        worldMap.setValue(coordinates, entity);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        return worldMap.getValue(coordinates);
    }

    public void removeEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        worldMap.removeValue(coordinates);
    }

    public List<Entity> getEntities() {
        return Arrays.asList(worldMap.getValuesWithCoordinatesCopy().values().toArray(new Entity[0]));
    }

    public Map<Coordinates, Entity> getEntitiesWithCoordinates() {
        return worldMap.getValuesWithCoordinatesCopy();
    }

    public List<Entity> getAroundEntities(Set<Coordinates> aroundCoordinates) throws InvalidCoordinatesException {
        List<Entity> result = new ArrayList<>();
        for (Coordinates coordinate : aroundCoordinates) {
            Optional<Entity> entity = worldMap.getValue(coordinate);
            entity.ifPresent(result::add);
        }
        return result;
    }

    public <T> List<T> getEntitiesByType(Class<T> type) {
        return worldMap.getValuesWithCoordinatesCopy().values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public Set<Coordinates> getFreeCoordinates() {
        return worldMap.getFreeCoordinatesCopy();
    }

    public Set<Coordinates> getBorderCoordinates() {
        Set<Coordinates> borderCoordinates = new HashSet<>();
        int firstRowY = 0;
        int lastRowY = worldMap.getHeight() - 1;
        for (int x = 0; x < worldMap.getWidth(); x++) {
            borderCoordinates.add(new Coordinates(x, firstRowY));
            borderCoordinates.add(new Coordinates(x, lastRowY));
        }
        int firstColumn = 0;
        int lastColumn = worldMap.getWidth() - 1;
        for (int y = 0; y < worldMap.getHeight(); y++) {
            borderCoordinates.add(new Coordinates(firstColumn, y));
            borderCoordinates.add(new Coordinates(lastColumn, y));
        }
        return borderCoordinates;
    }

    public Set<Coordinates> getBorderFreeCoordinates() {
        Set<Coordinates> borderFreeCoordinates = new HashSet<>(getBorderCoordinates());
        borderFreeCoordinates.removeAll(worldMap.getValuesWithCoordinatesCopy().keySet());
        return borderFreeCoordinates;
    }

    public Optional<Coordinates> getOneRandomFreeCoordinates() {
        if (worldMap.getFreeCoordinatesCopy().isEmpty()) {
            return Optional.empty();
        }
        Coordinates[] coordinates = worldMap.getFreeCoordinatesCopy().toArray(new Coordinates[0]);
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
        result.retainAll(worldMap.getFreeCoordinatesCopy());
        return result;
    }

    public Optional<Coordinates> getEntityCoordinate(Entity entity) {
        Optional<Coordinates> result = Optional.empty();
        for (Map.Entry<Coordinates, Entity> entityEntry : worldMap.getValuesWithCoordinatesCopy().entrySet()) {
            if (entityEntry.getValue().equals(entity)) {
                result = Optional.ofNullable(entityEntry.getKey());
            }
        }
        return result;
    }

    private boolean isCoordinatesValid(Coordinates coordinates) {
        try {
            worldMap.validateCoordinates(coordinates);
        } catch (InvalidCoordinatesException e) {
            return false;
        }
        return true;
    }
}
