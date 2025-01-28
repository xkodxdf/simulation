package com.xkodxdf.app.entities.base;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
