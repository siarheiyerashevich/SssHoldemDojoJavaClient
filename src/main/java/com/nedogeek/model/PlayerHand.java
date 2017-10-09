package com.nedogeek.model;

/**
 * Created by Sergey_Fedorchuk on 10/8/2017.
 */
public class PlayerHand {

    private Hand hand;
    private int playerIndex;

    public PlayerHand(Hand hand, int playerIndex) {
        this.hand = hand;
        this.playerIndex = playerIndex;
    }

    public Hand getHand() {

        return hand;
    }

    public void setHand(Hand hand) {

        this.hand = hand;
    }

    public int getPlayerIndex() {

        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {

        this.playerIndex = playerIndex;
    }
}
