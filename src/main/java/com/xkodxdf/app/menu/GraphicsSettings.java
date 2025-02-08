package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.render.EntityNotation;
import com.xkodxdf.app.text_constants.MenuContent;

public class GraphicsSettings extends BaseMenu {

    public GraphicsSettings(SimulationManage simulationManager) {
        super(MenuContent.GraphicSettingsMenu.TITLE, simulationManager);
        setDefaultMenuContent();
    }

    @Override
    protected void setDefaultMenuContent() {
        addItems(
                new Item(
                        MenuContent.GraphicSettingsMenu.ASCII_GRAPHICS,
                        () -> {
                            simulationManager.setEntityNotationPreset(EntityNotation.SYMBOL);
                            informAboutSelectedOption(MenuContent.GraphicSettingsMenu.ASCII_GRAPHICS
                                    + MenuContent.SELECTED);
                            return this;
                        }
                ),
                new Item(
                        MenuContent.GraphicSettingsMenu.EMOJI_GRAPHICS,
                        () -> {
                            simulationManager.setEntityNotationPreset(EntityNotation.EMOJI);
                            informAboutSelectedOption(MenuContent.GraphicSettingsMenu.EMOJI_GRAPHICS
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
