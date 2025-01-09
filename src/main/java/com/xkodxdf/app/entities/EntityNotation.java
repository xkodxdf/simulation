package com.xkodxdf.app.entities;

public enum EntityNotation {

    EMPTY_SQUARE("\uD83D\uDFEB"),
    GRASS("\uD83C\uDF31"),
    ROCK("⛰️"),
    TREE("\uD83C\uDFD5️"),
    HERBIVORE("\uD83E\uDD8C"),
    PREDATOR("\uD83D\uDC3A");


    private final String notation;


    EntityNotation(String notation) {
        this.notation = notation;
    }


    public String getNotation() {
        return notation;
    }
}
