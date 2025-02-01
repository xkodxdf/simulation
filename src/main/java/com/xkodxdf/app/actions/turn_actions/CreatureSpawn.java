package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.animate.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.Set;

public class CreatureSpawn extends SpawnActions {

    /*
     *  creatures begin to spawn when the level of their current population
     *  decreases to (initial level * SPAWN_THRESHOLD_FACTOR)
     *  and spawn until their original population level is restored
     */

    private static final double SPAWN_THRESHOLD_FACTOR = 0.5D;

    private boolean spawningHerbsAllowed = false;
    private boolean spawningPredatorsAllowed = false;

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        Set<Coordinates> borderFreeCoordinates = mapManager.getBorderFreeCoordinates();
        if (borderFreeCoordinates.isEmpty()) {
            return;
        }
        updateSpawnFlags(mapManager);
        if (spawningHerbsAllowed) {
            spawnCreature(new Herbivore(mapManager), borderFreeCoordinates, mapManager);
        }
        if (spawningPredatorsAllowed) {
            spawnCreature(new Predator(mapManager), borderFreeCoordinates, mapManager);
        }
    }

    private void updateSpawnFlags(WorldMapManage mapManager) throws InvalidParametersException {
        spawningHerbsAllowed = setSpawningFlag(EntityType.HERBIVORE, spawningHerbsAllowed, mapManager);
        spawningPredatorsAllowed = setSpawningFlag(EntityType.PREDATOR, spawningPredatorsAllowed, mapManager);
    }

    private boolean setSpawningFlag(EntityType entityType, boolean spawningFlag, WorldMapManage mapManager)
            throws InvalidParametersException {
        int baseMapFillingPercentage = mapManager.getConfig().getEntityMapFillingPercentage(entityType);
        Class<? extends Entity> entyClass = entityType.equals(EntityType.HERBIVORE) ?
                Herbivore.class : Predator.class;
        int currentMapFillingPercentage = getCurrentEntityMapFillingPercentage(entyClass, mapManager);
        int percentageToStartSpawn = (int) (baseMapFillingPercentage * SPAWN_THRESHOLD_FACTOR);
        return determineSpawningFlagState(currentMapFillingPercentage, percentageToStartSpawn, baseMapFillingPercentage, spawningFlag);
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

    private void spawnCreature(Entity entityToSpawn, Set<Coordinates> freeCoordinates, WorldMapManage mapManager)
            throws InvalidCoordinatesException {
        Coordinates spawnCoordinates = mapManager.getOneRandomFreeCoordinates(freeCoordinates).get();
        mapManager.setEntity(spawnCoordinates, entityToSpawn);
    }
}
