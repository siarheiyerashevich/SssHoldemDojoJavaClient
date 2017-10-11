package com.nedogeek.strategy.postflop;

import java.util.List;
import java.util.SplittableRandom;

import com.nedogeek.Client;
import com.nedogeek.context.HandContext;
import com.nedogeek.context.MoveContext;
import com.nedogeek.model.Card;
import com.nedogeek.model.Hand;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.montecarlo.FastCombination;
import com.nedogeek.util.MoveDataAnalyzer;

public class NoActionsPostFlopStrategy extends PostFlopActionStrategy {

    private static final int CONTBET_PROBABILITY_PERCENT = 80;
    private static final int CONTBET_OWNBALANCE_RANK = 3;
    private static final int CONTBET_OPPONENT_COUNT = 2;

    @Override
    public MoveResponse evaluateResponse() {

        int pot = MoveContext.INSTANCE.getPot();
        int ownBalance = MoveDataAnalyzer.calculateOwnBalance();
        boolean iamAggressor = Client.USER_NAME.equalsIgnoreCase(HandContext.INSTANCE.getAggressor());
        MoveResponse contBetResponse = evaluateContBetResponse(pot, ownBalance, iamAggressor);
        if (contBetResponse != null) {
            return contBetResponse;
        }

        List<Card> myCards = MoveContext.INSTANCE.getPlayers().stream()
                .filter(player -> Client.USER_NAME.equalsIgnoreCase(player.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + Client.USER_NAME))
                .getCards();
        Hand myHand = Hand.of(myCards.get(0), myCards.get(1));
        Card[] board = MoveContext.INSTANCE.getDeskCards().toArray(new Card[0]);
        FastCombination.sortHand(board);
        double combinationWeight = FastCombination.getCombinationWeight(myHand, board);

        boolean combinationWithHand = true;
        boolean highSameCards = true;
        if (combinationWeight >= FastCombination.MIN_PAIR_WEIGHT
                && combinationWeight < FastCombination.MIN_TWOPAIR_WEIGHT) {
            int pairMainWeight = (int) (combinationWeight - FastCombination.MIN_PAIR_WEIGHT);
            combinationWithHand = myHand.getFirstCard().getValue().ordinal() == pairMainWeight ||
                    myHand.getSecondCard().getValue().ordinal() == pairMainWeight;
//            highSameCards = board[0].getValue().ordinal() == pairMainWeight;
        }
        if (combinationWeight >= FastCombination.MIN_TRIPLE_WEIGHT
                && combinationWeight < FastCombination.MIN_STRAIGHT_WEIGHT) {
            int tripleMainWeight = (int) (combinationWeight - FastCombination.MIN_TRIPLE_WEIGHT);
            combinationWithHand = myHand.getFirstCard().getValue().ordinal() == tripleMainWeight ||
                    myHand.getSecondCard().getValue().ordinal() == tripleMainWeight;
//            highSameCards = board[0].getValue().ordinal() == tripleMainWeight;
        }

        // Make a raise for 1/2 pot when you're are aggressor and par++ combination on hand
        if (iamAggressor && combinationWeight >= FastCombination.MIN_PAIR_WEIGHT
                && combinationWithHand) {
            return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(pot / 2);
        }

        // Make a raise for 1/2 pot when you're are not an aggressor and high par++ combination on hand
        if (!iamAggressor && combinationWeight >= FastCombination.MIN_PAIR_WEIGHT
                && combinationWithHand && highSameCards) {
            return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(pot / 2);
        }

        return MoveResponse.CHECK_MOVE_RESPONSE;
    }

    private MoveResponse evaluateContBetResponse(int pot, int ownBalance, boolean iamAggressor) {

        int probability = new SplittableRandom().nextInt(0, 100);
        if (MoveContext.INSTANCE.getPlayers().size() <= CONTBET_OPPONENT_COUNT
                && iamAggressor
                && probability <= CONTBET_PROBABILITY_PERCENT
                && ownBalance > CONTBET_OWNBALANCE_RANK * pot) {

            System.out.println("{\"ContBet\": {" +
                    "\"pot\":" + pot + ","
                    + "\"ownBalance\":" + ownBalance
                    + "}},");
            return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(pot / 2);
        }
        return null;
    }
}
