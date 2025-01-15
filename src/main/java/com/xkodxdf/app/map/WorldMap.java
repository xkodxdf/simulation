package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.base.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldMap {

    protected final Set<Coordinates> coordinates;
    protected final Set<Coordinates> occupiedCoordinates;
    protected final Set<Coordinates> unoccupiedCoordinates;
    protected final Map<Coordinates, Entity> entities = new HashMap<>();


    protected WorldMap(Set<Coordinates> coordinates) {
        this.coordinates = coordinates;
        this.occupiedCoordinates = new HashSet<>();
        this.unoccupiedCoordinates = new HashSet<>(coordinates);
    }


    public void setEntity(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
        occupyCoordinates(coordinates);

    }

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public Entity removeEntity(Coordinates coordinates) {
        Entity removedEntity = entities.remove(coordinates);
        releaseCoordinates(coordinates);

        return removedEntity;
    }


    private void occupyCoordinates(Coordinates coordinates) {
        unoccupiedCoordinates.remove(coordinates);
        occupiedCoordinates.add(coordinates);
    }

    private void releaseCoordinates(Coordinates coordinates) {
        occupiedCoordinates.remove(coordinates);
        unoccupiedCoordinates.add(coordinates);
    }
}
