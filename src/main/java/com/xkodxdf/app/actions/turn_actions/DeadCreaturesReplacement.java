package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.creation.CorpseCreator;
import com.xkodxdf.app.map.exceptions.WorldMapException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManagement;

import java.util.List;
import java.util.stream.Collectors;

public class DeadCreaturesReplacement extends TurnAction implements CorpseCreator {

    @Override
    public void process(WorldMapManagement mapManager) throws WorldMapException {
        for (Creature creature : getDeadCreatures(mapManager)) {
            Coordinates creatureCoordinates = mapManager.getEntityCoordinate(creature).get();
            mapManager.setEntity(creatureCoordinates, getCorpse());
        }
    }

    private List<Creature> getDeadCreatures(WorldMapManagement mapManager) {
        return mapManager.getEntitiesByType(Creature.class).stream()
                .filter(Creature::isDead)
                .collect(Collectors.toList());
    }
}
