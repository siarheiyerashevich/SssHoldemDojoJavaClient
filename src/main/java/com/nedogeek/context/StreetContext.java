package com.nedogeek.context;

import com.nedogeek.model.Round;

public enum StreetContext {
    INSTANCE;

    private Round round;


    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void resetContext() {
        round = null;
    }
}
