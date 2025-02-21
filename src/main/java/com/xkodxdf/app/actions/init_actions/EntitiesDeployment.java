package com.xkodxdf.app.actions.init_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.creation.EntityCreator;
import com.xkodxdf.app.text_constants.ErrorMessages;
import com.xkodxdf.app.worldmap.Coordinates;
import com.xkodxdf.app.worldmap.WorldMapManagement;
import com.xkodxdf.app.worldmap.config.Config;

import java.util.Optional;

public class EntitiesDeployment extends InitAction implements EntityCreator {

    @Override
    public void process(WorldMapManagement mapManager) {
        deployEntities(EntityType.values(), mapManager);
    }

    private void deployEntities(EntityType[] entityTypes, WorldMapManagement mapManager) {
        int mapSize = mapManager.getSize();
        for (EntityType entityType : entityTypes) {
            if (entityType.equals(EntityType.CORPSE)) {
                continue;
            }
            int entityMapFillingPercentage = Config.getInstance().getEntityMapFillingPercentage(entityType);
            int squaresAvailableForEntity = calculateSquaresAvailable(mapSize, entityMapFillingPercentage);
            for (int i = 0; i < squaresAvailableForEntity; i++) {
                Optional<Coordinates> optionalCoordinates = mapManager.getOneRandomFreeCoordinates();
                optionalCoordinates.ifPresent(coordinates -> deployEntity(coordinates, entityType, mapManager));
            }
        }
    }

    private int calculateSquaresAvailable(int mapSize, int fillingPercentage) {
        double percentDivisor = 100D;
        return (int) (Math.ceil(mapSize / percentDivisor * fillingPercentage));
    }

    private void deployEntity(Coordinates coordinate, EntityType entityType, WorldMapManagement mapManager) {
        switch (entityType) {
            case ROCK:
                mapManager.setEntity(coordinate, getRock());
                break;
            case TREE:
                mapManager.setEntity(coordinate, getTree());
                break;
            case GRASS:
                mapManager.setEntity(coordinate, getGrass());
                break;
            case HERBIVORE:
                mapManager.setEntity(coordinate, getHerbivore(mapManager));
                break;
            case PREDATOR:
                mapManager.setEntity(coordinate, getPredator(mapManager));
                break;
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_ENTITY_TYPE + entityType.name());

        }
    }
}
