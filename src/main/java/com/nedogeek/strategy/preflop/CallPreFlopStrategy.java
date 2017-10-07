package com.nedogeek.strategy.preflop;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.CALL_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.CALL_RAISE_COEFFICIENT;

public class CallPreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return CALL_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return CALL_CALL_COEFFICIENT;
    }
}
