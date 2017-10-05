package com.nedogeek.strategy.preflop;

import com.nedogeek.model.MoveResponse;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.util.MoveDataAnalyzer;

public class RaisePreFlopStrategy implements Strategy {

    private double raiseProbabilityLimit = 0.93;
    private double callProbabilityLimit = 0.86;

    @Override
    public MoveResponse evaluateResponse() {
        double winProbability = MoveDataAnalyzer.calculateHandWinProbability();
        if (winProbability >= raiseProbabilityLimit) {
            return MoveResponse.RAISE_MOVE_RESPONSE.withAmount(null);
        } else if (winProbability >= callProbabilityLimit) {
            return MoveResponse.CALL_MOVE_RESPONSE;
        } else {
            return MoveResponse.CHECK_MOVE_RESPONSE;
        }
    }
}
