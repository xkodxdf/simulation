package com.xkodxdf.app;

import com.xkodxdf.app.input.IntegerInput;
import com.xkodxdf.app.input.StringInput;
import com.xkodxdf.app.menu.Exit;
import com.xkodxdf.app.menu.MainMenu;
import com.xkodxdf.app.menu.Menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SimulationManage simulationManage = new SimulationManage(new StringInput(reader));
        Menu currentMenu = new MainMenu(new IntegerInput(reader), simulationManage);
        while (!currentMenu.getClass().equals(Exit.class)) {
            currentMenu.display();
            currentMenu = currentMenu.selectItem();
        }
        currentMenu.display();
        reader.close();
    }
}
