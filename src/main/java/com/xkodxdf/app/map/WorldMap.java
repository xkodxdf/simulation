package com.xkodxdf.app.map;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidMapParametersException;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {

    private final int width;
    private final int height;
    private final Map<Coordinates, Entity> map = new HashMap<>();


    public WorldMap() {
        this.width = 32;
        this.height = 16;
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
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void initMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map.put(new Coordinates(x, y), null);
            }
        }
    }

    public int getSize() {
        return map.size();
    }

    public void setEntity(Coordinates coordinates, Entity entity) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        map.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        return map.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) throws InvalidCoordinatesException {
        validateCoordinates(coordinates);
        map.remove(coordinates);
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
}
