package com.xkodxdf.app.menu;

public class Item {

    protected final String text;
    protected final ItemAction itemAction;

    public Item(String text, ItemAction itemAction) {
        this.text = text;
        this.itemAction = itemAction;
    }
}
