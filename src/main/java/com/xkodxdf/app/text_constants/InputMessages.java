package com.xkodxdf.app.text_constants;

import com.xkodxdf.app.worldmap.config.Config;

public class InputMessages {

    public static class SettingsMenuInput {

        public static final String AMOUNT_OF_TURNS = "Enter the number of turns you want to stimulate " +
                "(1 - 1 000 000 000 ):";

        public static final String TURN_DELAY = "Enter the delay between moves in milliseconds:";
    }

    public static class WorldSettingsInput {

        public static final String MAP_WIDTH = String.format("Enter map width (%d - %d):",
                Config.MIN_WIDTH, Config.MAX_WIDTH);

        public static final String MAP_HEIGHT = String.format("Enter map height (%d - %d):",
                Config.MIN_HEIGHT, Config.MAX_HEIGHT);

        public static final String ANIMATE_FILLING = String.format("Enter world map filling percentage " +
                        "for herbivores and predators\n(total percentage for creatures is no more than %d)",
                Config.ANIMATE_MAX_FILLING_PERCENTAGE);

        public static final String HERBIVORES_PERCENTAGE = "Herbivores world map filling percentage:";

        public static final String PREDATORS_PERCENTAGE = "Predators world map filling percentage:";

        public static final String ROCKS_PERCENTAGE = "Rocks world map filling percentage:";

        public static final String TREES_PERCENTAGE = "Trees world map filling percentage:";

        public static final String GRASS_PERCENTAGE = "Grass world map filling percentage:";

        public static final String INANIMATE_FILLING = String.format("Enter world map filling percentage" +
                        "for rocks, trees and grass\n(total percentage for inanimate entities is no more than %d)",
                Config.INANIMATE_MAX_FILLING_PERCENTAGE);
    }

    public static final String INVALID_INPUT = "Invalid input. Try again";
}
