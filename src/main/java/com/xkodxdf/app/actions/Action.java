package com.xkodxdf.app.actions;

import com.xkodxdf.app.map.exceptions.WorldMapException;
import com.xkodxdf.app.map.WorldMapManagement;

public interface Action {

    void process(WorldMapManagement mapManager) throws WorldMapException;
}
