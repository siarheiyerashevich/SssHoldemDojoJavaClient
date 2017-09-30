package com.nedogeek.model;

import java.util.List;

public class MoveData {

    List<Card> deskCards;
    int pot;
    String gameRound;
    String dealer;
    String mover;
    List<String> event;
    List<Player> players;
    String cardCombination;

    public List<Card> getDeskCards() {
        return deskCards;
    }

    public void setDeskCards(List<Card> deskCards) {
        this.deskCards = deskCards;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public String getGameRound() {
        return gameRound;
    }

    public void setGameRound(String gameRound) {
        this.gameRound = gameRound;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getMover() {
        return mover;
    }

    public void setMover(String mover) {
        this.mover = mover;
    }

    public List<String> getEvent() {
        return event;
    }

    public void setEvent(List<String> event) {
        this.event = event;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getCardCombination() {
        return cardCombination;
    }

    public void setCardCombination(String cardCombination) {
        this.cardCombination = cardCombination;
    }
}
