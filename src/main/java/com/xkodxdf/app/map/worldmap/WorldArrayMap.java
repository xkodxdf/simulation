package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldArrayMap extends BaseWorldMap<Coordinates, Entity> {

    private int rows;
    private int columns;
    private Entity[][] entities;

    public WorldArrayMap(int width, int height) {
        super(width, height);
        rows = height;
        columns = width;
        initMap();
    }

    @Override
    public void setValue(Coordinates coordinates, Entity value) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        entities[coordinates.getY()][coordinates.getX()] = value;
        takeCoordinates(coordinates);
    }

    @Override
    public Optional<Entity> getValue(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        return Optional.ofNullable(entities[coordinates.getY()][coordinates.getX()]);
    }

    @Override
    public void removeValue(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        entities[coordinates.getY()][coordinates.getX()] = null;
        releaseCoordinates(coordinates);
    }

    @Override
    public Map<Coordinates, Entity> getValuesWithCoordinatesCopy() {
        Map<Coordinates, Entity> result = new HashMap<>();
        for (int row = 0; row < entities.length; row++) {
            for (int col = 0; col < entities[row].length; col++) {
                if (entities[row][col] != null) {
                    result.put(new Coordinates(col, row), entities[row][col]);
                }
            }

        }
        return result;
    }

    @Override
    protected Coordinates createCoordinate(int x, int y) {
        return new Coordinates(x, y);
    }

    @Override
    protected void clearValues() {
        initMap();
    }

    private void initMap() {
        rows = height;
        columns = width;
        entities = new Entity[rows][columns];
    }
}
