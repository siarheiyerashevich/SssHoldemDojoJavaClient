package com.nedogeek.montecarlo;

public class EquityResult {

    private String playerName;
    private double equity;

    public EquityResult(String playerName, double equity) {

        this.playerName = playerName;
        this.equity = equity;
    }

    public String getPlayerName() {

        return playerName;
    }

    public double getEquity() {

        return equity;
    }
}
