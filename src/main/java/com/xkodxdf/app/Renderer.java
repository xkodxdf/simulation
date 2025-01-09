package com.xkodxdf.app;

import com.xkodxdf.app.entities.EntityNotation;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMap;

import java.util.Objects;

public class Renderer {

    private final WorldMap worldMap;


    public Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }


    public void renderMap() throws InvalidCoordinatesException {
        StringBuilder row = new StringBuilder();
        System.out.println();
        for (int y = 0; y < worldMap.getHeight(); y++) {
            System.out.println(assembleRow(row, y));
            row.setLength(0);
        }
    }

    private String assembleRow(StringBuilder row, int y) throws InvalidCoordinatesException {
        for (int x = 0; x < worldMap.getWidth(); x++) {
            Entity entity = worldMap.getEntity(new Coordinates(x, y));
            row.append(getEntityNotation(entity)).append(" ");
        }
        return row.toString();
    }

    private String getEntityNotation(Entity entity) {
        if (Objects.isNull(entity)) {
            return EntityNotation.EMPTY_SQUARE.getNotation();
        }
        if (entity instanceof Grass) {
            return EntityNotation.GRASS.getNotation();
        }
        if (entity instanceof Rock) {
            return EntityNotation.ROCK.getNotation();
        }
        if (entity instanceof Tree) {
            return EntityNotation.TREE.getNotation();
        }
        if (entity instanceof Herbivore) {
            return EntityNotation.HERBIVORE.getNotation();
        }
        if (entity instanceof Predator) {
            return EntityNotation.PREDATOR.getNotation();
        }
        throw new IllegalStateException(Messages.noNotationForEntity + entity.getClass().getSimpleName());
    }
}
