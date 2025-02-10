package com.xkodxdf.app.text_constants;

public class MenuContent {

    public static class MainMenu {

        public static final String TITLE = "### Main menu ###";
        public static final String START_SIMULATION = "[Start simulation]";
        public static final String SETTINGS = "Settings->";
    }

    public static class SettingsMenu {

        public static final String TITLE = "### Settings ###";
        public static final String WORLD_SETTINGS = "World settings->";
        public static final String GRAPHIC_SETTINGS = "Graphic settings->";
        public static final String ENTITIES_STORAGE = "Entities storage type->";
        public static final String AMOUNT_OF_TURNS = "-How many turns to simulate";
        public static final String TURN_DELAY = "-Delay between turns";
    }

    public static class WorldSettingsMenu {

        public static final String TITLE = "### World settings ###";
        public static final String MAP_SIZE = "-Map size";
        public static final String ANIMATE_FILL_PERCENTAGE = "-Creatures world filling percentage";
        public static final String INANIMATE_FILL_PERCENTAGE = "-Inanimate world filling percentage";
    }

    public static class GraphicSettingsMenu {

        public static final String TITLE = "### Graphics ###";
        public static final String ASCII_GRAPHICS = "-ASCII";
        public static final String EMOJI_GRAPHICS = "-EMOJI";
    }

    public static class EntityStorageMenu {

        public static final String TITLE = "### Entity storage ###";
        public static final String HASHMAP = "-HashMap storage";
        public static final String ARRAY = "-Array storage";
    }

    public static class Exit {

        public static final String STOP_RUNNING = "The simulation has completed its work. Good luck.";
    }

    public static final String PROMPT_MSG = "Enter number from 1 to ";
    public static final String SELECTED = " selected";
    public static final String BACK = "<-back";
    public static final String TO_MAIN_MENU = "<<main menu";
    public static final String EXIT = "<!exit";
}
