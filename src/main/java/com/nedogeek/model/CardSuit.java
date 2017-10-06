package com.nedogeek.model;

public enum CardSuit {
    SPADES("♠"),
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣");

    private final String stringValue;

    CardSuit(String stringValue) {
        this.stringValue = stringValue;
    }

    public static CardSuit fromString(String s) {
        switch (s) {
            case "♠":
                return SPADES;
            case "♥":
                return HEARTS;
            case "♦":
                return DIAMONDS;
            case "♣":
                return CLUBS;
        }

        throw new IllegalArgumentException("Suit not found: " + s);
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
