package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.worldmap.WorldMapManagement;

public abstract class SpawnAction extends TurnAction {

    protected int getCurrentEntityMapFillingPercentage(Class<? extends Entity> entity, WorldMapManagement mapManager) {
        int mapSize = mapManager.getSize();
        int currentEntitiesAmountOnMap = mapManager.getEntitiesByType(entity).size();
        double percentDivisor = 100;
        return (int) (currentEntitiesAmountOnMap / (mapSize / percentDivisor));
    }
}
