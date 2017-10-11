package com.nedogeek.strategy.postflop;

import java.util.SplittableRandom;

import com.nedogeek.Client;
import com.nedogeek.context.HandContext;
import com.nedogeek.context.MoveContext;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.util.MoveDataAnalyzer;

public class NoActionsPostFlopStrategy extends PostFlopActionStrategy {

    private static final int CONTBET_PROBABILITY_PERCENT = 80;
    private static final int CONTBET_OWNBALANCE_RANK = 3;
    private static final int CONTBET_OPPONENT_COUNT = 2;

    @Override
    public MoveResponse evaluateResponse() {

        int pot = MoveContext.INSTANCE.getPot();
        int ownBalance = MoveDataAnalyzer.calculateOwnBalance();
        MoveResponse contBetResponse = evaluateContBetResponse(pot, ownBalance);
        if(contBetResponse != null) {
            return contBetResponse;
        }

        return null;
    }

    private MoveResponse evaluateContBetResponse(int pot, int ownBalance) {

        boolean iamAggressor = Client.USER_NAME.equalsIgnoreCase(HandContext.INSTANCE.getAggressor());
        int probability = new SplittableRandom().nextInt(0, 100);
        if (MoveContext.INSTANCE.getPlayers().size() <= CONTBET_OPPONENT_COUNT
                && iamAggressor
                && probability <= CONTBET_PROBABILITY_PERCENT
                && ownBalance > CONTBET_OWNBALANCE_RANK * pot) {

            System.out.println("{\"ContBet\": {" +
                    "\"pot\":" + pot + ","
                    + "\"ownBalance\":" + ownBalance
                    + "}},");
            return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(pot/2);
        }
        return null;
    }
}
