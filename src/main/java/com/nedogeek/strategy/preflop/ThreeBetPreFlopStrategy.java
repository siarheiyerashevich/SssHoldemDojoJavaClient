package com.nedogeek.strategy.preflop;

import com.nedogeek.context.GameContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.AggressorData;
import com.nedogeek.util.MoveDataAnalyzer;

import java.util.Map;
import java.util.Set;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.THREE_BET_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.THREE_BET_RAISE_COEFFICIENT;

public class ThreeBetPreFlopStrategy extends PreFlopActionStrategy {


    public double getInitialRaiseProbabilityLimit() {
        return THREE_BET_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return THREE_BET_CALL_COEFFICIENT;
    }

    public double calculateStatsBasedRaiseProbabilityLimit() {
        double minimalEntryProbability = calculateMinimalEntryProbability();
        return 1 - 0.9 * minimalEntryProbability / 2;
    }

    public double calculateStatsBasedCallProbabilityLimit() {
        double minimalEntryProbability = calculateMinimalEntryProbability();
        return 1 - 0.9 * minimalEntryProbability;
    }

    private double calculateMinimalEntryProbability() {
        AggressorData aggressorData = MoveDataAnalyzer.calculateAggressors();
        Set<String> threeBetters = aggressorData.getThreeBetters();
        Map<String, AggressionData> aggressionMap = GameContext.INSTANCE.getAggressionMap();
        double minimalEntryProbability = 1;

        for (String threeBetter : threeBetters) {
            double threeBetCount = aggressionMap.get(threeBetter).getCallCount();
            double threeBetProbability = threeBetCount / GameContext.INSTANCE.getHandsCount();
            if (minimalEntryProbability < threeBetProbability) {
                minimalEntryProbability = threeBetProbability;
            }
        }

        return minimalEntryProbability;
    }
}
