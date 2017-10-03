package com.nedogeek.model;

public enum CardSuit {
    SPADES ("♠"),
    HEARTS ("♥"),
    DIAMONDS ("♦"),
    CLUBS ("♣");

    private final String stringValue;

    CardSuit(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}