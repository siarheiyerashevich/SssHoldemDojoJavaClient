package com.nedogeek.context;

import com.nedogeek.model.Position;

public enum HandContext {
    INSTANCE;

    private Position position;
    private int initialCardsWeight;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getInitialCardsWeight() {
        return initialCardsWeight;
    }

    public void setInitialCardsWeight(int initialCardsWeight) {
        this.initialCardsWeight = initialCardsWeight;
    }

    public void resetContext() {
        position = null;
        initialCardsWeight = -1;
    }
}
