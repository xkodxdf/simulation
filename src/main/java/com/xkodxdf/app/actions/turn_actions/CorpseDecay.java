package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.map.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.map.exceptions.WorldMapException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManagement;

import java.util.List;
import java.util.stream.Collectors;

public class CorpseDecay extends TurnAction {

    @Override
    public void process(WorldMapManagement mapManager) throws WorldMapException {
        List<Corpse> corpses = getCorpses(mapManager);
        corpses.forEach(Corpse::decay);
        removeRottedCorpses(corpses, mapManager);
    }

    private List<Corpse> getCorpses(WorldMapManagement mapManager) {
        return mapManager.getEntities().stream()
                .filter(entity -> entity instanceof Corpse)
                .map(entity -> (Corpse) entity)
                .collect(Collectors.toList());
    }

    private void removeRottedCorpses(List<Corpse> corpses, WorldMapManagement mapManager)
            throws InvalidCoordinatesException {
        int rotIndex = 0;
        for (Corpse c : corpses) {
            Coordinates corpseCoordinates = mapManager.getEntityCoordinate(c).get();
            if (c.getDecayCounter() <= rotIndex) {
                mapManager.removeEntity(corpseCoordinates);
            }
        }
    }
}
