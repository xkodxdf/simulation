package com.xkodxdf.app.input;

import java.io.BufferedReader;

public class StringInput extends BaseInput<String> {

    public StringInput(BufferedReader reader) {
        super(reader, input -> input);
    }
}
