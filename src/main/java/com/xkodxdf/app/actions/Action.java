package com.xkodxdf.app.actions;

import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.WorldMapManage;

public interface Action {

    void process(WorldMapManage mapManager) throws InvalidParametersException;
}
