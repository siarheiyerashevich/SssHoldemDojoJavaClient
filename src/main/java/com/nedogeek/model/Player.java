package com.nedogeek.model;

import java.util.List;

public class Player {

    private final String name;
    private final int balance;
    private final int bet;
    private final String status;
    private final List<Card> cards;

    public Player(String name, int balance, int bet, String status, List<Card> cards) {
        this.name = name;
        this.balance = balance;
        this.bet = bet;
        this.status = status;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getBet() {
        return bet;
    }

    public String getStatus() {
        return status;
    }

    public List<Card> getCards() {
        return cards;
    }

}
