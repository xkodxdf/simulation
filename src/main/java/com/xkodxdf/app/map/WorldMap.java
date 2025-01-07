package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.EmptySquare;

import java.util.HashMap;
import java.util.Map;

/*
    Карта, содержит в себе коллекцию для хранения существ и их расположения.
*/
public class WorldMap {

    private final int width;
    private final int height;
    private final Map<Coordinates, Entity> map = new HashMap<>();


    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Coordinates, Entity> getMap() {
        return map;
    }


    public void initMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map.put(new Coordinates(x, y), new EmptySquare());
            }
        }
    }
}
