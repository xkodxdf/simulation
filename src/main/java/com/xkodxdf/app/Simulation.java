package com.xkodxdf.app;

import com.xkodxdf.app.actions.Action;
import com.xkodxdf.app.actions.init_actions.EntitiesDeployment;
import com.xkodxdf.app.actions.init_actions.InitAction;
import com.xkodxdf.app.actions.turn_actions.*;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.render.Render;
import com.xkodxdf.app.text_constants.SimulationPauseMessages;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private volatile boolean isPaused;
    private volatile boolean isRunning;

    private int currentTurn;
    private int amountOfTurns;
    private long turnDelay;
    private final int turnsLimit;
    BaseInput<String> stringInput;
    private final Render renderer;
    private final List<InitAction> initActions;
    private final List<TurnAction> turnActions;
    private final WorldMapManage mapManager;

    public Simulation(BaseInput<String> stringInput, Render renderer, WorldMapManage mapManager) {
        this.isPaused = false;
        this.isRunning = true;
        this.currentTurn = 0;
        this.defaultAmountOfTurns = 100;
        this.amountOfTurns = defaultAmountOfTurns;
        this.defaultTurnDelay = 1000L;
        this.turnDelay = defaultTurnDelay;
        this.turnsLimit = 1_000_000_000;
        this.stringInput = stringInput;
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
