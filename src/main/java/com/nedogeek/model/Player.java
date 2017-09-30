package com.nedogeek.model;

import java.util.List;

public class Player {

    final String name;
    final int balance;
    final int bet;
    final String status;
    final List<Card> cards;

    public Player(String name, int balance, int bet, String status, List<Card> cards) {
        this.name = name;
        this.balance = balance;
        this.bet = bet;
        this.status = status;
        this.cards = cards;
    }

}
