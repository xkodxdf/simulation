package com.xkodxdf.app.render;

import com.xkodxdf.app.Messages;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.config.Config;

import java.util.Map;
import java.util.Objects;

public class Renderer {

    public void renderMap(Map<Coordinates, Entity> map) {
        StringBuilder row = new StringBuilder();
        System.out.println();
        for (int y = 0; y < Config.getConfig().getHeight(); y++) {
            System.out.println(assembleRow(row, y, map));
            row.setLength(0);
        }
    }

    private String assembleRow(StringBuilder row, int y, Map<Coordinates, Entity> map) {
        for (int x = 0; x < Config.getConfig().getWidth(); x++) {
            Entity entity = map.get(new Coordinates(x, y));
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
