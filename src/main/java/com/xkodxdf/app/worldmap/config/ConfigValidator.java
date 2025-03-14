package com.xkodxdf.app.worldmap.config;

import com.xkodxdf.app.worldmap.exceptions.InvalidFillingPercentageException;
import com.xkodxdf.app.worldmap.exceptions.InvalidMapSizeParametersException;
import com.xkodxdf.app.worldmap.exceptions.WorldMapException;
import com.xkodxdf.app.text_constants.ErrorMessages;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ConfigValidator {

    private ConfigValidator() {
    }

    public static void validateConfig(Config config) throws WorldMapException {
        validateMapSizeParameters(config);
        validateMapFillingPercentages(config);
    }

    protected static void validateMapSizeParameters(Config config) throws InvalidMapSizeParametersException {
        if ((config.getHeight() < Config.MIN_HEIGHT || config.getHeight() > Config.MAX_HEIGHT)
                || (config.getWidth() < Config.MIN_WIDTH || config.getWidth() > Config.MAX_WIDTH)) {
            throw new InvalidMapSizeParametersException(ErrorMessages.INVALID_MAP_SIZE_PARAMETERS + config.getWidth()
                    + " " + config.getHeight());
        }
    }

    protected static void validateMapFillingPercentages(Config config) throws InvalidFillingPercentageException {
        int[] inanimateValues = {
                config.getRocksMapFillingPercentage(),
                config.getTreesMapFillingPercentage(),
                config.getGrassMapFillingPercentage()
        };
        int[] animateValues = {
                config.getHerbivoreMapFillingPercentage(),
                config.getPredatorMapFillingPercentage()
        };
        if ((hasNegativeValues(animateValues) || hasNegativeValues(inanimateValues))
                || (hasExceededLimit(animateValues, Config.ANIMATE_MAX_FILLING_PERCENTAGE)
                || hasExceededLimit(inanimateValues, Config.INANIMATE_MAX_FILLING_PERCENTAGE))) {
            throw new InvalidFillingPercentageException(ErrorMessages.ENTITIES_FILLING_PERCENTAGE_ERR
                    + ErrorMessages.INANIMATE_VALUE + Arrays.toString(inanimateValues)
                    + ErrorMessages.ANIMATE_VALUE + Arrays.toString(animateValues));
        }
    }

    private static boolean hasNegativeValues(int... values) {
        return IntStream.of(values).anyMatch(value -> value < 0);
    }

    private static boolean hasExceededLimit(int[] values, int limit) {
        return IntStream.of(values).sum() > limit;
    }
}
