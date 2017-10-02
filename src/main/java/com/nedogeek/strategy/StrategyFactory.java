package com.nedogeek.strategy;

import com.nedogeek.context.StreetContext;
import com.nedogeek.model.Round;

public class StrategyFactory {

    private final Strategy checkStrategy = new CheckStrategy();
    private final Strategy preFlopStrategy = new PreFlopStrategy();

    public Strategy getRoundStrategy() {
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
}
