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
import com.nedogeek.model.HandStorage;
import com.nedogeek.model.HandsRange;
import com.nedogeek.model.PlayerStatus;
import com.nedogeek.montecarlo.FastCombination;
import com.nedogeek.montecarlo.GameEmulator;
import com.nedogeek.strategy.Strategy;

public abstract class PostFlopActionStrategy implements Strategy {

    protected double getEquityVsPairPlus(List<String> opponents) {

        List<Card> myCards = MoveContext.INSTANCE.getPlayers().stream()
                .filter(player -> Client.USER_NAME.equalsIgnoreCase(player.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + Client.USER_NAME))
                .getCards();
        Hand myHand = Hand.of(myCards.get(0), myCards.get(1));
        Card[] board = MoveContext.INSTANCE.getDeskCards().toArray(new Card[0]);

        Map<String, PlayerStatus> preFlopStatusMap = HandContext.INSTANCE.getPreFlopStatusMap();
        List<Integer> preFlopPlayersCardsPercentage = new ArrayList<>(preFlopStatusMap.size() - 1);
        for (Entry<String, PlayerStatus> playerStatusEntry : preFlopStatusMap.entrySet()) {
            String playerName = playerStatusEntry.getKey();
            if (opponents.contains(playerName)) {
                preFlopPlayersCardsPercentage.add(AggressionDataCalculator
                        .getPlayerCardPercentage(playerName, playerStatusEntry.getValue().getStatus()));
            }
        }
        List<HandsRange> strengthenedHandsOfPlayers = new ArrayList<>(preFlopPlayersCardsPercentage.size());
        int playerIndex = 0;
        for(Integer preFlopPlayerCardsPercentage : preFlopPlayersCardsPercentage) {

            HandsRange handsRange = HandStorage.getInstance().getHandsRange2(preFlopPlayerCardsPercentage);
            handsRange.getHands().removeIf(myHand::partiallyEquals);
            List<Hand> strengthenedHands = new ArrayList<>();
            for(Hand hand : handsRange.getHands()) {
                double combinationWeight = FastCombination.getCombinationWeight(hand, board);
                if(combinationWeight < FastCombination.MIN_PAIR_WEIGHT) {
                    strengthenedHands.add(hand);
                }
            }
            strengthenedHandsOfPlayers.add(new HandsRange(strengthenedHands, playerIndex++));
        }
        return GameEmulator.emulateGamesAfterFlopWithHandsRange(myHand, board, strengthenedHandsOfPlayers);
    }
}
