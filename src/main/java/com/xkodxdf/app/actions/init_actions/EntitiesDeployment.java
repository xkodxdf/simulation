package com.xkodxdf.app.actions.init_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.Manager;

public class EntitiesDeployment extends InitActions {

    @Override
    public void process(Manager mapManager) throws InvalidParametersException {
        deployEntities(EntityType.values(), mapManager);
    }


    private void deployEntities(EntityType[] entityTypes, Manager mapManager) throws InvalidParametersException {
        int mapSize = mapManager.getSize();
        for (EntityType entityType : entityTypes) {
            if (entityType.equals(EntityType.CORPSE)) {
                continue;
            }
            int entityMapFillingPercentage = mapManager.getEntityMapFillingPercentage(entityType);
            int squaresAvailableForEntity = (int) (Math.ceil(mapSize / 100D * entityMapFillingPercentage));
            for (int i = 0; i < squaresAvailableForEntity; i++) {
                Coordinates coordinates = mapManager.getRandomUnoccupiedCoordinate();
                deployEntity(coordinates, entityType, mapManager);
            }
        }
    }

    private void deployEntity(Coordinates coordinate, EntityType entityType, Manager mapManager) throws InvalidParametersException {
        switch (entityType) {
            case ROCK:
                mapManager.setEntity(coordinate, new Rock());
                break;
            case TREE:
                mapManager.setEntity(coordinate, new Tree());
                break;
            case GRASS:
                mapManager.setEntity(coordinate, new Grass());
                break;
            case HERBIVORE:
                mapManager.setEntity(coordinate, new Herbivore(mapManager));
                break;
            case PREDATOR:
                mapManager.setEntity(coordinate, new Predator(mapManager));
                break;
            default:
                throw new InvalidParametersException("REPLACE!");

        }
    }
}
