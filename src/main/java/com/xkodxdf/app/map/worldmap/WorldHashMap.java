package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;

import java.util.*;

public class WorldHashMap extends BaseWorldMap<Coordinates, Entity> {

    private final Map<Coordinates, Entity> entities;


    public WorldHashMap(int width, int height) {
        super(width, height);
        entities = new HashMap<>();
    }


    @Override
    public void setValue(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        entities.put(coordinates, entity);
        takeCoordinates(coordinates);
    }

    @Override
    public Optional<Entity> getValue(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);

        return Optional.ofNullable(entities.get(coordinates));
    }

    @Override
    public void removeValue(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        entities.remove(coordinates);
        releaseCoordinates(coordinates);
    }

    @Override
    public Map<Coordinates, Entity> getValuesWithCoordinates() {
        return new HashMap<>(entities);
    }

    @Override
    protected Set<Coordinates> generateMapCoordinates(int width, int height) {
        Set<Coordinates> result = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.add(new Coordinates(x, y));
            }
        }

        return result;
    }

    @Override
    protected void clearEntities() {
        entities.clear();
    }
}
