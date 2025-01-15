package com.xkodxdf.app.map;

import com.xkodxdf.app.entities.EntityType;
import com.xkodxdf.app.entities.animated.Predator;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.exceptions.InvalidParametersException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Manager {

    int getWidth();

    int getHeight();

    int getSize();

    void setEntity(Coordinates coordinates, Entity entity) throws InvalidParametersException;

    Entity getEntity(Coordinates coordinates) throws InvalidParametersException;

    Entity removeEntity(Coordinates coordinates) throws InvalidParametersException;

    int getEntityMapFillingPercentage(EntityType entityType) throws InvalidParametersException;

    Coordinates getEntityCoordinate(Entity entity);

    Set<Coordinates> getUnoccupiedCoordinates();

    Coordinates getRandomUnoccupiedCoordinate();

    Set<Coordinates> getAroundCoordinates(Coordinates target, int radius);

    Set<Coordinates> getAroundUnoccupiedCoordinates(Coordinates target, int radius);

    List<Entity> getAroundEntities(Coordinates target, int radius) throws InvalidParametersException;

    Set<Map.Entry<Coordinates, Creature>> getCreaturesWithCoordinates();

    Set<Map.Entry<Coordinates, Predator>> getPredatorsWithCoordinates();

    List<Creature> getCreatures();

    void validateCoordinates(Coordinates coordinates) throws InvalidParametersException;
}
