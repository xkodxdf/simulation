package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManagement;
import com.xkodxdf.app.pathfinder.PathFinder;
import com.xkodxdf.app.pathfinder.PathFinderBFS;

public class CreaturesMoveExecute extends TurnAction {

    private final PathFinder<Coordinates> pathFinder = new PathFinderBFS();

    @Override
    public void process(WorldMapManagement mapManager) throws InvalidParametersException {
        for (Creature creature : mapManager.getEntitiesByType(Creature.class)) {
            creature.makeMove(pathFinder);
        }
    }
}
