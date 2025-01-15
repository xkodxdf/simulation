package com.xkodxdf.app.map.config;

import com.xkodxdf.app.exceptions.InvalidFillingPercentageException;
import com.xkodxdf.app.exceptions.InvalidMapSizeParametersException;

import java.util.stream.IntStream;

public class ConfigValidator {

    private ConfigValidator() {
    }


    protected static void validateMapSizeParameters(Config config) throws InvalidMapSizeParametersException {
        if ((config.getHeight() < Config.MIN_HEIGHT || config.getHeight() > Config.MAX_HEIGHT)
                || (config.getWidth() < Config.MIN_WIDTH || config.getWidth() > Config.MAX_WIDTH)) {

            throw new InvalidMapSizeParametersException("REPLACE!!!");
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

        if (hasNegativeValues(inanimateValues) || hasNegativeValues(animateValues)) {
            throw new InvalidFillingPercentageException("REPLACE!!!");
        }

        if (hasExceededLimit(inanimateValues, Config.INANIMATE_MAX_FILLING_PERCENTAGE)
                || hasExceededLimit(animateValues, Config.ANIMATE_MAX_FILLING_PERCENTAGE)) {
            throw new InvalidFillingPercentageException("REPLACE!!!");
        }
    }


    private static boolean hasNegativeValues(int... values) {
        return IntStream.of(values).anyMatch(value -> value < 0);
    }

    private static boolean hasExceededLimit(int[] values, int limit) {
        return IntStream.of(values).sum() > limit;
    }
}
