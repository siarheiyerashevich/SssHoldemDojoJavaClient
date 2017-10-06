package com.nedogeek.strategy.preflop;

public class CallPreFlopStrategy extends PreFlopActionStrategy {

    @Override public double getInitialRaiseProbabilityLimit() {
        return 0.9;
    }

    @Override public double getInitialCallProbabilityLimit() {
        return 0.8;
    }
}
