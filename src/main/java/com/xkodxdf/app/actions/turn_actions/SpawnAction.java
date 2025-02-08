package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.map.WorldMapManage;

public abstract class SpawnAction extends TurnAction {

    protected int getCurrentEntityMapFillingPercentage(Class<? extends Entity> entity, WorldMapManage mapManager) {
        int mapSize = mapManager.getSize();
        int currentEntitiesAmountOnMap = mapManager.getEntitiesByType(entity).size();
        return (int) (currentEntitiesAmountOnMap / (mapSize / 100D));
    }
}
