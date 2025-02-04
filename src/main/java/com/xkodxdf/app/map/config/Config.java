package com.xkodxdf.app.map.config;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.exceptions.InvalidFillingPercentageException;
import com.xkodxdf.app.exceptions.InvalidMapSizeParametersException;
import com.xkodxdf.app.exceptions.InvalidParametersException;

public class Config {

    private static Config config;

    protected static final int MIN_WIDTH = 8;
    protected static final int MIN_HEIGHT = 8;
    protected static final int MAX_WIDTH = 64;
    protected static final int MAX_HEIGHT = 48;
    protected static final int ANIMATE_MAX_FILLING_PERCENTAGE = 20;
    protected static final int INANIMATE_MAX_FILLING_PERCENTAGE = 60;


    private int width = 8;
    private int height = 8;
    private int rocksMapFillingPercentage = 20;
    private int treesMapFillingPercentage = 20;
    private int grassMapFillingPercentage = 20;
    private int herbivoreMapFillingPercentage = 10;
    private int predatorMapFillingPercentage = 10;


    private Config() {
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) throws InvalidMapSizeParametersException {
        this.width = width;
        ConfigValidator.validateMapSizeParameters(this);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) throws InvalidMapSizeParametersException {
        this.height = height;
        ConfigValidator.validateMapSizeParameters(this);
    }

    public int getRocksMapFillingPercentage() {
        return rocksMapFillingPercentage;
    }

    public void setRocksMapFillingPercentage(int rocksMapFillingPercentage) throws InvalidFillingPercentageException {
        this.rocksMapFillingPercentage = rocksMapFillingPercentage;
        ConfigValidator.validateMapFillingPercentages(this);
    }

    public int getTreesMapFillingPercentage() {
        return treesMapFillingPercentage;
    }

    public void setTreesMapFillingPercentage(int treesMapFillingPercentage) throws InvalidFillingPercentageException {
        this.treesMapFillingPercentage = treesMapFillingPercentage;
        ConfigValidator.validateMapFillingPercentages(this);
    }

    public int getGrassMapFillingPercentage() {
        return grassMapFillingPercentage;
    }

    public void setGrassMapFillingPercentage(int grassMapFillingPercentage) throws InvalidFillingPercentageException {
        this.grassMapFillingPercentage = grassMapFillingPercentage;
        ConfigValidator.validateMapFillingPercentages(this);
    }

    public int getHerbivoreMapFillingPercentage() {
        return herbivoreMapFillingPercentage;
    }

    public void setHerbivoreMapFillingPercentage(int herbivoreMapFillingPercentage) throws InvalidFillingPercentageException {
        this.herbivoreMapFillingPercentage = herbivoreMapFillingPercentage;
        ConfigValidator.validateMapFillingPercentages(this);
    }

    public int getPredatorMapFillingPercentage() {
        return predatorMapFillingPercentage;
    }

    public void setPredatorMapFillingPercentage(int predatorMapFillingPercentage) throws InvalidFillingPercentageException {
        this.predatorMapFillingPercentage = predatorMapFillingPercentage;
        ConfigValidator.validateMapFillingPercentages(this);
    }

    public int getEntityMapFillingPercentage(EntityType entityType) throws InvalidParametersException {
        switch (entityType) {
            case ROCK:
                return rocksMapFillingPercentage;
            case TREE:
                return treesMapFillingPercentage;
            case CORPSE:
                return 0;
            case GRASS:
                return grassMapFillingPercentage;
            case HERBIVORE:
                return herbivoreMapFillingPercentage;
            case PREDATOR:
                return predatorMapFillingPercentage;
            default:
                throw new InvalidParametersException(Messages.INVALID_ENTITY_TYPE);

        }
    }
}
