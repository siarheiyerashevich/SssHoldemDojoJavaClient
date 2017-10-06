package com.nedogeek.montecarlo;

import java.util.Arrays;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;

import javafx.util.Pair;

public class FastCombination {

    public static void main(String[] args) {

        final int attempt = 100000;
        long time = System.currentTimeMillis();
        int combinationWeight = 0;
        System.out.println(time);
        for(int i = 0; i < attempt; i++) {
            combinationWeight = emulateCombinationEvaluator();
        }
        System.out.println(System.currentTimeMillis());
        double avgTime = (System.currentTimeMillis() - time) / 100;
        System.out.println("combinationWeight " + combinationWeight + ", avg execution: " + avgTime + " ms");
//        System.out.println("average execution: " + time + " ms");
    }

    private static int emulateCombinationEvaluator() {

        long time = System.currentTimeMillis();
        Card[] hand = new Card[2];
        hand[0] = new Card(CardSuit.SPADES, CardValue.KING);
        hand[1] = new Card(CardSuit.SPADES, CardValue.QUEEN);

        Card[] board = new Card[5];
        board[0] = new Card(CardSuit.SPADES, CardValue.FIVE);
        board[1] = new Card(CardSuit.SPADES, CardValue.NINE);
        board[2] = new Card(CardSuit.DIAMONDS, CardValue.JACK);
        board[3] = new Card(CardSuit.HEARTS, CardValue.EIGHT);
        board[4] = new Card(CardSuit.HEARTS, CardValue.SIX);

        return getCombinationWeight(hand, board);
    }

    private static void sortHand(Card[] cards) {

        for (int n = 0; n < cards.length; n++) {
            for (int i = 0; i < cards.length - n - 1; i++) {
                if (cards[i].compareTo(cards[i + 1]) < 0) {
                    Card tempCard = cards[i];
                    cards[i] = cards[i + 1];
                    cards[i + 1] = tempCard;
                }
            }
        }
    }

    static int getCombinationWeight(Card[] hand, Card[] board) {

        Card[] cards = new Card[hand.length + board.length];
        System.arraycopy(hand, 0, cards, 0, hand.length);
        System.arraycopy(board, 0, cards, hand.length,
                board.length);
        sortHand(cards);
        CardSuit flashCardSuit = getFlashCardSuit(cards);
        Pair<Integer, Pair<Integer, Integer>> royalFlashPair = isRoyalFlash(cards, flashCardSuit);
        if (royalFlashPair.getKey() != -1) {
            return 117;
        }
        Pair<Integer, Integer> straightFlashPair = royalFlashPair.getValue();
        int result = straightFlashPair.getKey();
        if (result != -1) {
            return 104 + result;
        }
        SameCards sameCards = getSameCards(cards);
        result = sameCards.getQuads();
        if (result != -1) {
            return 91 + result;
        }
        result = sameCards.getFullHouse();
        if (result != -1) {
            return 78 + result;
        }
        result = straightFlashPair.getValue();
        if (result != -1) {
            return 65 + result;
        }
        // FIXME: calculate isStraight inside royalFlash
        result = isStraight(cards);
        if (result != -1) {
            return 52 + result;
        }
        result = sameCards.getTriple();
        if (result != -1) {
            return 39 + result;
        }
        result = sameCards.getTwoPairs();
        if (result != -1) {
            return 26 + result;
        }
        result = sameCards.getPair();
        if (result != -1) {
            return 13 + result;
        }
        // FIXME: return weight of whole hand
        return sameCards.getNotSameCardValues()[0].ordinal();
    }

    private static CardSuit getFlashCardSuit(Card[] cards) {

        int[] suits = new int[4];
        for (Card card : cards) {
            if (card.getSuit() == CardSuit.SPADES) {
                suits[0]++;
            } else if (card.getSuit() == CardSuit.HEARTS) {
                suits[1]++;
            } else if (card.getSuit() == CardSuit.DIAMONDS) {
                suits[2]++;
            } else if (card.getSuit() == CardSuit.CLUBS) {
                suits[3]++;
            }
        }
        CardSuit cardSuit = null;
        for (int i = 0; i < 4; i++) {
            if (suits[i] >= 5) {
                if (i == 0) {
                    cardSuit = CardSuit.SPADES;
                } else if (i == 1) {
                    cardSuit = CardSuit.HEARTS;
                } else if (i == 2) {
                    cardSuit = CardSuit.DIAMONDS;
                } else if (i == 3) {
                    cardSuit = CardSuit.CLUBS;
                }
                break;
            }
        }
        return cardSuit;
    }

    private static Pair<Integer, Pair<Integer, Integer>> isRoyalFlash(Card[] cards,
            CardSuit flashCardSuit) {

        Pair<Integer, Integer> straightFlashPair = isStraightFlash(cards, flashCardSuit);
        if (straightFlashPair.getKey() == CardValue.ACE.ordinal()) {
            return new Pair<>(straightFlashPair.getKey(), straightFlashPair);
        }
        return new Pair<>(-1, straightFlashPair);
    }

    private static Pair<Integer, Integer> isStraightFlash(Card[] cards, CardSuit flashCardSuit) {

        if (flashCardSuit == null) {
            return new Pair<>(-1, -1);
        }
        Card[] flashCards = new Card[5];
        int i = 0;
        for (Card card : cards) {
            if (card.getSuit() == flashCardSuit && i < 5) {
                flashCards[i++] = card;
            }
        }
        return new Pair<>(isStraight(flashCards), flashCards[0].getValue().ordinal());
    }

