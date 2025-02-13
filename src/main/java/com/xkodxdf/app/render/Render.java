package com.xkodxdf.app.render;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.text_constants.ErrorMessages;
import com.xkodxdf.app.text_constants.SimulationPauseMessages;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Render {

    private EntityNotationProvider entityNotation;

    public Render() {
        this.entityNotation = EntityNotation.SYMBOL;
    }

    public void setEntityNotation(EntityNotation entityNotation) {
        Objects.requireNonNull(entityNotation, ErrorMessages.MUST_NOT_BE_NULL);
        this.entityNotation = entityNotation;
    }

    public void printlnString(String s) {
        System.out.println(s);
    }

    public void renderMenu(String title, List<String> items, String prompt) {
        printlnString(title);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf(" %d. %s\n", i + 1, items.get(i));
        }
        printlnString(prompt);
    }

    public void renderTurn(Map<Coordinates, Entity> map) {
        StringBuilder rowContent = new StringBuilder();
        Config config = Config.getConfig();
        if (entityNotation.equals(EntityNotation.SYMBOL)) {
            drawXCoordinateLine(config.getWidth());
        }
        printlnString("");
        int totalRows = config.getHeight();
        int totalColumns = config.getWidth();
        for (int rowCounter = 0; rowCounter < totalRows; rowCounter++) {
            if (entityNotation.equals(EntityNotation.SYMBOL)) {
                drawYCoordinateLine(rowCounter);
            }
            printlnString(assembleRow(rowContent, rowCounter, totalColumns, map));
            rowContent.setLength(0);
        }
        printlnString(SimulationPauseMessages.PROMPT_MSG);
    }

    private String assembleRow(StringBuilder rowContent, int rowCounter, int totalColumns,
                               Map<Coordinates, Entity> map) {
        for (int columnCounter = 0; columnCounter < totalColumns; columnCounter++) {
            Entity entity = map.get(new Coordinates(columnCounter, rowCounter));
            rowContent.append(entityNotation.getNotation(entity));
        }
        return rowContent.toString();
    }

    private void drawXCoordinateLine(int lastXCoordinate) {
        System.out.print("\n +");
        IntStream.range(0, lastXCoordinate).forEach(num -> System.out.printf(" %2d", num));
    }

    private void drawYCoordinateLine(int coordinateY) {
        System.out.printf("%2d ", coordinateY);
    }
}
