package com.xkodxdf.app.render;

import com.xkodxdf.app.entities.animate.Herbivore;
import com.xkodxdf.app.entities.animate.Predator;
import com.xkodxdf.app.entities.base.Entity;
import com.xkodxdf.app.entities.inanimate.Corpse;
import com.xkodxdf.app.entities.inanimate.Grass;
import com.xkodxdf.app.entities.inanimate.Rock;
import com.xkodxdf.app.entities.inanimate.Tree;
import com.xkodxdf.app.messages.Messages;

public enum EntityNotation implements EntityNotationProvider {
    EMOJI {
        @Override
        public String getNotation(Entity entity) {
            if (entity == null) {
                return EmojiNotation.EMPTY;
            }
            if (entity instanceof Rock) {
                return EmojiNotation.ROCK;
            }
            if (entity instanceof Tree) {
                return EmojiNotation.TREE;
            }
            if (entity instanceof Corpse) {
                return EmojiNotation.CORPSE;
            }
            if (entity instanceof Grass) {
                return EmojiNotation.GRASS;
            }
            if (entity instanceof Herbivore) {
                return EmojiNotation.HERBIVORE;
            }
            if (entity instanceof Predator) {
                return EmojiNotation.PREDATOR;
            }
            throw new IllegalArgumentException(Messages.NO_NOTATION_FOUND_FOR_ENTITY + entity.getClass().getSimpleName());
        }
    },
    SYMBOL {
        @Override
        public String getNotation(Entity entity) {
            if (entity == null) {
                return AnsiColor.colorizeNotation(AnsiColor.WHITE, SymbolNotation.EMPTY);
            }
            if (entity instanceof Rock) {
                return AnsiColor.colorizeNotation(AnsiColor.BRIGHT_WHITE, SymbolNotation.ROCK);
            }
            if (entity instanceof Tree) {
                return AnsiColor.colorizeNotation(AnsiColor.BRIGHT_GREEN, SymbolNotation.TREE);
            }
            if (entity instanceof Corpse) {
                return AnsiColor.colorizeNotation(AnsiColor.BRIGHT_RED, SymbolNotation.CORPSE);
            }
            if (entity instanceof Grass) {
                return AnsiColor.colorizeNotation(AnsiColor.MAGENTA, SymbolNotation.GRASS);
            }
            if (entity instanceof Herbivore) {
                return AnsiColor.colorizeNotation(AnsiColor.BRIGHT_BLUE, SymbolNotation.HERBIVORE);
            }
            if (entity instanceof Predator) {
                return AnsiColor.colorizeNotation(AnsiColor.BRIGHT_YELLOW, SymbolNotation.PREDATOR);
            }
            throw new IllegalArgumentException(Messages.NO_NOTATION_FOUND_FOR_ENTITY + entity.getClass().getSimpleName());
        }
    };


    private static class EmojiNotation {

        private static final String EMPTY = "\uD83D\uDFEB";
        private static final String ROCK = "⛰️";
        private static final String TREE = "\uD83C\uDFD5️";
        private static final String CORPSE = "☠️";
        private static final String GRASS = "\uD83C\uDF31";
        private static final String HERBIVORE = "\uD83E\uDD8C";
        private static final String PREDATOR = "\uD83D\uDC3A";
    }


    private static class SymbolNotation {

        private static final String EMPTY = " . ";
        private static final String ROCK = "/^\\";
        private static final String TREE = "(¶)";
        private static final String CORPSE = " x ";
        private static final String GRASS = " ѽ ";
        private static final String HERBIVORE = " @ ";
        private static final String PREDATOR = " $ ";
    }


    private static class AnsiColor {

        private static final String RESET = "\u001B[0m";
        private static final String TEMPLATE = "\u001B[%dm%s" + RESET;
        private static final int BRIGHT_RED = 91;
        private static final int BRIGHT_GREEN = 92;
        private static final int BRIGHT_YELLOW = 93;
        private static final int BRIGHT_BLUE = 94;
        private static final int MAGENTA = 35;
        private static final int WHITE = 37;
        private static final int BRIGHT_WHITE = 97;

        private static String colorizeNotation(int mainColor, String notation) {
            return String.format(TEMPLATE, mainColor, notation);
        }
    }
}

