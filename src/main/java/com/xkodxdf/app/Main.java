package com.xkodxdf.app;

import com.xkodxdf.app.menu.Exit;
import com.xkodxdf.app.menu.MainMenu;
import com.xkodxdf.app.menu.Menu;

public class Main {

    public static void main(String[] args) throws Exception {
        SimulationManage simulationManage = new SimulationManage();
        Menu currentMenu = new MainMenu(simulationManage);
        while (!currentMenu.getClass().equals(Exit.class)) {
            currentMenu.display();
            currentMenu = currentMenu.selectItem();
        }
        currentMenu.display();
        ((Exit) currentMenu).closeScanner();
    }
}
