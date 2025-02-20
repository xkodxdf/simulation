package com.xkodxdf.app.actions;

import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManagement;

public interface Action {

    void process(WorldMapManagement mapManager) throws InvalidParametersException;
}
