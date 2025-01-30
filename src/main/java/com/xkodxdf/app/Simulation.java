package com.xkodxdf.app;

import com.xkodxdf.app.actions.Actions;
import com.xkodxdf.app.actions.init_actions.EntitiesDeployment;
import com.xkodxdf.app.actions.init_actions.InitActions;
import com.xkodxdf.app.actions.turn_actions.*;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.map.worldmap.WorldHashMap;
import com.xkodxdf.app.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private int turn = 0;
    private final Config config = Config.getConfig();
    private final Renderer renderer = new Renderer();
    private final List<InitActions> initActions;
    private final List<TurnActions> turnActions;
    private final WorldMapManage mapManager = new WorldMapManage(
            new WorldHashMap(config.getWidth(), config.getHeight())
    );

    {
        initActions = new ArrayList<>() {{
            add(new EntitiesDeployment());
        }};

        turnActions = new ArrayList<>() {{
            add(new CreaturesTurnExecute());
            add(new CreaturesStateUpdate());
            add(new DeadCreaturesReplacement());
            add(new CorpseDecay());
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
