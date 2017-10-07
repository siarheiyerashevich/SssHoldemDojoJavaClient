package com.nedogeek.strategy.preflop;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.RAISE_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.RAISE_RAISE_COEFFICIENT;

public class RaisePreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return RAISE_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return RAISE_CALL_COEFFICIENT;
    }
}
