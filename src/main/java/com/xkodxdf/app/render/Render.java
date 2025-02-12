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

    public void renderMenu(String title, List<String> items, String prompt) {
        System.out.println(title);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf(" %d. %s\n", i + 1, items.get(i));
        }
        printString(prompt);
    }

    public void printString(String s) {
        System.out.println(s);
    }

    public void renderTurn(Map<Coordinates, Entity> map) {
        StringBuilder row = new StringBuilder();
        Config config = Config.getConfig();
        if (entityNotation.equals(EntityNotation.SYMBOL)) {
            drawXCoordinateLine(config.getWidth());
        }
        System.out.println();
        for (int y = 0; y < Config.getConfig().getHeight(); y++) {
            if (entityNotation.equals(EntityNotation.SYMBOL)) {
                drawYCoordinateLine(y);
            }
            printString(assembleRow(row, y, map));
            row.setLength(0);
        }
        printString(SimulationPauseMessages.PROMPT_MSG);
    }

    private String assembleRow(StringBuilder row, int y, Map<Coordinates, Entity> map) {
        for (int x = 0; x < Config.getConfig().getWidth(); x++) {
            Entity entity = map.get(new Coordinates(x, y));
            row.append(entityNotation.getNotation(entity));
        }
        return row.toString();
    }

    private void drawXCoordinateLine(int lastXCoordinate) {
        System.out.print("\n +");
        IntStream.range(0, lastXCoordinate).forEach(num -> System.out.printf(" %2d", num));
    }

    private void drawYCoordinateLine(int coordinateY) {
        System.out.printf("%2d ", coordinateY);
    }
}
