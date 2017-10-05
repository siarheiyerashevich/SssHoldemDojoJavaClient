package com.nedogeek.strategy.preflop;

public class RaisePreFlopStrategy extends PreFlopActionStrategy {

    @Override public double getInitialRaiseProbabilityLimit() {
        return 0.93;
    }

    @Override public double getInitialCallProbabilityLimit() {
        return 0.86;
    }
}
