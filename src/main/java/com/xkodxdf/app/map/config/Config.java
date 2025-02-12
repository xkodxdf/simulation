package com.xkodxdf.app.map.config;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.exceptions.InvalidFillingPercentageException;
import com.xkodxdf.app.exceptions.InvalidMapSizeParametersException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.text_constants.ErrorMessages;

public class Config {

    private static Config config;

    public static final int MIN_WIDTH = 8;
    public static final int MAX_WIDTH = 80;
    public static final int MIN_HEIGHT = 8;
    public static final int MAX_HEIGHT = 80;
    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = 12;
    public static final int ANIMATE_MAX_FILLING_PERCENTAGE = 20;
    public static final int INANIMATE_MAX_FILLING_PERCENTAGE = 60;

    private int width = 32;
    private int height = 16;
    private int rocksMapFillingPercentage = 6;
    private int treesMapFillingPercentage = 8;
    private int grassMapFillingPercentage = 24;
    private int herbivoreMapFillingPercentage = 6;
    private int predatorMapFillingPercentage = 4;

    private Config() {
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void resetEntitiesFillingPercentagesToDefault() {
        rocksMapFillingPercentage = 8;
        treesMapFillingPercentage = 12;
        grassMapFillingPercentage = 36;
        herbivoreMapFillingPercentage = 3;
        predatorMapFillingPercentage = 2;
    }

    public void setInanimateEntitiesFillingPercentageToZero() {
        rocksMapFillingPercentage = 0;
        treesMapFillingPercentage = 0;
        grassMapFillingPercentage = 0;
    }

    public void setAnimateEntitiesFillingPercentagesToZero() {
        herbivoreMapFillingPercentage = 0;
        predatorMapFillingPercentage = 0;
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
                throw new InvalidParametersException(ErrorMessages.INVALID_ENTITY_TYPE);

        }
    }
}
