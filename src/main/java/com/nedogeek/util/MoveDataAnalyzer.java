package com.nedogeek.util;

import com.nedogeek.Client;
import com.nedogeek.context.MoveContext;
import com.nedogeek.model.Card;
import com.nedogeek.model.Player;
import com.nedogeek.model.Position;
import com.nedogeek.model.Round;

import java.util.ArrayList;
import java.util.List;

public class MoveDataAnalyzer {

    public static Position calculatePosition() {
        if (Client.USER_NAME.equalsIgnoreCase(MoveContext.INSTANCE.getDealer())) {
            return Position.BUTTON;
        }

        List<String> sortedPlayers =
                sortPlayersStartingFromSmallBlind();
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

    public static int calculateInitialCardsWeight() {

        List<Card> myCards = MoveContext.INSTANCE.getPlayers().stream()
                .filter(player -> Client.USER_NAME.equalsIgnoreCase(player.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + Client.USER_NAME))
                .getCards();

        Card firstCard = myCards.get(0);
        Card secondCard = myCards.get(1);

        return CardsWeightCalculator.calculatePairWeight(firstCard.getValue(), secondCard.getValue(), firstCard
                .getSuit().equals(secondCard.getSuit()));
    }

    public static double calculateHandWinProbability(int cardsWeight) {
        return (170d - cardsWeight) / 169d;
    }

    private static List<String> sortPlayersStartingFromSmallBlind() {
        List<String> result = new ArrayList<>();

        List<Player> players = MoveContext.INSTANCE.getPlayers();
        int playersCount = players.size();
        int dealerPosition = 0;
        String dealerName = MoveContext.INSTANCE.getDealer();

        for (int i = 0; i < playersCount; i++) {
            Player current = players.get(i);
            String currentName = current.getName();
            if (dealerName.equalsIgnoreCase(currentName)) {
                dealerPosition = i;
                break;
            }
        }

        if (dealerPosition < playersCount - 1) {
            for (int i = dealerPosition + 1; i < playersCount; i++) {
                result.add(players.get(i).getName());
            }
        }

        for (int i = 0; i <= dealerPosition; i++) {
            result.add(players.get(i).getName());
        }

        return result;
    }

    public static Round calculateRound() {
        String event = MoveContext.INSTANCE.getGameRound();
        switch (event) {
            case "BLIND":
                return Round.PRE_FLOP;
            case "THREE_CARDS":
                return Round.FLOP;
            case "FOUR_CARDS":
                return Round.TURN;
            case "FIVE_CARDS":
                return Round.RIVER;
            case "FINAL":
                return Round.FINAL;
        }

        throw new IllegalArgumentException("Round not found: " + event);
    }
}
