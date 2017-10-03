package com.nedogeek.montecarlo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;

public class GameEmulator {

    private static final int HAND_SIZE = 2;
    private static final int BOARD_SIZE = 5;
    private static final CardSuit[] cardSuits = CardSuit.values();
    private static final CardValue[] cardValues = CardValue.values();

    public static void main(String[] args) {

        Card[] myHand = new Card[2];
        myHand[0] = new Card(CardSuit.SPADES, CardValue.ACE);
        myHand[1] = new Card(CardSuit.HEARTS, CardValue.ACE);

        int playerCount = 2;
        int emulationCount = 1000000;

        long time = System.currentTimeMillis();
        double[] equities = emulateGames(myHand, playerCount, emulationCount);
        time = System.currentTimeMillis() - time;
        System.out.println("Emulation count: " + emulationCount + ", time: " + time + " ms");
        System.out.println("Me: " + (equities[equities.length - 1] * 100) + "%");
        for (int i = 0; i < equities.length - 1; i++) {
            System.out.println("Player " + (i + 1) + ": " + (equities[i] * 100) + "%");
        }
    }

    public static double[] emulateGames(Card[] myHand, int playersCount, int emulationCount) {

        double[] playersEquityArr = new double[playersCount + 1];
        for (int j = 0; j < playersCount + 1; j++) {
            playersEquityArr[j] = 0;
        }
        // TODO: Emulate games untill 200-300 ms is ended and break it
        for (int i = 0; i < emulationCount; i++) {
            double[] gameResults = emulateGame(myHand, playersCount);
            for (int j = 0; j < playersCount + 1; j++) {
                playersEquityArr[j] += gameResults[j];
            }
        }
        for (int j = 0; j < playersCount + 1; j++) {
            playersEquityArr[j] = playersEquityArr[j] / emulationCount;
        }
        return playersEquityArr;
    }

    public static double[] emulateGame(Card[] myHand, int playersCount) {

        // TODO: is it necessary to replace to array?
        List<Card> existedCards = new ArrayList<>(HAND_SIZE + BOARD_SIZE + playersCount * HAND_SIZE);
        existedCards.addAll(Arrays.asList(myHand));

        Card[] board = emulateBoard(existedCards);
        int[] playersCombinations = new int[playersCount + 1];
        for (int i = 0; i < playersCount; i++) {
            Card[] playerHand = emulateHand(existedCards);
            playersCombinations[i] = FastCombination.getCombinationWeight(playerHand, board);
        }
        playersCombinations[playersCount] = FastCombination.getCombinationWeight(myHand, board);

        return calculateGameResults(playersCombinations);
    }

    private static double[] calculateGameResults(int[] combinations) {

        int max = 0;
        int len = combinations.length;
        double[] results = new double[len];
        for (int combination : combinations) {
            if (combination > max) {
                max = combination;
            }
        }
        // TODO: Calculate average if some players win
        int countWinPlayers = 0;
        for (int i = 0; i < len; i++) {
            if (combinations[i] == max) {
                countWinPlayers++;
            }
        }
        // TODO: Check for zero but it's not possible
        double winPercentage = 1d / countWinPlayers;
        for (int i = 0; i < len; i++) {
            results[i] = combinations[i] == max ? winPercentage : 0;
        }
        return results;
    }

    private static Card[] emulateHand(List<Card> existedCards) {

        return emulateCards(HAND_SIZE, existedCards);
    }

    private static Card[] emulateBoard(List<Card> existedCards) {

        return emulateCards(BOARD_SIZE, existedCards);
    }

    private static Card[] emulateCards(int count, List<Card> existedCards) {

        SplittableRandom random = new SplittableRandom();
        Card[] cards = new Card[count];
        for (int i = 0; i < count; i++) {
            Card card;
            do {
                card = new Card(
                        cardSuits[random.nextInt(0, 3)],
                        cardValues[random.nextInt(0, 12)]
                );
            } while (existedCards.contains(card));
            cards[i] = card;
            existedCards.add(card);
        }
        return cards;
    }
}