    /**
     * Check that 5 of 7 sorted cards are straight
     *
     * @param cards cards
     * @return the high card value of straight
     */
    private static int isStraight(Card[] cards) {

        int length = cards.length;
        int nextIteration = getNextIndexForStraight(cards, length, 0);

        if (nextIteration == -1 || nextIteration > 4) {
            return cards[0].getValue().ordinal();
        }

        if (nextIteration == 1) {
            nextIteration = getNextIndexForStraight(cards, length, nextIteration);
        }

        if (nextIteration == -1 || nextIteration > 5) {
            return cards[0].getValue().ordinal();
        }

        if (nextIteration == 2) {
            nextIteration = getNextIndexForStraight(cards, length, nextIteration);
        }

        if (nextIteration == -1) {
            return cards[0].getValue().ordinal();
        }

        return -1;
    }

    private static int getNextIndexForStraight(Card[] cards, int length, int startIndex) {

        for (int i = startIndex; i < length - 1; i++) {
            if (!cards[i].isNear(cards[i + 1])) {
                return i + 1;
            }
        }
        return -1;
    }

    private static SameCards getSameCards(Card[] cards) {

        SameCards sameCards = new SameCards();
        CardValue[] notSameCardValues = new CardValue[7];
        int notSameCardsIndex = 0;
        int length = cards.length;
        int repeat = 1;
        for (int i = 0; i < length - 1; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue()) {
                repeat++;
            } else {
                if (repeat == 1) {
                    notSameCardValues[notSameCardsIndex++] = cards[i].getValue();
                } else if (repeat == 2) {
                    sameCards.addSameCard(new SameCard(cards[i].getValue(), SameCardType.PAIR));
                } else if (repeat == 3) {
                    sameCards.addSameCard(new SameCard(cards[i].getValue(), SameCardType.TRIPLE));
                } else if (repeat == 4) {
                    sameCards.addSameCard(new SameCard(cards[i].getValue(), SameCardType.QUAD));
                }
                // Reset repeat counter
                repeat = 1;
            }
        }
        sameCards.addNotSameCardValues(notSameCardValues);
        return sameCards;
    }

    private static class SameCards {

        private boolean isQuads = false;
        private boolean isFullHouse = false;
        private boolean isTriple = false;
        private boolean isTwoPair = false;
        private boolean isPair = false;
        private int pairCount = 0;
        private int tripleCount = 0;
        private SameCard[] sameCardsArr;
        private CardValue[] notSameCardValues;
        private int index = 0;

        SameCards() {

            sameCardsArr = new SameCard[3];
        }

        void addNotSameCardValues(CardValue[] notSameCardValues) {
            this.notSameCardValues = notSameCardValues;
        }

        CardValue[] getNotSameCardValues() {
            return this.notSameCardValues;
        }

        void addSameCard(SameCard sameCard) {

            if (index >= 3) {
                return;
            }
            sameCardsArr[index++] = sameCard;
            if (sameCard.sameCardType == SameCardType.PAIR) {
                if (tripleCount > 0) {
                    isFullHouse = true;
                } else if (pairCount > 0) {
                    isTwoPair = true;
                }
                isPair = true;
                pairCount++;
            } else if (sameCard.sameCardType == SameCardType.TRIPLE) {
                if (tripleCount > 0 || pairCount > 0) {
                    isFullHouse = true;
                }
                isTriple = true;
                tripleCount++;
            } else if (sameCard.sameCardType == SameCardType.QUAD) {
                isQuads = true;
            }
        }

        int getQuads() {

            if (isQuads) {
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.QUAD) {
                        return sameCardsArr[i].cardValue.ordinal();
                    }
                }
            }
            return -1;
        }

        int getFullHouse() {

            if (isFullHouse) {

                int highTriple = -1;
                int highPair = -1;
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.TRIPLE
                            && sameCardsArr[i].cardValue.ordinal() > highTriple) {
                        highTriple = sameCardsArr[i].cardValue.ordinal();
                    }
                }
                for (int i = 0; i < index; i++) {
                    if ((sameCardsArr[i].sameCardType == SameCardType.TRIPLE
                            || sameCardsArr[i].sameCardType == SameCardType.PAIR)
                            && sameCardsArr[i].cardValue.ordinal() != highTriple
                            && sameCardsArr[i].cardValue.ordinal() > highPair) {
                        highPair = sameCardsArr[i].cardValue.ordinal();
                    }
                }
                // FIXME: calculate pair weight in full house combination
                return highTriple;
            }
            return -1;
        }

        int getTriple() {

            if(isTriple) {
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.TRIPLE) {
                        return sameCardsArr[i].cardValue.ordinal();
                    }
                }
            }
            return -1;
        }

        int getTwoPairs() {

            if (isTwoPair) {

                int highPair = -1;
                int secondPair = -1;
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.PAIR
                            && sameCardsArr[i].cardValue.ordinal() > highPair) {
                        highPair = sameCardsArr[i].cardValue.ordinal();
                    }
                }
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.PAIR
                            && sameCardsArr[i].cardValue.ordinal() != highPair
                            && sameCardsArr[i].cardValue.ordinal() > secondPair) {
                        highPair = sameCardsArr[i].cardValue.ordinal();
                    }
                }
                // FIXME: calculate low pair weight in combination
                return highPair;
            }
            return -1;
        }

        int getPair() {

            if(isPair) {
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.PAIR) {
                        return sameCardsArr[i].cardValue.ordinal();
                    }
                }
            }
            return -1;
        }
    }

    private static class SameCard {

        private CardValue cardValue;
        private SameCardType sameCardType;

        SameCard(CardValue cardValue, SameCardType sameCardType) {

            this.cardValue = cardValue;
            this.sameCardType = sameCardType;
        }
    }

    private enum SameCardType {
        QUAD, TRIPLE, PAIR
    }
}
