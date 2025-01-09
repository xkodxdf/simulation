package com.xkodxdf.app;

import com.xkodxdf.app.entities.EntityNotation;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.animated.*;
import com.xkodxdf.app.entities.inanimate.*;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMap;

import java.util.Objects;
import java.util.stream.IntStream;

public class Renderer {

    private final WorldMap worldMap;


    public Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }


    public void renderMap() throws InvalidCoordinatesException {
        StringBuilder row = new StringBuilder();
        System.out.print("\n   +");
        IntStream.range(0, worldMap.getWidth()).forEach(num -> System.out.printf(" %2d ", num));
        System.out.println();
        for (int y = 0; y < worldMap.getHeight(); y++) {
            System.out.printf("%2d ", y);
            System.out.println(assembleRow(row, y));
            row.setLength(0);
        }
    }

    private String assembleRow(StringBuilder row, int y) throws InvalidCoordinatesException {
        for (int x = 0; x < worldMap.getWidth(); x++) {
            Entity entity = worldMap.getEntity(new Coordinates(x, y));
            row.append(" . ").append(getEntityNotation(entity));
        }
        row.append(" .");

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
