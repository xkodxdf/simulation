package com.xkodxdf.app.actions.init_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManager;
import com.xkodxdf.app.map.config.Config;

public class EntitiesDeployment extends InitActions {

    @Override
    public void process(WorldMapManager mapManager) throws InvalidParametersException {
        deployEntities(EntityType.values(), mapManager);
    }


    private void deployEntities(EntityType[] entityTypes, WorldMapManager mapManager)
            throws InvalidParametersException {
        int mapSize = mapManager.getSize();
        for (EntityType entityType : entityTypes) {
            if (entityType.equals(EntityType.CORPSE)) {
                continue;
            }
            int entityMapFillingPercentage = Config.getConfig().getEntityMapFillingPercentage(entityType);
            int squaresAvailableForEntity = calculateSquaresAvailable(mapSize, entityMapFillingPercentage);
            for (int i = 0; i < squaresAvailableForEntity; i++) {
                Coordinates coordinates = mapManager.getOneRandomFreeCoordinates();
                deployEntity(coordinates, entityType, mapManager);
            }
        }
    }

    private void deployEntity(Coordinates coordinate, EntityType entityType, WorldMapManager mapManager)
            throws InvalidParametersException {
        switch (entityType) {
            case ROCK:
                mapManager.setEntity(coordinate, Rock.getInstance());
                break;
            case TREE:
                mapManager.setEntity(coordinate, Tree.getInstance());
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


    private int calculateSquaresAvailable(int mapSize, int fillingPercentage) {
        return (int) (Math.ceil(mapSize / 100D * fillingPercentage));
    }
}
