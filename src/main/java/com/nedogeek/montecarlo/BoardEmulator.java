package com.nedogeek.montecarlo;

import java.util.Arrays;
import java.util.SplittableRandom;

import com.nedogeek.model.Card;
import com.nedogeek.model.CardSuit;
import com.nedogeek.model.CardValue;

/**
 * Created by Sergey_Fedorchuk on 10/3/2017.
 */
public class BoardEmulator {

    private static final int BOARD_SIZE = 5;
    private static final CardSuit[] cardSuits = CardSuit.values();
    private static final CardValue[] cardValues = CardValue.values();

    public static void main (String[] args) {

        for(int i = 0; i < 1000; i++) {
            System.out.println(Arrays.toString(emulateBoard()));
        }
    }

    private static Card[] emulateBoard() {

        SplittableRandom random = new SplittableRandom();
        Card[] cards = new Card[BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            cards[i] = new Card(
                    cardSuits[random.nextInt(0, 3)],
                    cardValues[random.nextInt(0, 12)]
                    );
        }
        return cards;
    }
}
