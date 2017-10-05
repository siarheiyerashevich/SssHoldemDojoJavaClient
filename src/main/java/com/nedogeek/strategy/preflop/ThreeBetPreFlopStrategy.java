package com.nedogeek.strategy.preflop;

public class ThreeBetPreFlopStrategy extends PreFlopActionStrategy {

    @Override public double getInitialRaiseProbabilityLimit() {
        return 0.95;
    }

    @Override public double getInitialCallProbabilityLimit() {
        return 0.9;
    }
}
