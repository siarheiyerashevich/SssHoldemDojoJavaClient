package com.nedogeek.strategy.preflop;

import com.nedogeek.model.MoveResponse;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.strategy.StrategyFactory;

public class PreFlopStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse() {
        Strategy currentStrategy = StrategyFactory.INSTANCE.calculatePreFlopStrategy();
        return currentStrategy.evaluateResponse();
    }
}
