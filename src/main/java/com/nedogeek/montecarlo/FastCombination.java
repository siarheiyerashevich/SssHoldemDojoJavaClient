package com.nedogeek.montecarlo;

import java.util.ArrayList;
import java.util.List;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;
import com.nedogeek.model.Hand;

import javafx.util.Pair;

public class FastCombination {

    public static void main(String[] args) {

        final int attempt = 100000;
        long time = System.currentTimeMillis();
        double combinationWeight = 0;
        System.out.println(time);
        for (int i = 0; i < attempt; i++) {
            combinationWeight = emulateCombinationEvaluator();
        }
        System.out.println(System.currentTimeMillis());
        double avgTime = (System.currentTimeMillis() - time) / 100;
        System.out.println("combinationWeight " + combinationWeight + ", avg execution per 100 items: " + avgTime + " ms");
//        System.out.println("average execution: " + time + " ms");
    }

    private static double emulateCombinationEvaluator() {

        long time = System.currentTimeMillis();
        Card[] hand = new Card[2];
        hand[0] = new Card(CardSuit.DIAMONDS, CardValue.ACE);
        hand[1] = new Card(CardSuit.CLUBS, CardValue.TWO);

        Card[] board = new Card[5];
        board[0] = new Card(CardSuit.SPADES, CardValue.JACK);
        board[1] = new Card(CardSuit.SPADES, CardValue.TEN);
        board[2] = new Card(CardSuit.SPADES, CardValue.NINE);
        board[3] = new Card(CardSuit.SPADES, CardValue.EIGHT);
        board[4] = new Card(CardSuit.DIAMONDS, CardValue.KING);

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

    static double getCombinationWeight(Hand hand, Card[] board) {

        int handLen = 2;
        Card[] cards = new Card[handLen + board.length];
        cards[0] = hand.getFirstCard();
        cards[1] = hand.getSecondCard();
        System.arraycopy(board, 0, cards, handLen,
                board.length);
        return getCombinationWeight(cards);
    }

    static double getCombinationWeight(Card[] hand, Card[] board) {

        Card[] cards = new Card[hand.length + board.length];
        System.arraycopy(hand, 0, cards, 0, hand.length);
        System.arraycopy(board, 0, cards, hand.length,
                board.length);
        return getCombinationWeight(cards);
    }

    private static final double RANK_RANGE = 100;
    private static final double MAIN_RANK = 4;
    private static final double MAIN_WEIGHT_RANK = Math.pow(RANK_RANGE, MAIN_RANK);

    static double calculateCombinationWeight(int mainWeight, int...additionalWeights) {

        double result = mainWeight * MAIN_WEIGHT_RANK;
        if(additionalWeights != null) {
            double rankIndex = MAIN_RANK - 1;
            for (int additionalWeight : additionalWeights) {
                if(rankIndex < 0) {
                    break;
                }
                result += additionalWeight * Math.pow(RANK_RANGE, rankIndex--);
            }
        }
        return result;
    }

    static double getCombinationWeight(Card[] cards) {

        sortHand(cards);
        CardSuit flashCardSuit = getFlashCardSuit(cards);
        // FIXME: Potential degradation performance??
        FlashSraightCards straightFlashPair = isRoyalFlash(cards, flashCardSuit);
        if (straightFlashPair.getFlashRoyal() != -1) {
//            return 117;
            return calculateCombinationWeight(117);
        }
        int result = straightFlashPair.getStraightFlash();
        if (result != -1) {
//            return 104 + result;
            return calculateCombinationWeight(104 + result);
        }
        SameCards sameCards = getSameCards(cards);
        int[] notSameCardValues = sameCards.getNotSameCardValues();
        result = sameCards.getQuads();
        if (result != -1) {
            return calculateCombinationWeight(91 + result, notSameCardValues[0]);
        }
        Pair<Integer, Integer> doubleResult = sameCards.getFullHouse();
        if (doubleResult != null) {
//            return 78 + result;
            return calculateCombinationWeight(78 + doubleResult.getKey(), doubleResult.getValue());
        }
        result = straightFlashPair.getFlash();
        if (result != -1) {
//            return 65 + result;
            return calculateCombinationWeight(65 + result);
        }
        result = straightFlashPair.getStraight();
        if (result != -1) {
//            return 52 + result;
            return calculateCombinationWeight(52 + result);
        }
        result = sameCards.getTriple();
        if (result != -1) {
//            return 39 + result;
            return calculateCombinationWeight(39 + result, notSameCardValues[0], notSameCardValues[1]);
        }
        doubleResult = sameCards.getTwoPairs();
        if (doubleResult != null) {
//            return 26 + result;
            return calculateCombinationWeight(26 + doubleResult.getKey(),
                    doubleResult.getValue(), notSameCardValues[0]);
        }
        result = sameCards.getPair();
        if (result != -1) {
//            return 13 + result;
            return calculateCombinationWeight(13 + result,
                    notSameCardValues[0], notSameCardValues[1], notSameCardValues[2]);
        }
        return calculateCombinationWeight(notSameCardValues[0], notSameCardValues[1], notSameCardValues[2],
                notSameCardValues[3], notSameCardValues[4]);
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

    private static FlashSraightCards isRoyalFlash(Card[] cards, CardSuit flashCardSuit) {

        FlashSraightCards straightFlashPair = isStraightFlash(cards, flashCardSuit);
        if (straightFlashPair.getStraightFlash() == CardValue.ACE.ordinal()) {
            straightFlashPair.setFlashRoyal(straightFlashPair.getStraightFlash());
        }
        return straightFlashPair;
    }

    private static FlashSraightCards isStraightFlash(Card[] cards, CardSuit flashCardSuit) {

        FlashSraightCards flashSraightCards = FlashSraightCards.not();
        int straight = isStraight(cards);
        flashSraightCards.setStraight(straight);

        if (flashCardSuit == null) {
            return flashSraightCards;
        }
//        Card[] flashCards = new Card[7];
        List<Card> flashCards = new ArrayList<>();
        int i = 0;

        // FIXME: it's not calculate if straight flash in 7 suites cards - CHECK IT
        for (Card card : cards) {
            if (card.getSuit() == flashCardSuit) {
//                flashCards[i++] = card;
                flashCards.add(card);
            }
        }
        Card[] flashCardsArr = new Card[flashCards.size()];
        flashCardsArr = flashCards.toArray(flashCardsArr);
        flashSraightCards.setFlash(flashCardsArr[0].getValue().ordinal());
        if(straight >= 0) {
            flashSraightCards.setStraightFlash(isStraight(flashCardsArr));
        }
        return flashSraightCards;
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
        } else if(length <= 5) {
            return -1;
        }

        if (nextIteration == 1) {
            nextIteration = getNextIndexForStraight(cards, length, nextIteration);
        }

        if (nextIteration == -1 || nextIteration > 5) {
            return cards[1].getValue().ordinal();
        } else if(length <= 6) {
            return -1;
        }

        if (nextIteration == 2) {
            nextIteration = getNextIndexForStraight(cards, length, nextIteration);
        }

        if (nextIteration == -1) {
            return cards[2].getValue().ordinal();
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
        int[] notSameCardValues = new int[7];
        int notSameCardsIndex = 0;
        int length = cards.length;
        int repeat = 1;
        for (int i = 0; i < length - 1; i++) {
            if (cards[i].getValue() == cards[i + 1].getValue()) {
                repeat++;
            } else {
                if (repeat == 1) {
                    notSameCardValues[notSameCardsIndex++] = cards[i].getValue().ordinal();
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

    private static class FlashSraightCards {

        private int flashRoyal;
        private int straightFlash;
        private int flash;
        private int straight;

        public FlashSraightCards() {
            this.flashRoyal = -1;
            this.straightFlash = -1;
            this.flash = -1;
            this.straight = -1;
        }

        public FlashSraightCards(int flashRoyal, int straightFlash, int flash, int straight) {
            this.flashRoyal = flashRoyal;
            this.straightFlash = straightFlash;
            this.flash = flash;
            this.straight = straight;
        }

        public static FlashSraightCards not() {
            return new FlashSraightCards(-1, -1, -1, -1);
        }

        public int getFlashRoyal() {

            return flashRoyal;
        }

        public void setFlashRoyal(int flashRoyal) {

            this.flashRoyal = flashRoyal;
        }

        public int getStraightFlash() {

            return straightFlash;
        }

        public void setStraightFlash(int straightFlash) {

            this.straightFlash = straightFlash;
        }

        public int getFlash() {

            return flash;
        }

        public void setFlash(int flash) {

            this.flash = flash;
        }

        public int getStraight() {

            return straight;
        }

        public void setStraight(int straight) {

            this.straight = straight;
        }
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
        private int[] notSameCardValues;
        private int index = 0;

        SameCards() {

            sameCardsArr = new SameCard[3];
        }

        void addNotSameCardValues(int[] notSameCardValues) {

            this.notSameCardValues = notSameCardValues;
        }

        int[] getNotSameCardValues() {

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

        Pair<Integer, Integer> getFullHouse() {

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
                return new Pair<>(highTriple, highPair);
            }
            return null;
        }

        int getTriple() {

            if (isTriple) {
                for (int i = 0; i < index; i++) {
                    if (sameCardsArr[i].sameCardType == SameCardType.TRIPLE) {
                        return sameCardsArr[i].cardValue.ordinal();
                    }
                }
            }
            return -1;
        }

        Pair<Integer, Integer> getTwoPairs() {

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
                        secondPair = sameCardsArr[i].cardValue.ordinal();
                    }
                }
                return new Pair<>(highPair, secondPair);
            }
            return null;
        }

        int getPair() {

            if (isPair) {
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
