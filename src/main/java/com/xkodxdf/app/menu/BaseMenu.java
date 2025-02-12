package com.xkodxdf.app.menu;

import com.xkodxdf.app.SimulationManage;
import com.xkodxdf.app.input.BaseInput;
import com.xkodxdf.app.text_constants.InputMessages;
import com.xkodxdf.app.text_constants.MenuContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMenu implements Menu {

    protected final String title;
    protected final List<Item> items;
    protected final BaseInput<Integer> input;
    protected final SimulationManage simulationManager;

    public BaseMenu(String title, BaseInput<Integer> input, SimulationManage simulationManager) {
        this.title = title;
        this.items = new ArrayList<>();
        this.input = input;
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
        List<String> itemsText = items.stream().map(item -> item.text).collect(Collectors.toList());
        String prompt = MenuContent.PROMPT_MSG + items.size();
        simulationManager.getRenderer().renderMenu(title, itemsText, prompt);
    }

    @Override
    public Menu selectItem() throws Exception {
        int item = input.getInput(null, InputMessages.INVALID_INPUT,
                selectedItem -> (selectedItem > 0) && (selectedItem <= items.size()));
        int itemIndex = item - 1;
        return items.get(itemIndex).itemAction.process();
    }

    protected Exit exit() {
        return new Exit(input, simulationManager);
    }

    protected void informAboutSelectedOption(String msg) {
        System.out.println(msg);
    }

    protected abstract void setDefaultMenuContent();
}
