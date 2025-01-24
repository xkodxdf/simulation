package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManager;

public class CreaturesTurnExecute extends TurnActions {

    @Override
    public void process(WorldMapManager mapManager) throws InvalidParametersException {
        for (Creature creature : mapManager.getCreatures()) {
            creature.makeMove();
        }
    }
}
