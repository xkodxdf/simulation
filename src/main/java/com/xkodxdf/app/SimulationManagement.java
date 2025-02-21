package com.xkodxdf.app;

import com.xkodxdf.app.worldmap.exceptions.InvalidFillingPercentageException;
import com.xkodxdf.app.worldmap.exceptions.InvalidMapSizeParametersException;
import com.xkodxdf.app.worldmap.exceptions.WorldMapException;
import com.xkodxdf.app.worldmap.WorldMapManagement;
import com.xkodxdf.app.worldmap.config.Config;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.render.EntityNotation;
import com.xkodxdf.app.render.Render;

public class SimulationManagement {

    private final Config conf;
    private final Render renderer;
    private final WorldMapManagement mapManager;
    private final Simulation simulation;

    public SimulationManagement(BaseInput<String> stringInput) throws WorldMapException {
        this.conf = Config.getInstance();
        this.renderer = new Render();
        this.mapManager = new WorldMapManagement(conf);
        this.simulation = new Simulation(stringInput, renderer, mapManager);
    }

    public Render getRenderer() {
        return renderer;
    }

    public void start() throws WorldMapException, InterruptedException {
        simulation.start();
    }

    public int getMaxPossibleTurnsAMount() {
        return simulation.getTurnsLimit();
    }

    public void setAmountOfTurns(int totalTurns) {
        simulation.setAmountOfTurns(totalTurns);
    }

    public void setDelayBetweenTurns(long delay) {
        simulation.setTurnDelay(delay);
    }

    public void setMapSize(int width, int height) throws InvalidMapSizeParametersException {
        conf.setWidth(width);
        conf.setHeight(height);
        mapManager.recreateMap();
    }

    public void setHashMapEntityStorage() {
        mapManager.selectWorldHashMapAsWorldMap();
    }

    public void setArrayEntityStorage() {
        mapManager.selectWorldArrayMapAsWorldMap();
    }

    public void resetSettings() {
        conf.resetEntitiesFillingPercentagesToDefault();
        simulation.setAmountOfTurnsToDefault();
        simulation.setTurnDelayToDefault();
        mapManager.resetMapSizeToDefault();
        setEntityNotationPreset(EntityNotation.SYMBOL);
        setHashMapEntityStorage();
    }

    public void setAnimateEntitiesFillingPercentagesToZero() {
        conf.setAnimateEntitiesFillingPercentagesToZero();
    }

    public void setInanimateEntitiesFillingPercentageToZero() {
        conf.setInanimateEntitiesFillingPercentageToZero();
    }

    public void setRockMapFillingPercentage(int percentage) throws InvalidFillingPercentageException {
        conf.setRocksMapFillingPercentage(percentage);
    }

    public void setTreeMapFillingPercentage(int percentage) throws InvalidFillingPercentageException {
        conf.setTreesMapFillingPercentage(percentage);
    }

    public void setGrassMapFillingPercentage(int percentage) throws InvalidFillingPercentageException {
        conf.setGrassMapFillingPercentage(percentage);
    }

    public void setHerbivoreMapFillingPercentage(int percentage) throws InvalidFillingPercentageException {
        conf.setHerbivoreMapFillingPercentage(percentage);
    }

    public void setPredatorMapFillingPercentage(int percentage) throws InvalidFillingPercentageException {
        conf.setPredatorMapFillingPercentage(percentage);
    }

    public void setEntityNotationPreset(EntityNotation notation) {
        renderer.setEntityNotation(notation);
    }
}
