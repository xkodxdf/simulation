package com.xkodxdf.app.menu.input;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaseInput<V> {

    private final Scanner scn;
    private final Function<String, V> verification;

    public BaseInput(Scanner scn, Function<String, V> verification) {
        this.scn = scn;
        this.verification = verification;
    }

    public V getInput(String promptMsg, String invalidInputMsg, Predicate<V> validation) {
        V result;
        while (true) {
            printMsgIfPresent(promptMsg);
            String input = scn.nextLine();
            try {
                result = verification.apply(input);
                if (validation.test(result)) {
                    break;
                }
            } catch (Exception ignore) {
            }
            printMsgIfPresent(invalidInputMsg);
        }
        return result;
    }

    private void printMsgIfPresent(String msg) {
        if ((msg != null) && (!msg.isBlank())) {
            System.out.println(msg);
        }
    }
}
