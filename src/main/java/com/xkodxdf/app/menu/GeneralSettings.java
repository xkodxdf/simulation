package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManagement;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.text_constants.InputMessages;
import com.xkodxdf.app.text_constants.MenuContent;

public class GeneralSettings extends BaseMenu {


    public GeneralSettings(BaseInput<Integer> input, SimulationManagement simulationManager) {
        super(MenuContent.SettingsMenu.TITLE, input, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.SettingsMenu.WORLD_SETTINGS,
                        () -> new WorldSettings(input, simulationManager)
                ),
                new Item(
                        MenuContent.SettingsMenu.GRAPHIC_SETTINGS,
                        () -> new GraphicsSettings(input, simulationManager)
                ),
                new Item(
                        MenuContent.SettingsMenu.ENTITIES_STORAGE,
                        () -> new EntityStorageMenu(input, simulationManager)
                ),
                new Item(
                        MenuContent.SettingsMenu.AMOUNT_OF_TURNS,
                        () -> {
                            setAmountOfTurns();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.SettingsMenu.TURN_DELAY,
                        () -> {
                            setTurnDelay();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.SettingsMenu.RESET_SETTINGS,
                        () -> {
                            simulationManager.resetSettings();
                            return this;
                        }
                ),
                new Item(
                        MenuContent.BACK,
                        () -> new MainMenu(input, simulationManager)
                ),
                new Item(
                        MenuContent.EXIT,
                        this::exit
                )
        );
    }

    private void setAmountOfTurns() {
        int turns = input.getValidInput(
                InputMessages.SettingsMenuInput.AMOUNT_OF_TURNS,
                InputMessages.INVALID_INPUT,
                inputTurns -> (inputTurns > 0) && (inputTurns <= simulationManager.getMaxPossibleTurnsAMount()));
        simulationManager.setAmountOfTurns(turns);
    }

    private void setTurnDelay() {
        int delay = input.getValidInput(
                InputMessages.SettingsMenuInput.TURN_DELAY,
                InputMessages.INVALID_INPUT,
                inputDelay -> true
        );
        simulationManager.setDelayBetweenTurns(delay);
    }
}
