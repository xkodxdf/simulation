package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.exceptions.InvalidCoordinatesException;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseWorldMap<C, V> implements WorldMap<C, V> {

    protected final Set<C> freeCoordinates;
    protected final Set<C> takenCoordinates;


    public BaseWorldMap(int width, int height) {
        this.freeCoordinates = generateMapCoordinates(width, height);
        this.takenCoordinates = new HashSet<>();
    }


    @Override
    public int size() {
        return freeCoordinates.size() + takenCoordinates.size();
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
        clearEntities();
        freeCoordinates.clear();
        takenCoordinates.clear();
        freeCoordinates.addAll(generateMapCoordinates(width, height));
    }

    @Override
    public void validateCoordinates(C coordinates) throws InvalidCoordinatesException {
        if (!freeCoordinates.contains(coordinates) && !takenCoordinates.contains(coordinates)) {
            throw new InvalidCoordinatesException("REPLACE");
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


    protected abstract Set<C> generateMapCoordinates(int width, int height);

    protected abstract void clearEntities();
}
