package com.xkodxdf.app.menu.input;

import java.util.Scanner;

public class IntegerInput extends BaseInput<Integer> {

    public IntegerInput(Scanner scn) {
        super(scn, Integer::parseInt);
    }
}
