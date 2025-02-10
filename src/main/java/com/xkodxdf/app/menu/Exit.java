package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.text_constants.MenuContent;

public class Exit extends BaseMenu {

    public Exit(SimulationManage simulationManager) {
        super(MenuContent.Exit.STOP_RUNNING, simulationManager);
    }

    @Override
    public void display() {
        System.out.println(title);
    }

    @Override
    protected void setDefaultMenuContent() {
    }

    public void closeScanner() {
        scn.close();
    }
}
