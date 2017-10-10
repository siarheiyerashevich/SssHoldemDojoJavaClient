package com.nedogeek.strategy.postflop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nedogeek.Client;
import com.nedogeek.context.HandContext;
import com.nedogeek.context.MoveContext;
import com.nedogeek.model.AggressionDataCalculator;
import com.nedogeek.model.Card;
import com.nedogeek.model.Hand;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.PlayerStatus;
import com.nedogeek.montecarlo.GameEmulator;
import com.nedogeek.strategy.Strategy;

public class PostFlopActionStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse() {

        // TODO: TBD
        return null;
    }

    protected double getEquity(List<String> opponents) {

        List<Card> myCards = MoveContext.INSTANCE.getPlayers().stream()
                .filter(player -> Client.USER_NAME.equalsIgnoreCase(player.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + Client.USER_NAME))
                .getCards();
        Hand myHand = Hand.of(myCards.get(0), myCards.get(1));
        Card[] board = MoveContext.INSTANCE.getDeskCards().toArray(new Card[0]);

        Map<String, PlayerStatus> preFlopStatusMap = HandContext.INSTANCE.getPreFlopStatusMap();
        List<Integer> playersCardsPercentage = new ArrayList<>(preFlopStatusMap.size() - 1);
        for (Entry<String, PlayerStatus> playerStatusEntry : preFlopStatusMap.entrySet()) {
            String playerName = playerStatusEntry.getKey();
            if (opponents.contains(playerName)) {
                playersCardsPercentage.add(AggressionDataCalculator
                        .getPlayerCardPercentage(playerName, playerStatusEntry.getValue().getStatus()));
            }
        }
        return GameEmulator.emulateGamesAfterFlop(myHand, board, playersCardsPercentage);
    }
}
