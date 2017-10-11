package com.nedogeek.strategy;

import com.nedogeek.context.StreetContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.Round;
import com.nedogeek.strategy.postflop.NoActionsPostFlopStrategy;
import com.nedogeek.strategy.postflop.PostFlopStrategy;
import com.nedogeek.strategy.postflop.RaisePostFlopStrategy;
import com.nedogeek.strategy.postflop.ThreeBetPlusPostFlopStrategy;
import com.nedogeek.strategy.preflop.CallPreFlopStrategy;
import com.nedogeek.strategy.preflop.FourBetPlusPreFlopStrategy;
import com.nedogeek.strategy.preflop.NoActionsPreFlopStrategy;
import com.nedogeek.strategy.preflop.PreFlopStrategy;
import com.nedogeek.strategy.preflop.RaisePreFlopStrategy;
import com.nedogeek.strategy.preflop.ThreeBetPreFlopStrategy;
import com.nedogeek.util.MoveDataAnalyzer;

public enum StrategyFactory {

    INSTANCE;

    // Pre-flop strategies
    private final Strategy preFlopStrategy = new PreFlopStrategy();
    private final Strategy noActionsPreFlopStrategy = new NoActionsPreFlopStrategy();
    private final Strategy callPreFlopStrategy = new CallPreFlopStrategy();
    private final Strategy raisePreFlopStrategy = new RaisePreFlopStrategy();
    private final Strategy threeBetPreFlopStrategy = new ThreeBetPreFlopStrategy();
    private final Strategy fourBetPlusPreFlopStrategy = new FourBetPlusPreFlopStrategy();

    // Post-flop strategies
    private final Strategy postFlopStrategy = new PostFlopStrategy();
    private final Strategy noActionsPostFlopStrategy = new NoActionsPostFlopStrategy();
    private final Strategy raisePostFlopStrategy = new RaisePostFlopStrategy();
    private final Strategy threeBetPlusPostFlopStrategy = new ThreeBetPlusPostFlopStrategy();

    private final Strategy checkStrategy = new CheckStrategy();

    public Strategy calculateRoundStrategy() {

        Round round = StreetContext.INSTANCE.getRound();

        switch (round) {
            case PRE_FLOP:
                return preFlopStrategy;
            case FLOP:
            case TURN:
            case RIVER:
            case FINAL:
                return postFlopStrategy;
            case INITIAL:
                return checkStrategy;
        }

        throw new IllegalArgumentException("Round not found: " + round);
    }

    public Strategy calculatePreFlopStrategy() {

        AggressionData aggressionData = MoveDataAnalyzer.calculateAggression();

        long callCount = aggressionData.getCallCount();
        long raiseCount = aggressionData.getRaiseCount();
        long fourBetPlusCount = aggressionData.getFourBetPlusCount();

        if (raiseCount == 0) {
            if (callCount == 0) {
                return noActionsPostFlopStrategy;
            } else {
                return callPreFlopStrategy;
            }
        } else if (raiseCount == 1) {
            return raisePreFlopStrategy;
        } else if (fourBetPlusCount > 0) {
            return fourBetPlusPreFlopStrategy;
        } else {
            return threeBetPreFlopStrategy;
        }
    }

    public Strategy calculatePostFlopStrategy() {

        AggressionData aggressionData = MoveDataAnalyzer.calculateAggression();

        long raiseCount = aggressionData.getRaiseCount();

        if (raiseCount == 0) {
            return noActionsPreFlopStrategy;
        } else if (raiseCount == 1) {
            return raisePostFlopStrategy;
        } else {
            return raisePostFlopStrategy;
        }
    }
}
