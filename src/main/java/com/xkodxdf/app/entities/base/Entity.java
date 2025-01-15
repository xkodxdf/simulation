package com.xkodxdf.app.entities.base;

/*
    Корневой абстрактный класс для всех существ и объектов существующих в симуляции.
*/
public abstract class Entity {

    private static int nextId = 1;

    protected final int id;


    public Entity() {
        id = nextId;
        nextId++;
    }


    public int getId() {
        return id;
    }
}
