package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.exceptions.InvalidMapSizeParametersException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.text_constants.InputMessages;
import com.xkodxdf.app.text_constants.MenuContent;

import static com.xkodxdf.app.text_constants.InputMessages.WorldSettingsInput.INANIMATE_FILLING;

public class WorldSettings extends BaseMenu {

    public WorldSettings(BaseInput<Integer> input, SimulationManage simulationManager) {
        super(MenuContent.WorldSettingsMenu.TITLE, input, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.WorldSettingsMenu.MAP_SIZE,
                        () -> {
                            setMapSize();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.WorldSettingsMenu.ANIMATE_FILL_PERCENTAGE,
                        () -> {
                            setAnimateMapFillingPercentage();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.WorldSettingsMenu.INANIMATE_FILL_PERCENTAGE,
                        () -> {
                            setInanimateMapFillingPercentage();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.BACK,
                        () -> new GeneralSettings(input, simulationManager)
                ),
                new Item(
                        MenuContent.TO_MAIN_MENU,
                        () -> new MainMenu(input, simulationManager)
                ),
                new Item(
                        MenuContent.EXIT,
                        this::exit
                )
        );
    }

    private void setMapSize() throws InvalidMapSizeParametersException {
        int width = getValidWorldSettingsParameter(
                InputMessages.WorldSettingsInput.MAP_WIDTH,
                Config.MIN_WIDTH,
                Config.MAX_WIDTH);
        int height = getValidWorldSettingsParameter(
                InputMessages.WorldSettingsInput.MAP_HEIGHT,
                Config.MIN_HEIGHT,
                Config.MAX_HEIGHT
        );
        simulationManager.setMapSize(width, height);
    }

    private void setAnimateMapFillingPercentage() {
        boolean isSet = false;
        while (!isSet) {
            System.out.println(InputMessages.WorldSettingsInput.ANIMATE_FILLING);
            int minPercentage = 0;
            int herbivoresPercentage = getValidWorldSettingsParameter(
                    InputMessages.WorldSettingsInput.HERBIVORES_PERCENTAGE,
                    minPercentage,
                    Config.ANIMATE_MAX_FILLING_PERCENTAGE
            );
            int predatorsPercentage = getValidWorldSettingsParameter(
                    InputMessages.WorldSettingsInput.PREDATORS_PERCENTAGE,
                    minPercentage,
                    Config.ANIMATE_MAX_FILLING_PERCENTAGE
            );
            isSet = tryToSetAnimateMapFillingPercentages(herbivoresPercentage, predatorsPercentage);
            if (!isSet) {
                System.out.println(InputMessages.INVALID_INPUT);
            }
        }
    }

    private void setInanimateMapFillingPercentage() {
        boolean isSet = false;
        while (!isSet) {
            System.out.println(INANIMATE_FILLING);
            int minPercentage = 0;
            int rocksPercentage = getValidWorldSettingsParameter(
                    InputMessages.WorldSettingsInput.ROCKS_PERCENTAGE,
                    minPercentage,
                    Config.INANIMATE_MAX_FILLING_PERCENTAGE
            );
            int treesPercentage = getValidWorldSettingsParameter(
                    InputMessages.WorldSettingsInput.TREES_PERCENTAGE,
                    minPercentage,
                    Config.INANIMATE_MAX_FILLING_PERCENTAGE
            );
            int grassPercentage = getValidWorldSettingsParameter(
                    InputMessages.WorldSettingsInput.GRASS_PERCENTAGE,
                    minPercentage,
                    Config.INANIMATE_MAX_FILLING_PERCENTAGE
            );
            isSet = tryToSetInanimateMapFillingPercentages(rocksPercentage, treesPercentage, grassPercentage);
            if (!isSet) {
                System.out.println(InputMessages.INVALID_INPUT);
            }
        }
    }

    private int getValidWorldSettingsParameter(String prompt, int minValueIncl, int maxValueIncl) {
        return input.getInput(
                prompt,
                InputMessages.INVALID_INPUT,
                inputPercentage -> (inputPercentage >= minValueIncl) && (inputPercentage <= maxValueIncl)
        );
    }

    private boolean tryToSetAnimateMapFillingPercentages(int herbivoresPercentage, int predatorsPercentage) {
        simulationManager.setAnimateEntitiesFillingPercentagesToZero();
        try {
            simulationManager.setHerbivoreMapFillingPercentage(herbivoresPercentage);
            simulationManager.setPredatorMapFillingPercentage(predatorsPercentage);
        } catch (InvalidParametersException ignore) {
            return false;
        }
        return true;
    }

    private boolean tryToSetInanimateMapFillingPercentages(int rocksPercentage, int treesPercentage,
                                                           int grassPercentage) {
        simulationManager.setInanimateEntitiesFillingPercentageToZero();
        try {
            simulationManager.setRockMapFillingPercentage(rocksPercentage);
            simulationManager.setTreeMapFillingPercentage(treesPercentage);
            simulationManager.setGrassMapFillingPercentage(grassPercentage);
        } catch (InvalidParametersException ignore) {
            return false;
        }
        return true;
    }
}
