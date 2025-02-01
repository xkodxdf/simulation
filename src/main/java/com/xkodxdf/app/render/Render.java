package com.xkodxdf.app.render;

import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.config.Config;
import com.xkodxdf.app.messages.Messages;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Render {

    private EntityNotationProvider entityNotation;

    public Render() {
        this.entityNotation = EntityNotation.SYMBOL;
    }

    public void setEntityNotation(EntityNotation entityNotation) {
        Objects.requireNonNull(entityNotation, Messages.MUST_NOT_BE_NULL);
        this.entityNotation = entityNotation;
    }

    public void renderMap(Map<Coordinates, Entity> map) {
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
            System.out.println(assembleRow(row, y, map));
            row.setLength(0);
        }
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
