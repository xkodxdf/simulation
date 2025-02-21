package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.animate.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.creation.CreatureCreator;
import com.xkodxdf.app.map.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.exceptions.WorldMapException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManagement;

import java.util.Set;

public class CreatureSpawn extends SpawnAction implements CreatureCreator {

    private static final double SPAWN_THRESHOLD_FACTOR = 0.5D;

    private boolean spawningHerbsAllowed = false;
    private boolean spawningPredatorsAllowed = false;

    @Override
    public void process(WorldMapManagement mapManager) throws WorldMapException {
        Set<Coordinates> borderFreeCoordinates = mapManager.getBorderFreeCoordinates();
        if (borderFreeCoordinates.isEmpty()) {
            return;
        }
        updateSpawnFlags(mapManager);
        if (spawningHerbsAllowed) {
            spawnCreature(getHerbivore(mapManager), borderFreeCoordinates, mapManager);
        }
        if (spawningPredatorsAllowed) {
            spawnCreature(getPredator(mapManager), borderFreeCoordinates, mapManager);
        }
    }

    private void updateSpawnFlags(WorldMapManagement mapManager) {
        spawningHerbsAllowed = setSpawningFlag(EntityType.HERBIVORE, spawningHerbsAllowed, mapManager);
        spawningPredatorsAllowed = setSpawningFlag(EntityType.PREDATOR, spawningPredatorsAllowed, mapManager);
    }

    private boolean setSpawningFlag(EntityType entityType, boolean spawningFlag, WorldMapManagement mapManager) {
        int baseMapFillingPercentage = mapManager.getConfig().getEntityMapFillingPercentage(entityType);
        Class<? extends Entity> entyClass = entityType.equals(EntityType.HERBIVORE) ?
                Herbivore.class : Predator.class;
        int currentMapFillingPercentage = getCurrentEntityMapFillingPercentage(entyClass, mapManager);
        int percentageToStartSpawn = (int) (baseMapFillingPercentage * SPAWN_THRESHOLD_FACTOR);
        return determineSpawningFlagState(currentMapFillingPercentage, percentageToStartSpawn,
                baseMapFillingPercentage, spawningFlag);
    }

    private boolean determineSpawningFlagState(int currentMapFillingPercentage, int percentageToStartSpawn,
                                               int baseMapFillingPercentage, boolean spawningFlag) {
        if ((!spawningFlag) && (currentMapFillingPercentage <= percentageToStartSpawn)) {
            return true;
        }
        if (currentMapFillingPercentage >= baseMapFillingPercentage) {
            return false;
        }
        return spawningFlag;
    }

    private void spawnCreature(Entity entityToSpawn, Set<Coordinates> freeCoordinates, WorldMapManagement mapManager)
            throws InvalidCoordinatesException {
        Coordinates spawnCoordinates = mapManager.getOneRandomFreeCoordinates(freeCoordinates).get();
        mapManager.setEntity(spawnCoordinates, entityToSpawn);
    }
}
