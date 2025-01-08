package com.xkodxdf.app.entities;

public enum EntityNotation {

    EMPTY_SQUARE(" "),
    GRASS("G"),
    ROCK("R"),
    TREE("T"),
    HERBIVORE("H"),
    PREDATOR("P");


    private final String notation;


    EntityNotation(String notation) {
        this.notation = notation;
    }


    public String getNotation() {
        return notation;
    }
}
