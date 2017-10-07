package com.nedogeek.strategy.preflop;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.NO_ACTIONS_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.NO_ACTIONS_RAISE_COEFFICIENT;

public class NoActionsPreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return NO_ACTIONS_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return NO_ACTIONS_CALL_COEFFICIENT;
    }
}
