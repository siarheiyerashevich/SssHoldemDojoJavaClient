package com.nedogeek.model;

import java.util.List;

public class HandsRange {

    private List<Hand> hands;
    private int playerIndex;

    public HandsRange(List<Hand> hands) {

        this.hands = hands;
    }

    public HandsRange(List<Hand> hands, int playerIndex) {

        this.hands = hands;
        this.playerIndex = playerIndex;
    }

    public List<Hand> getHands() {

        return hands;
    }

    public void setHands(List<Hand> hands) {

        this.hands = hands;
    }

    public int getPlayerIndex() {

        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {

        this.playerIndex = playerIndex;
    }
}
