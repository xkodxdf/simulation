package com.xkodxdf.app.render;

import com.xkodxdf.app.entities.base.Entity;

public interface EntityNotationProvider {

    String getNotation(Entity entity) throws IllegalArgumentException;
}
