package com.xkodxdf.app.text_constants;

import com.xkodxdf.app.render.EntityNotation;

public final class MenuContent {

    private MenuContent() {
    }

    public static final String PROMPT = "Enter number from 1 to ";
    public static final String SELECTED = " selected";
    public static final String BACK = "<-back";
    public static final String TO_MAIN_MENU = "<<main menu";
    public static final String EXIT = "<!exit";

    public final static class MainMenu {

        private MainMenu() {
        }

        public static final String TITLE = "### Main menu ###";
        public static final String START_SIMULATION = "[Start simulation]";
        public static final String SETTINGS = "Settings->";
        public static final String INFO = "-Info";
        public static final String INFO_CONTENT = String.format(
                "###The Simulation Project###\n" +
                        "The essence of the project is a step-by-step simulation of a 2D world inhabited by " +
                        "herbivores and predators.\nIn addition to creatures, the world contains resources (grasses) that " +
                        "herbivores feed on,\nand static objects that cannot be interacted with - they just take up space.\n" +
                        "The 2D world is an NxM matrix, each creature or object occupies an entire cell," +
                        "\nand the presence of several objects/creatures in a cell is unacceptable.\n\n" +
                        "For ASCII graphics:\n%s - rock\n%s - tree\n%s - grass\n%s - herbivore\n%s - predator\n" +
                        "%s - corpse\n",
                EntityNotation.SymbolNotation.ROCK, EntityNotation.SymbolNotation.TREE,
                EntityNotation.SymbolNotation.GRASS, EntityNotation.SymbolNotation.HERBIVORE,
                EntityNotation.SymbolNotation.PREDATOR, EntityNotation.SymbolNotation.CORPSE);
    }

    public final static class SettingsMenu {

        private SettingsMenu() {
        }

        public static final String TITLE = "### Settings ###";
        public static final String WORLD_SETTINGS = "World settings->";
        public static final String GRAPHIC_SETTINGS = "Graphic settings->";
        public static final String ENTITIES_STORAGE = "Entities storage type->";
        public static final String AMOUNT_OF_TURNS = "-How many turns to simulate";
        public static final String TURN_DELAY = "-Delay between turns";
        public static final String RESET_SETTINGS = "-Reset settings";
    }

    public final static class WorldSettingsMenu {

        private WorldSettingsMenu() {
        }

        public static final String TITLE = "### World settings ###";
        public static final String MAP_SIZE = "-Map size";
        public static final String ANIMATE_FILL_PERCENTAGE = "-Creatures world filling percentage";
        public static final String INANIMATE_FILL_PERCENTAGE = "-Inanimate world filling percentage";
    }

    public final static class GraphicSettingsMenu {

        private GraphicSettingsMenu() {
        }

        public static final String TITLE = "### Graphics ###";
        public static final String ASCII_GRAPHICS = "-ASCII";
        public static final String EMOJI_GRAPHICS = "-EMOJI";
    }

    public final static class EntityStorageMenu {

        private EntityStorageMenu() {
        }

        public static final String TITLE = "### Entity storage ###";
        public static final String HASHMAP = "-HashMap storage";
        public static final String ARRAY = "-Array storage";
    }

    public final static class Exit {

        private Exit() {
        }

        public static final String STOP_RUNNING = "The simulation has completed its work. Good luck.";
    }
}
