package com.xkodxdf.app.render;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.Manager;

import java.util.Objects;

public class Renderer {

    private final Manager mapManager;


    public Renderer(Manager mapManager) {
        this.mapManager = mapManager;
    }


    public void renderMap() throws InvalidParametersException {
        StringBuilder row = new StringBuilder();
        System.out.println();
        for (int y = 0; y < mapManager.getHeight(); y++) {
            System.out.println(assembleRow(row, y));
            row.setLength(0);
        }
    }

    private String assembleRow(StringBuilder row, int y) throws InvalidParametersException {
        for (int x = 0; x < mapManager.getWidth(); x++) {
            Entity entity = mapManager.getEntity(new Coordinates(x, y));
            row.append(getEntityNotation(entity)).append(" ");
        }
        return row.toString();
    }

    private String getEntityNotation(Entity entity) {
        if (Objects.isNull(entity)) {
            return Sprite.EMPTY.getNotation();
        }
        if (entity instanceof Grass) {
            return Sprite.GRASS.getNotation();
        }
        if (entity instanceof Rock) {
            return Sprite.ROCK.getNotation();
        }
        if (entity instanceof Tree) {
            return Sprite.TREE.getNotation();
        }
        if (entity instanceof Herbivore) {
            return Sprite.HERBIVORE.getNotation();
        }
        if (entity instanceof Predator) {
            return Sprite.PREDATOR.getNotation();
        }
        if (entity instanceof Corpse) {
            return Sprite.CORPSE.getNotation();
        }
        throw new IllegalStateException(Messages.noNotationForEntity + entity.getClass().getSimpleName());
    }
}
