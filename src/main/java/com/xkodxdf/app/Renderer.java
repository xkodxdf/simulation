package com.xkodxdf.app;

import com.xkodxdf.app.entities.EntityNotation;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMap;

import java.util.Map;

public class Renderer {

    private final WorldMap worldMap;


    public Renderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }


    public void renderMap() {
        Map<com.xkodxdf.app.map.Coordinates, Entity> map = worldMap.getMap();
        StringBuilder line = new StringBuilder();
        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                Entity entity = map.get(new Coordinates(x, y));
                switch (entity.getClass().getSimpleName()) {
                    case "EmptySquare":
                        line.append(EntityNotation.EMPTY_SQUARE.getNotation());
                        break;
                    case "Grass":
                        line.append(EntityNotation.GRASS.getNotation());
                        break;
                    case "Rock":
                        line.append(EntityNotation.ROCK.getNotation());
                        break;
                    case "Tree":
                        line.append(EntityNotation.TREE.getNotation());
                        break;
                    case "Herbivore":
                        line.append(EntityNotation.HERBIVORE.getNotation());
                        break;
                    case "Predator":
                        line.append(EntityNotation.PREDATOR.getNotation());
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
            System.out.println(line);
            line.setLength(0);
        }
    }
}
