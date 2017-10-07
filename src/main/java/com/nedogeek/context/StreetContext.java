package com.nedogeek.context;

import com.nedogeek.model.Round;

public enum StreetContext {
    INSTANCE;

    private Round round;
    private String firstRaiser;

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getFirstRaiser() {
        return firstRaiser;
    }

    public void setFirstRaiser(String firstRaiser) {
        this.firstRaiser = firstRaiser;
    }

    public void resetContext() {
        round = null;
        firstRaiser = null;
    }
}
