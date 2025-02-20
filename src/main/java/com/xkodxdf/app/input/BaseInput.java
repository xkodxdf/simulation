package com.xkodxdf.app.input;

import com.xkodxdf.app.render.Render;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaseInput<V> {

    private final BufferedReader reader;
    private final Function<String, V> verification;
    private final Render render;

    public BaseInput(BufferedReader reader, Function<String, V> verification) {
        this.reader = reader;
        this.verification = verification;
        this.render = new Render();
    }

    public V getVerifiedInput() {
        while (true) {
            try {
                String input = reader.readLine();
                return verification.apply(input);
            } catch (Exception ignore) {
            }
        }
    }

    public V getValidInput(String promptMessage, String invalidInputMessage, Predicate<V> validation) {
        V result;
        while (true) {
            printMessageIfPresent(promptMessage);
            try {
                String input = reader.readLine();
                result = verification.apply(input);
                if (validation.test(result)) {
                    break;
                }
            } catch (Exception ignore) {
            }
            printMessageIfPresent(invalidInputMessage);
        }
        return result;
    }

    public boolean ready() {
        try {
            return reader.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printMessageIfPresent(String message) {
        if (message != null && !message.isBlank()) {
            render.printlnString(message);
        }
    }
}
