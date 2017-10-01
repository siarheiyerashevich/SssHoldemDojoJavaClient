package com.nedogeek.model;

public class HandData {

    private Position position;
    private int initialCardsWeight;

    public int getInitialCardsWeight() {
        return initialCardsWeight;
    }

    public void setInitialCardsWeight(int initialCardsWeight) {
        this.initialCardsWeight = initialCardsWeight;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
