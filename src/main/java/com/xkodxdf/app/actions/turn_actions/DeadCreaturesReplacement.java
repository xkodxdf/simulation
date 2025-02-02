package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.List;
import java.util.stream.Collectors;

public class DeadCreaturesReplacement extends TurnActions {

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        for (Creature creature : getDeadCreatures(mapManager)) {
            Coordinates creatureCoordinates = mapManager.getEntityCoordinate(creature).get();
            mapManager.setEntity(creatureCoordinates, new Corpse());
        }
    }


    private List<Creature> getDeadCreatures(WorldMapManage mapManager) {
        return mapManager.getCreatures().stream()
                .filter(creature -> creature.getHealthPoints() <= 0)
                .collect(Collectors.toList());
    }
}
