package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.creation.GrassCreator;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.worldmap.Coordinates;
import com.xkodxdf.app.worldmap.WorldMapManagement;

import java.util.Optional;
import java.util.Set;

public class GrassSpawn extends SpawnAction implements GrassCreator {

    @Override
    public void process(WorldMapManagement mapManager) {
        int baseGrassMapFillingPercentage = mapManager.getConfig().getGrassMapFillingPercentage();
        int currentGrassMapFillingPercentage = getCurrentEntityMapFillingPercentage(Grass.class, mapManager);
        if (currentGrassMapFillingPercentage < baseGrassMapFillingPercentage) {
            int additionalGrass = getAmountOfGrassToAdd(baseGrassMapFillingPercentage, currentGrassMapFillingPercentage,
                    mapManager);
            spawnGrass(additionalGrass, mapManager);
        }
    }

    private int getAmountOfGrassToAdd(int baseGrass, int currentGrass, WorldMapManagement mapManager) {
        int bonus = getBonusFromAmountOfHerbivores(mapManager);
        currentGrass = Math.max(currentGrass, 1);
        return (baseGrass + bonus) / currentGrass;
    }

    private int getBonusFromAmountOfHerbivores(WorldMapManagement mapManager) {
        int baseHerbivoreMapFillingPercentage = mapManager.getConfig().getHerbivoreMapFillingPercentage();
        int currentHerbivoreMapFillingPercentage = getCurrentEntityMapFillingPercentage(Herbivore.class, mapManager);
        return baseHerbivoreMapFillingPercentage - currentHerbivoreMapFillingPercentage;
    }

    private void spawnGrass(int amountToSpawn, WorldMapManagement mapManager) {
        for (int i = 0; i < amountToSpawn; i++) {
            Set<Coordinates> freeCoordinates = mapManager.getFreeCoordinates();
            freeCoordinates.removeAll(mapManager.getBorderFreeCoordinates());
            Optional<Coordinates> spawnCoordinate = mapManager.getOneRandomFreeCoordinates(freeCoordinates);
            spawnCoordinate.ifPresent(coordinates -> mapManager.setEntity(coordinates, getGrass()));
        }
    }
}
