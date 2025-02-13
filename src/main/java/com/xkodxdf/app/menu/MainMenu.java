package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.text_constants.MenuContent;

public class MainMenu extends BaseMenu {

    public MainMenu(BaseInput<Integer> input, SimulationManage simulationManager) {
        super(MenuContent.MainMenu.TITLE, input, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.MainMenu.START_SIMULATION,
                        () -> {
                            simulationManager.start();
                            return new MainMenu(input, simulationManager);
                        }
                ),
                new Item(
                        MenuContent.MainMenu.SETTINGS,
                        () -> new GeneralSettings(input, simulationManager)
                ),
                new Item(
                        MenuContent.MainMenu.INFO,
                        () -> {
                            simulationManager.getRenderer().printlnString(MenuContent.MainMenu.INFO_CONTENT);
                            return this;
                        }
                ),
                new Item(
                        MenuContent.EXIT,
                        this::exit
                )
        );
    }
}
