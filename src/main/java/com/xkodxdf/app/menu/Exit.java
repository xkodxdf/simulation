package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManagement;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.text_constants.MenuContent;

public class Exit extends BaseMenu {

    public Exit(BaseInput<Integer> input, SimulationManagement simulationManager) {
        super(MenuContent.Exit.STOP_RUNNING, input, simulationManager);
    }

    @Override
    public void display() {
        simulationManager.getRenderer().printlnString(title);
    }

    @Override
    protected void setDefaultMenuContent() {
    }
}
