package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Manager;

public class CreaturesTurnExecutor extends TurnActions {

    @Override
    public void process(Manager mapManager) throws InvalidParametersException {
        for (Creature creature : mapManager.getCreatures()) {
            creature.makeMove();
        }
    }
}
