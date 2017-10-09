package com.nedogeek.model;

public class Hand {

    private Card firstCard;
    private Card secondCard;

    public static Hand of(Card firstCard, Card secondCard) {

        return new Hand(firstCard, secondCard);
    }

    public static Hand of(CardSuit firstCardSuit, CardValue firstCardValue,
            CardSuit secondCardSuit, CardValue secondCardValue) {

        return new Hand(new Card(firstCardSuit, firstCardValue), new Card(secondCardSuit, secondCardValue));
    }

    private Hand(Card firstCard, Card secondCard) {

        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public Card getFirstCard() {

        return firstCard;
    }

    public void setFirstCard(Card firstCard) {

        this.firstCard = firstCard;
    }

    public Card getSecondCard() {

        return secondCard;
    }

    public void setSecondCard(Card secondCard) {

        this.secondCard = secondCard;
    }

    @Override
    public String toString() {

        return "" + firstCard + " " + secondCard;
    }

    public boolean partiallyEquals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Hand hand = (Hand) o;

        return firstCard.equals(hand.firstCard)
                || secondCard.equals(hand.secondCard)
                || firstCard.equals(hand.secondCard)
                || secondCard.equals(hand.firstCard);

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Hand hand = (Hand) o;

        return firstCard.equals(hand.firstCard) && secondCard.equals(hand.secondCard);

    }

    @Override
    public int hashCode() {

        int result = firstCard != null ? firstCard.hashCode() : 0;
        result = 31 * result + (secondCard != null ? secondCard.hashCode() : 0);
        return result;
    }
}
