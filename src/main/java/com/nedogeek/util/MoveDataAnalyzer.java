package com.nedogeek.util;

import com.nedogeek.Client;
import com.nedogeek.model.Card;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.Player;
import com.nedogeek.model.Position;

import java.util.ArrayList;
import java.util.List;

public class MoveDataAnalyzer {

    public static Position calculatePosition(MoveData moveData) {
        if (Client.USER_NAME.equalsIgnoreCase(moveData.getDealer())) {
            return Position.BUTTON;
        }

        List<String> sortedPlayers = sortStartingFromSmallBlind(moveData.getPlayers(), moveData.getDealer());
        if (Client.USER_NAME.equalsIgnoreCase(sortedPlayers.get(0))) {
            return Position.SMALL_BLIND;
        }
        if (Client.USER_NAME.equalsIgnoreCase(sortedPlayers.get(1))) {
            return Position.BIG_BLIND;
        }

        int playersCount = sortedPlayers.size();
        if (Client.USER_NAME.equalsIgnoreCase(sortedPlayers.get(playersCount - 2))) {
            return Position.CUT_OFF;
        }
        if (Client.USER_NAME.equalsIgnoreCase(sortedPlayers.get(2))) {
            return Position.UNDER_THE_GUN;
        }

        // TODO:
        // divide UTG/MP/CU for different sizes

        return Position.MIDDLE_POSITION;
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

    public static double calculateHandWinProbability(int cardsWeight) {
        return (170d - cardsWeight) / 169d;
    }

    private static List<String> sortStartingFromSmallBlind(List<Player> initial, String dealerName) {
        List<String> result = new ArrayList<>();

        int playersCount = initial.size();
        int dealerPosition = 0;
        for (int i = 0; i < playersCount; i++) {
            Player current = initial.get(i);
            String currentName = current.getName();
            if (dealerName.equalsIgnoreCase(currentName)) {
                dealerPosition = i;
                break;
            }
        }

        if (dealerPosition < playersCount - 1) {
            for (int i = dealerPosition + 1; i < playersCount; i++) {
                result.add(initial.get(i).getName());
            }
        }

        for (int i = 0; i <= dealerPosition; i++) {
            result.add(initial.get(i).getName());
        }

        return result;
    }
}
