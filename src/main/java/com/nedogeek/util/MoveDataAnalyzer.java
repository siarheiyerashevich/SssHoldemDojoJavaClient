package com.nedogeek.util;

import com.nedogeek.Client;
import com.nedogeek.model.Card;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.Position;

import java.util.List;

public class MoveDataAnalyzer {

    public static Position calculatePosition(MoveData moveData) {
        if (Client.USER_NAME.equalsIgnoreCase(moveData.getDealer())) {
            return Position.BUTTON;
        }

        return null;
    }

    public static int calculateInitialCardsWeight(MoveData moveData) {

        List<Card> myCards = moveData.getPlayers().stream()
                .filter(player -> Client.USER_NAME.equalsIgnoreCase(player.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player " + Client.USER_NAME + " not found"))
                .getCards();

        Card firstCard = myCards.get(0);
        Card secondCard = myCards.get(1);

        return CardsWeightCalculator.calculatePairWeight(firstCard.getValue(), secondCard.getValue(), firstCard
                .getSuit().equals(secondCard.getSuit()));
    }

    public static double calculateHandWinProbability(MoveData moveData) {
        return 0;
    }
}
