package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.creation.CorpseCreator;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.List;
import java.util.stream.Collectors;

public class DeadCreaturesReplacement extends TurnAction implements CorpseCreator {

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        for (Creature creature : getDeadCreatures(mapManager)) {
            Coordinates creatureCoordinates = mapManager.getEntityCoordinate(creature).get();
            mapManager.setEntity(creatureCoordinates, getCorpse());
        }
    }

    private List<Creature> getDeadCreatures(WorldMapManage mapManager) {
        return mapManager.getEntitiesByType(Creature.class).stream()
                .filter(Creature::isDead)
                .collect(Collectors.toList());
    }
}
