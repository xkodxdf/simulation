package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.text_constants.ErrorMessages;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class BaseWorldMap<V> implements WorldMap<V> {

    protected int width;
    protected int height;
    private Set<Coordinates> freeCoordinates;

    public BaseWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.freeCoordinates = generateMapCoordinates();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int size() {
        return width * height;
    }

    @Override
    public Set<Coordinates> getFreeCoordinatesCopy() {
        return new HashSet<>(freeCoordinates);
    }

    @Override
    public void recreateMap(int width, int height) {
        this.width = width;
        this.height = height;
        clearValues();
        freeCoordinates = generateMapCoordinates();
    }

    @Override
    public void validateCoordinates(Coordinates coordinates) throws InvalidCoordinatesException {
        if (Objects.isNull(coordinates) || !isCoordinatesWithinWorldMap(coordinates)) {
            throw new InvalidCoordinatesException(ErrorMessages.INCORRECT_COORDINATES_ARE_SPECIFIED);
        }
    }

    protected void takeCoordinates(Coordinates coordinates) {
        freeCoordinates.remove(coordinates);
    }

    protected void releaseCoordinates(Coordinates coordinates) {
        freeCoordinates.add(coordinates);
    }

    protected Set<Coordinates> generateMapCoordinates() {
        Set<Coordinates> result = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.add(new Coordinates(x, y));
            }
        }
        return result;
    }

    private boolean isCoordinatesWithinWorldMap(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return (x >= 0 && y >= 0) && (x < width && y < height);
    }

    protected abstract void clearValues();
}
