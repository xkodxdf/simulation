package com.xkodxdf.app.actions;

import com.xkodxdf.app.worldmap.exceptions.WorldMapException;
import com.xkodxdf.app.worldmap.WorldMapManagement;

public interface Action {

    void process(WorldMapManagement mapManager) throws WorldMapException;
}
