package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.text_constants.MenuContent;

public class MainMenu extends BaseMenu {

    public MainMenu(SimulationManage simulationManager) {
        super(MenuContent.MainMenu.TITLE, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.MainMenu.START_SIMULATION,
                        () -> {
                            simulationManager.start();
                            return new MainMenu(simulationManager);
                        }
                ),
                new Item(
                        MenuContent.MainMenu.SETTINGS,
                        () -> new GeneralSettings(simulationManager)
                ),
                new Item(
                        MenuContent.EXIT,
                        this::exit
                )
        );
    }
}
