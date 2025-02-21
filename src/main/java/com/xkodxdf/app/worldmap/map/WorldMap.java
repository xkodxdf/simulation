package com.xkodxdf.app.worldmap.map;

import com.xkodxdf.app.worldmap.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.worldmap.Coordinates;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface WorldMap<V> {

    int size();

    int getWidth();

    int getHeight();

    void setValue(Coordinates coordinates, V value) throws InvalidCoordinatesException;

    Optional<V> getValue(Coordinates coordinates) throws InvalidCoordinatesException;

    void removeValue(Coordinates coordinates) throws InvalidCoordinatesException;

    Set<Coordinates> getFreeCoordinatesCopy();

    Map<Coordinates, V> getValuesWithCoordinatesCopy();

    void recreateMap(int width, int height);

    void validateCoordinates(Coordinates coordinates) throws InvalidCoordinatesException;
}
