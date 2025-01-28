package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.WorldMapManager;
import com.xkodxdf.app.pathfinder.PathFinder;
import com.xkodxdf.app.pathfinder.PathFinderAStar;

public class CreaturesTurnExecute extends TurnActions {

    private final PathFinder<Coordinates> pathFinder = new PathFinderAStar();

    @Override
    public void process(WorldMapManager mapManager) throws InvalidParametersException {
        for (Creature creature : mapManager.getCreatures()) {
            creature.makeMove(pathFinder);
        }
    }
}
