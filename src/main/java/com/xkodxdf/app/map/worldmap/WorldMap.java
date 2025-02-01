package com.xkodxdf.app.map.worldmap;

import com.xkodxdf.app.exceptions.InvalidCoordinatesException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface WorldMap<C, V> {

    int size();

    int getWidth();

    int getHeight();

    void setValue(C coordinates, V value) throws InvalidCoordinatesException;

    Optional<V> getValue(C coordinates) throws InvalidCoordinatesException;

    void removeValue(C coordinates) throws InvalidCoordinatesException;

    Set<C> getFreeCoordinatesCopy();

    Set<C> getTakenCoordinatesCopy();

    Map<C, V> getValuesWithCoordinatesCopy();

    void recreateMap(int width, int height);

    void validateCoordinates(C coordinates) throws InvalidCoordinatesException;
}
