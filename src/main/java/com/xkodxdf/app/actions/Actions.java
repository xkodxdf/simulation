package com.xkodxdf.app.actions;

import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;

public interface Actions {

    void process(WorldMapManage mapManager) throws InvalidParametersException;
}
