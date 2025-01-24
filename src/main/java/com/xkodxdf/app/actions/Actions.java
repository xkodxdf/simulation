package com.xkodxdf.app.actions;

import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManager;

public interface Actions {

    void process(WorldMapManager mapManager) throws InvalidParametersException;
}
