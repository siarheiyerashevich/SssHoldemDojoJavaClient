package com.nedogeek.strategy.preflop;

import com.nedogeek.model.Commands;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.util.MoveDataAnalyzer;

public class ThreeBetPreFlopStrategy implements Strategy {

    private double raiseProbabilityLimit = 0.95;
    private double callProbabilityLimit = 0.9;

    @Override public MoveResponse evaluateResponse() {
        double winProbability = MoveDataAnalyzer.calculateHandWinProbability();
        if (winProbability >= raiseProbabilityLimit) {
            return new MoveResponse(Commands.Rise);
        } else if (winProbability >= callProbabilityLimit) {
            return new MoveResponse(Commands.Call);
        } else {
            return new MoveResponse(Commands.Check);
        }
    }
}
