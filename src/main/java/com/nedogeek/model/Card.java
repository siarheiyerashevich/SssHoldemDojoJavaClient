package com.nedogeek.model;

import java.util.Objects;

public class Card implements Comparable<Card> {

    private final CardSuit suit;
    private final CardValue value;

    public Card(CardSuit suit, CardValue value) {

        this.suit = suit;
        this.value = value;
    }

    public CardSuit getSuit() {

        return suit;
    }

    public CardValue getValue() {

        return value;
    }

    @Override
    public String toString() {
        return "" + value + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return suit == card.suit && value == card.value;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Card o) {

        return value.compareTo(o.getValue());
    }

    public boolean sameSuit(Card otherCard) {
        return Objects.equals(suit, otherCard.getSuit());
    }

    public boolean sameValue(Card otherCard) {

        return Objects.equals(value, otherCard.getValue());
    }

    public boolean isNear(Card otherCard) {
        int cardValueNumber = value.ordinal();
        int otherCardValue = otherCard.value.ordinal();
        return Math.abs(cardValueNumber - otherCardValue) == 1;
    }

    public String getStringValue() {
        return value.toString();
    }

    public String getStringSuit() {
        return suit.toString();
    }
}
