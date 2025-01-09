package com.xkodxdf.app.map;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidMapParametersException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldMap {

    private final int width;
    private final int height;
    private final Map<Coordinates, Entity> map = new HashMap<>();
    private final Set<Coordinates> unoccupiedCoordinates = new HashSet<>();


    public WorldMap() {
        this.width = 32;
        this.height = 16;
        setUnoccupiedCoordinates();
    }

    public WorldMap(int width, int height) throws InvalidMapParametersException {
        int minWidthHeightValue = 8;
        int maxWidthHeightValue = 48;
        if ((width < minWidthHeightValue) || (width > maxWidthHeightValue)
                || (height < minWidthHeightValue) || (height > maxWidthHeightValue)) {
            throw new InvalidMapParametersException(String.format(Messages.invalidMapParametersMsg + "%d - %d",
                    minWidthHeightValue, maxWidthHeightValue));
        }
        this.width = width;
        this.height = height;
        setUnoccupiedCoordinates();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<Coordinates> getUnoccupiedCoordinates() {
        return new HashSet<>(unoccupiedCoordinates);
    }


    public int getSize() {
        return map.size();
    }

    public void setEntity(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        map.put(coordinates, entity);
        unoccupiedCoordinates.remove(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        return map.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        map.remove(coordinates);
        unoccupiedCoordinates.add(coordinates);
    }

    private boolean isCoordinatesValid(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        return ((x >= 0) && (y >= 0) && (x < width) && (y < height));
    }

    private void validateCoordinates(Coordinates coordinates) throws InvalidCoordinatesException {
        if (!isCoordinatesValid(coordinates)) {
            throw new InvalidCoordinatesException(Messages.invalidCoordinatesMsg
                    + coordinates.getX() + ":" + coordinates.getY());
        }
    }

    private void setUnoccupiedCoordinates() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                unoccupiedCoordinates.add(new Coordinates(x, y));
            }
        }
    }
}
