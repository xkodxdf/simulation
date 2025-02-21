package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.worldmap.exceptions.WorldMapException;
import com.xkodxdf.app.worldmap.Coordinates;
import com.xkodxdf.app.worldmap.WorldMapManagement;
import com.xkodxdf.app.pathfinder.PathFinder;
import com.xkodxdf.app.pathfinder.PathFinderBFS;

public class CreaturesMoveExecute extends TurnAction {

    private final PathFinder<Coordinates> pathFinder = new PathFinderBFS();

    @Override
    public void process(WorldMapManagement mapManager) throws WorldMapException {
        for (Creature creature : mapManager.getEntitiesByType(Creature.class)) {
            creature.makeMove(pathFinder);
        }
    }
}
