package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.animated.Herbivore;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.exceptions.InvalidCoordinatesException;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {


    @Test
    public void createInstanceWithValidArguments() {
        int width = 16;
        int height = 8;

        assertDoesNotThrow(() -> new WorldMap(width, height));
    }

    @Test
    public void createInstanceWithInvalidArguments() {
        int width = 49;
        int height = 7;

        assertThrows(InvalidParametersException.class, () -> new WorldMap(width, height));
    }

    @Test
    public void testUnoccupiedCoordinatesInitSize() throws InvalidParametersException {
        int width = 48;
        int height = 10;
        int expectedSize = width * height;

        WorldMap worldMap = new WorldMap(width, height);

        assertEquals(expectedSize, worldMap.getUnoccupiedCoordinates().size());
    }

    @Test
    public void testEntityOccupiesAvailableCoordinate() throws InvalidParametersException,
            InvalidCoordinatesException {
        int width = 10;
        int height = 10;
        WorldMap worldMap = new WorldMap(width, height);
        Coordinates coordinates = new Coordinates(5, 5);
        int expectedUnoccupiedCoordinatesSize = width * height;

        assertEquals(expectedUnoccupiedCoordinatesSize, worldMap.getUnoccupiedCoordinates().size());
        assertTrue(worldMap.getUnoccupiedCoordinates().contains(coordinates));

        worldMap.setEntity(coordinates, new Herbivore());

        assertEquals(expectedUnoccupiedCoordinatesSize - 1, worldMap.getUnoccupiedCoordinates().size());
        assertFalse(worldMap.getUnoccupiedCoordinates().contains(coordinates));
    }

    @Test
    public void testEntityReleasesOccupiedCoordinate() throws InvalidParametersException,
            InvalidCoordinatesException {
        int width = 10;
        int height = 10;
        WorldMap worldMap = new WorldMap(width, height);
        Coordinates coordinates = new Coordinates(5, 5);
        worldMap.setEntity(coordinates, new Herbivore());
        int expectedUnoccupiedCoordinatesSize = width * height - 1;

        assertEquals(expectedUnoccupiedCoordinatesSize, worldMap.getUnoccupiedCoordinates().size());
        assertFalse(worldMap.getUnoccupiedCoordinates().contains(coordinates));

        worldMap.removeEntity(coordinates);

        assertEquals(expectedUnoccupiedCoordinatesSize + 1, worldMap.getUnoccupiedCoordinates().size());
        assertTrue(worldMap.getUnoccupiedCoordinates().contains(coordinates));
    }

    @Test
    public void setEntityWithValidCoordinates() throws InvalidParametersException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(8, 6);

        assertDoesNotThrow(() -> worldMap.setEntity(coordinates, new Rock()));
    }

    @Test
    public void setEntityWithInvalidCoordinates() throws InvalidParametersException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(16, -1);

        assertThrows(InvalidCoordinatesException.class, () ->
                worldMap.setEntity(coordinates, new Rock()));
    }

    @Test
    public void getEntityWithValidCoordinates() throws InvalidParametersException, InvalidCoordinatesException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(8, 6);
        Entity entity = new Rock();
        worldMap.setEntity(coordinates, entity);

        assertEquals(entity, worldMap.getEntity(coordinates));
    }

    @Test
    public void getEntityWithInvalidCoordinates() throws InvalidParametersException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(99, 99);

        assertThrows(InvalidCoordinatesException.class, () -> worldMap.getEntity(coordinates));
    }

    @Test
    public void removeEntityWithValidCoordinates() throws InvalidParametersException, InvalidCoordinatesException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(8, 6);
        Entity entity = new Rock();
        worldMap.setEntity(coordinates, entity);
        int worldMapSize = worldMap.getSize();

        worldMap.removeEntity(coordinates);

        assertNull(worldMap.getEntity(coordinates));
        assertEquals(worldMapSize - 1, worldMap.getSize());
    }

    @Test
    public void removeEntityWithInvalidCoordinates() throws InvalidParametersException {
        WorldMap worldMap = new WorldMap(16, 8);
        Coordinates coordinates = new Coordinates(6, 8);

        assertThrows(InvalidCoordinatesException.class, () -> worldMap.removeEntity(coordinates));
    }
}