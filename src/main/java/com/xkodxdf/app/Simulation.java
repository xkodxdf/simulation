package com.xkodxdf.app;

import com.xkodxdf.app.actions.Action;
import com.xkodxdf.app.actions.init_actions.EntitiesDeployment;
import com.xkodxdf.app.actions.init_actions.InitAction;
import com.xkodxdf.app.actions.turn_actions.*;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.render.Render;
import com.xkodxdf.app.text_constants.SimulationPauseMessages;
import com.xkodxdf.app.worldmap.WorldMapManagement;

import java.util.List;

public class Simulation {

    private volatile boolean isPaused;
    private volatile boolean isRunning;

    private int currentTurn;
    private int amountOfTurns;
    private long turnDelay;
    private final long defaultTurnDelay;
    private final int turnsLimit;
    private final BaseInput<String> stringInput;
    private final Render renderer;
    private final WorldMapManagement mapManager;
    private final Object pauseLock;
    private List<InitAction> initActions;
    private List<TurnAction> turnActions;

    public Simulation(BaseInput<String> stringInput, Render renderer, WorldMapManagement mapManager) {
        this.isPaused = false;
        this.isRunning = true;
        this.currentTurn = 0;
        this.turnsLimit = 1_000_000_000;
        this.amountOfTurns = turnsLimit;
        this.defaultTurnDelay = 1000L;
        this.turnDelay = defaultTurnDelay;
        this.stringInput = stringInput;
        this.renderer = renderer;
        this.mapManager = mapManager;
        this.pauseLock = new Object();
        setUpActions();
    }

    private void setUpActions() {
        initActions = List.of(new EntitiesDeployment());
        turnActions = List.of(
                new CreaturesMoveExecute(),
                new CreaturesStateUpdate(),
                new DeadCreaturesReplacement(),
                new CorpseDecay(),
                new GrassSpawn(),
                new CreatureSpawn()
        );
    }

    public int getTurnsLimit() {
        return turnsLimit;
    }

    public void setAmountOfTurns(int amountOfTurns) {
        this.amountOfTurns = amountOfTurns;
    }

    public void setAmountOfTurnsToDefault() {
        amountOfTurns = turnsLimit;
    }

    public void setTurnDelay(long turnDelay) {
        this.turnDelay = turnDelay;
    }

    public void setTurnDelayToDefault() {
        turnDelay = defaultTurnDelay;
    }

    public void start() throws InterruptedException {
        prepareForFirstTurn();
        runControlThread();
        while (isRunning && (currentTurn < amountOfTurns)) {
            synchronized (pauseLock) {
                while (isPaused) {
                    pauseLock.wait();
                }
            }
            if (isRunning) {
                nextTurn();
                Thread.sleep(turnDelay);
            }
        }
        standardStop();
    }

    private void prepareForFirstTurn() {
        isRunning = true;
        currentTurn = 0;
        for (Action action : initActions) {
            action.process(mapManager);
        }
    }

    private void nextTurn() {
        currentTurn++;
        renderer.renderTurn(mapManager.getEntitiesWithCoordinates());
        for (Action action : turnActions) {
            action.process(mapManager);
        }
    }

    private void standardStop() {
        isRunning = false;
        isPaused = false;
        mapManager.recreateMap();
    }

    private void runControlThread() {
        new Thread(() -> {
            try {
                while (isRunning) {
                    if (stringInput.ready()) {
                        String userInput = stringInput.getVerifiedInput();
                        if (userInput != null) {
                            if (userInput.isEmpty()) {
                                togglePause();
                            } else {
                                prematureStop();
                                break;
                            }
                        }
                    } else {
                        Thread.sleep(50);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (!isPaused) {
            synchronized (pauseLock) {
                pauseLock.notify();
            }
        }
        String pauseMessage = isPaused ? SimulationPauseMessages.PAUSED + currentTurn
                : SimulationPauseMessages.RESUMED;
        renderer.printlnString(pauseMessage);
        renderer.printlnString(SimulationPauseMessages.PROMPT);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void prematureStop() {
        isPaused = false;
        isRunning = false;
        synchronized (pauseLock) {
            pauseLock.notify();
        }
        renderer.printlnString(SimulationPauseMessages.STOPPED);
    }
}
