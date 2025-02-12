package com.xkodxdf.app.input;

import java.io.BufferedReader;

public class IntegerInput extends BaseInput<Integer> {

    public IntegerInput(BufferedReader reader) {
        super(reader, Integer::parseInt);
    }
}
