package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.Set;

public class GrassSpawn extends SpawnActions {

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        int baseGrassMapFillingPercentage = mapManager.getConfig().getGrassMapFillingPercentage();
        int currentGrassMapFillingPercentage = getCurrentEntityMapFillingPercentage(Grass.class, mapManager);
        if (currentGrassMapFillingPercentage < baseGrassMapFillingPercentage) {
            int additionalGrass = getAmountGrassToAdd(baseGrassMapFillingPercentage, currentGrassMapFillingPercentage,
                    mapManager);
            spawnGrass(additionalGrass, mapManager);
        }

    }

    private int getAmountGrassToAdd(int baseGrass, int currentGrass, WorldMapManage mapManager) {
        int bonus = getBonusFromAmountOfHerbivores(mapManager);
        currentGrass = Math.max(currentGrass, 1);
        return (baseGrass + bonus) / currentGrass;
    }

    private int getBonusFromAmountOfHerbivores(WorldMapManage mapManager) {
        int baseHerbivoreMapFillingPercentage = mapManager.getConfig().getHerbivoreMapFillingPercentage();
        int currentHerbivoreMapFillingPercentage = getCurrentEntityMapFillingPercentage(Herbivore.class, mapManager);
        return baseHerbivoreMapFillingPercentage - currentHerbivoreMapFillingPercentage;
    }

    private void spawnGrass(int amountToSpawn, WorldMapManage mapManager) throws InvalidCoordinatesException {
        for (int i = 0; i < amountToSpawn; i++) {
            Set<Coordinates> freeCoordinates = mapManager.getFreeCoordinates();
            freeCoordinates.removeAll(mapManager.getBorderFreeCoordinates());
            Coordinates spawnCoordinate = mapManager.getOneRandomFreeCoordinates(freeCoordinates).get();
            mapManager.setEntity(spawnCoordinate, new Grass());
        }
    }
}
