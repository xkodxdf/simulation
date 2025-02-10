package com.xkodxdf.app;

import com.xkodxdf.app.actions.Action;
import com.xkodxdf.app.actions.init_actions.EntitiesDeployment;
import com.xkodxdf.app.actions.init_actions.InitAction;
import com.xkodxdf.app.actions.turn_actions.*;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.render.Render;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private int turn;
    private int amountOfTurns;
    private long turnDelay;
    private final int turnsLimit;
    private final Render renderer;
    private final List<InitAction> initActions;
    private final List<TurnAction> turnActions;
    private final WorldMapManage mapManager;

    public Simulation(Render renderer, WorldMapManage mapManager) {
        this.turn = 0;
        this.turnsLimit = 5;
        this.amountOfTurns = turnsLimit;
        this.turnDelay = 1000L;
        this.renderer = renderer;
        this.mapManager = mapManager;
    }

    {
        initActions = new ArrayList<>() {{
            add(new EntitiesDeployment());
        }};

        turnActions = new ArrayList<>() {{
            add(new CreaturesTurnExecute());
            add(new CreaturesStateUpdate());
            add(new DeadCreaturesReplacement());
            add(new CorpseDecay());
            add(new GrassSpawn());
            add(new CreatureSpawn());
        }};
    }

    public int getTurnsLimit() {
        return turnsLimit;
    }

    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    public void setTurnDelay(long turnDelay) {
        this.turnDelay = turnDelay;
    }

    public void start() throws InvalidParametersException, InterruptedException {
        for (Action action : initActions) {
            action.process(mapManager);
        }
        while (turn < amountOfTurns) {
            turn++;
            renderer.clearScreen();
            renderer.renderMap(mapManager.getEntitiesWithCoordinates());
            nextTurn();
            Thread.sleep(turnDelay);
        }
        resetSimulation();
    }


    private void nextTurn() throws InvalidParametersException {
        for (Action action : turnActions) {
            action.process(mapManager);
        }
    }

    private void resetSimulation() {
        turn = 0;
        mapManager.recreateMap();
    }
}
