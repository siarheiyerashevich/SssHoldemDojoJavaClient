package com.nedogeek.strategy.preflop;

public class NoActionsPreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return 0.9;
    }

    public double getInitialCallProbabilityLimit() {
        return 0.8;
    }
}
