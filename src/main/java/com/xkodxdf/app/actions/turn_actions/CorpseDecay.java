package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.List;
import java.util.stream.Collectors;

public class CorpseDecay extends TurnAction {

    @Override
    public void process(WorldMapManage mapManager) throws InvalidParametersException {
        List<Corpse> corpses = getCorpses(mapManager);
        corpses.forEach(Corpse::decay);
        removeRottedCorpses(corpses, mapManager);
    }

    private List<Corpse> getCorpses(WorldMapManage mapManager) {
        return mapManager.getEntities().stream()
                .filter(entity -> entity instanceof Corpse)
                .map(entity -> (Corpse) entity)
                .collect(Collectors.toList());
    }

    private void removeRottedCorpses(List<Corpse> corpses, WorldMapManage mapManager)
            throws InvalidCoordinatesException {
        int corpseRemovingThreshold = 0;
        for (Corpse c : corpses) {
            Coordinates corpseCoordinates = mapManager.getEntityCoordinate(c).get();
            if (c.getDecayCounter() <= corpseRemovingThreshold) {
                mapManager.removeEntity(corpseCoordinates);
            }
        }
    }
}
