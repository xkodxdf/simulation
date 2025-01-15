package com.xkodxdf.app.render;

public enum Sprite {

    EMPTY("\uD83D\uDFEB"),
    ROCK("⛰️"),
    TREE("\uD83C\uDFD5️"),
    CORPSE("☠\uFE0F"),
    GRASS("\uD83C\uDF31"),
    HERBIVORE("\uD83E\uDD8C"),
    PREDATOR("\uD83D\uDC3A");


    private final String notation;


    Sprite(String notation) {
        this.notation = notation;
    }


    public String getNotation() {
        return notation;
    }
}
