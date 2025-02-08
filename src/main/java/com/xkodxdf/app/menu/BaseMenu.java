package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.menu.input.BaseInput;
import com.xkodxdf.app.menu.input.IntegerInput;
import com.xkodxdf.app.text_constants.InputMessages;
import com.xkodxdf.app.text_constants.MenuContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class BaseMenu implements Menu {

    protected final String title;
    protected final List<Item> items;
    protected final BaseInput<Integer> input;
    protected final SimulationManage simulationManager;
    public final Scanner scn = new Scanner(System.in);

    public BaseMenu(String title, SimulationManage simulationManager) {
        this.title = title;
        this.items = new ArrayList<>();
        this.input = new IntegerInput(scn);
        this.simulationManager = simulationManager;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
    }

    @Override
    public void display() {
        System.out.println(title);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf(" %d. %s\n", i + 1, items.get(i).text);
        }
        System.out.println(MenuContent.PROMPT_MSG + items.size());
    }

    @Override
    public Menu selectItem() throws Exception {
        int item = input.getInput(null, InputMessages.INVALID_INPUT,
                selectedItem -> (selectedItem > 0) && (selectedItem <= items.size()));
        int itemIndex = item - 1;
        return items.get(itemIndex).itemAction.process();
    }

    protected Exit exit() {
        scn.close();
        return new Exit(simulationManager);
    }

    protected void informAboutSelectedOption(String msg) {
        System.out.println(msg);
    }

    protected abstract void setDefaultMenuContent();
}
