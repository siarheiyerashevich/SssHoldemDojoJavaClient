package com.nedogeek.context;

import com.nedogeek.model.PlayerStatus;
import com.nedogeek.model.Round;

import java.util.HashMap;
import java.util.Map;

public enum StreetContext {
    INSTANCE;

    private Round round;
    private String firstRaiser;
    private Map<String, PlayerStatus> statusMap = new HashMap<>();

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

    public Map<String, PlayerStatus> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, PlayerStatus> statusMap) {
        this.statusMap = statusMap;
    }

    public void resetContext() {
        round = null;
        firstRaiser = null;
        statusMap = new HashMap<>();
    }
}
