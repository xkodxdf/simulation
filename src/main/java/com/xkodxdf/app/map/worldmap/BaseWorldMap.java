package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.text_constants.ErrorMessages;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseWorldMap<C, V> implements WorldMap<C, V> {

    protected int width;
    protected int height;
    private final Set<C> freeCoordinates;
    private final Set<C> takenCoordinates;

    public BaseWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.freeCoordinates = generateMapCoordinates();
        this.takenCoordinates = new HashSet<>();
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
    public Set<C> getFreeCoordinatesCopy() {
        return new HashSet<>(freeCoordinates);
    }

    @Override
    public Set<C> getTakenCoordinatesCopy() {
        return new HashSet<>(takenCoordinates);
    }

    @Override
    public void recreateMap(int width, int height) {
        this.width = width;
        this.height = height;
        clearValues();
        freeCoordinates.clear();
        takenCoordinates.clear();
        freeCoordinates.addAll(generateMapCoordinates());
    }

    @Override
    public void validateCoordinates(C coordinates) throws InvalidCoordinatesException {
        if (!freeCoordinates.contains(coordinates) && !takenCoordinates.contains(coordinates)) {
            throw new InvalidCoordinatesException(ErrorMessages.INCORRECT_COORDINATES_ARE_SPECIFIED);
        }
    }

    protected void takeCoordinates(C coordinates) {
        freeCoordinates.remove(coordinates);
        takenCoordinates.add(coordinates);
    }

    protected void releaseCoordinates(C coordinates) {
        takenCoordinates.remove(coordinates);
        freeCoordinates.add(coordinates);
    }

    protected Set<C> generateMapCoordinates() {
        Set<C> result = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.add(createCoordinate(x, y));
            }
        }
        return result;
    }

    protected abstract C createCoordinate(int x, int y);

    protected abstract void clearValues();
}
