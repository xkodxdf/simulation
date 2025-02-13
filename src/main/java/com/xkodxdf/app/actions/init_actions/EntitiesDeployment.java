package com.xkodxdf.app.actions.init_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.creation.EntityCreator;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.text_constants.ErrorMessages;

import java.util.Optional;

public class EntitiesDeployment extends InitAction implements EntityCreator {

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        deployEntities(EntityType.values(), mapManager);
    }

    private void deployEntities(EntityType[] entityTypes, WorldMapManage mapManager)
            throws InvalidParametersException {
        int mapSize = mapManager.getSize();
        for (EntityType entityType : entityTypes) {
            if (entityType.equals(EntityType.CORPSE)) {
                continue;
            }
            int entityMapFillingPercentage = Config.getConfig().getEntityMapFillingPercentage(entityType);
            int squaresAvailableForEntity = calculateSquaresAvailable(mapSize, entityMapFillingPercentage);
            for (int i = 0; i < squaresAvailableForEntity; i++) {
                Optional<Coordinates> optionalCoordinates = mapManager.getOneRandomFreeCoordinates();
                if (optionalCoordinates.isPresent()) {
                    deployEntity(optionalCoordinates.get(), entityType, mapManager);
                }
            }
        }
    }

    private int calculateSquaresAvailable(int mapSize, int fillingPercentage) {
        double percentDivisor = 100D;
        return (int) (Math.ceil(mapSize / percentDivisor * fillingPercentage));
    }

    private void deployEntity(Coordinates coordinate, EntityType entityType, WorldMapManage mapManager)
            throws InvalidParametersException {
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
                throw new InvalidParametersException(ErrorMessages.INVALID_ENTITY_TYPE + entityType.name());

        }
    }
}
