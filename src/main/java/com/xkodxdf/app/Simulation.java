package com.xkodxdf.app;

import com.xkodxdf.app.actions.Actions;
import com.xkodxdf.app.actions.init_actions.EntitiesDeployment;
import com.xkodxdf.app.actions.init_actions.InitActions;
import com.xkodxdf.app.actions.turn_actions.*;
import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.render.Render;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private int turn = 0;
    private final Render renderer;
    private final List<InitActions> initActions;
    private final List<TurnActions> turnActions;
    private final WorldMapManage mapManager;

    public Simulation(Render renderer, WorldMapManage mapManager) {
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

    public void start() throws InvalidParametersException, InterruptedException {
        for (Actions action : initActions) {
            action.process(mapManager);
        }
        while (true) {
            turn++;
            renderer.renderMap(mapManager.getEntitiesWithCoordinates());
            nextTurn();
            Thread.sleep(1500L);
        }

    }

    public void nextTurn() throws InvalidParametersException {
        for (Actions action : turnActions) {
            action.process(mapManager);
        }
    }
}
