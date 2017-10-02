package com.nedogeek.strategy;

import com.nedogeek.context.StreetContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.Round;
import com.nedogeek.strategy.preflop.CallPreFlopStrategy;
import com.nedogeek.strategy.preflop.NoActionsPreFlopStrategy;
import com.nedogeek.strategy.preflop.PreFlopStrategy;
import com.nedogeek.strategy.preflop.RaisePreFlopStrategy;
import com.nedogeek.strategy.preflop.ThreeBetPreFlopStrategy;
import com.nedogeek.util.MoveDataAnalyzer;

public enum StrategyFactory {

    INSTANCE;

    private final Strategy checkStrategy = new CheckStrategy();

    private final Strategy preFlopStrategy = new PreFlopStrategy();
    private final Strategy noActionsPreFlopStrategy = new NoActionsPreFlopStrategy();
    private final Strategy callPreFlopStrategy = new CallPreFlopStrategy();
    private final Strategy raisePreFlopStrategy = new RaisePreFlopStrategy();
    private final Strategy threeBetPreFlopStrategy = new ThreeBetPreFlopStrategy();

    public Strategy calculateRoundStrategy() {
        Round round = StreetContext.INSTANCE.getRound();

        switch (round) {
            case PRE_FLOP:
                return preFlopStrategy;
            case FLOP:
            case TURN:
            case RIVER:
            case FINAL:
                return checkStrategy;
        }

        throw new IllegalArgumentException("Round not found: " + round);
    }

    public Strategy calculatePreFlopStrategy() {
        AggressionData aggressionData = MoveDataAnalyzer.calculateAggression();

        long callCount = aggressionData.getCallCount();
        long raiseCount = aggressionData.getRaiseCount();

        if (raiseCount == 0) {
            if (callCount == 0) {
                return noActionsPreFlopStrategy;
            } else {
                return callPreFlopStrategy;
            }
        } else if (raiseCount == 1) {
            return raisePreFlopStrategy;
        } else {
            return threeBetPreFlopStrategy;
        }
    }
}
