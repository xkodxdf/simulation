package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;

import java.util.*;

public class WorldHashMap extends BaseWorldMap<Entity> {

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
    public Map<Coordinates, Entity> getValuesWithCoordinatesCopy() {
        return new HashMap<>(entities);
    }

    @Override
    protected void clearValues() {
        entities.clear();
    }
}
