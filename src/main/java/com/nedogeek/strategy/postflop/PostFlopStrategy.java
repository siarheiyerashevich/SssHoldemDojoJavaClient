package com.nedogeek.strategy.postflop;

import com.nedogeek.model.MoveResponse;
import com.nedogeek.strategy.Strategy;
import com.nedogeek.strategy.StrategyFactory;

public class PostFlopStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse() {
        Strategy currentStrategy = StrategyFactory.INSTANCE.calculatePostFlopStrategy();
        return currentStrategy.evaluateResponse();
    }
}
