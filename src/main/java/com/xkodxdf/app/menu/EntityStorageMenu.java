package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.text_constants.MenuContent;

public class EntityStorageMenu extends BaseMenu {

    public EntityStorageMenu(SimulationManage simulationManager) {
        super(MenuContent.EntityStorageMenu.TITLE, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.EntityStorageMenu.HASHMAP,
                        () -> {
                            simulationManager.setHashMapEntityStorage();
                            informAboutSelectedOption(MenuContent.EntityStorageMenu.HASHMAP
                                    + MenuContent.SELECTED);
                            return this;
                        }
                ),
                new Item(
                        MenuContent.EntityStorageMenu.ARRAY,
                        () -> {
                            simulationManager.setArrayEntityStorage();
                            informAboutSelectedOption(MenuContent.EntityStorageMenu.ARRAY
                                    + MenuContent.SELECTED);
                            return this;
                        }
                ),
                new Item(
                        MenuContent.BACK,
                        () -> new GeneralSettings(simulationManager)
                ),
                new Item(
                        MenuContent.TO_MAIN_MENU,
                        () -> new MainMenu(simulationManager)
                ),
                new Item(
                        MenuContent.EXIT,
                        this::exit
                )
        );
    }
}
