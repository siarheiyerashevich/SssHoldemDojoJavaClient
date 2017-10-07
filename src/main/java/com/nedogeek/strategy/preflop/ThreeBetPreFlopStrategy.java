package com.nedogeek.strategy.preflop;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.THREE_BET_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.THREE_BET_RAISE_COEFFICIENT;

public class ThreeBetPreFlopStrategy extends PreFlopActionStrategy {


    public double getInitialRaiseProbabilityLimit() {
        return THREE_BET_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return THREE_BET_CALL_COEFFICIENT;
    }
}
