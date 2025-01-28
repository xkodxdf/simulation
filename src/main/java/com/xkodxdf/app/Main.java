package com.xkodxdf.app;

import com.xkodxdf.app.exceptions.InvalidParametersException;

public class Main {

    public static void main(String[] args) throws InvalidParametersException, InterruptedException {
        Simulation sim = new Simulation();
        sim.start();
    }
}
