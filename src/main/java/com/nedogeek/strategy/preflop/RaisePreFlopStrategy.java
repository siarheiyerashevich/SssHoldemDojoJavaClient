package com.nedogeek.strategy.preflop;

import com.nedogeek.context.GameContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.AggressorData;
import com.nedogeek.util.MoveDataAnalyzer;

import java.util.Map;
import java.util.Set;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.RAISE_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.RAISE_RAISE_COEFFICIENT;

public class RaisePreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return RAISE_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return RAISE_CALL_COEFFICIENT;
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
        Set<String> raisers = aggressorData.getRaisers();
        Map<String, AggressionData> aggressionMap = GameContext.INSTANCE.getAggressionMap();
        double minimalEntryProbability = 1;

        for (String raiser : raisers) {
            AggressionData aggressionData = aggressionMap.get(raiser);
            double raiseCount = aggressionData.getRaiseCount() + aggressionData.getThreeBetCount() +
                                aggressionData.getFourBetPlusCount();
            double raiseProbability = raiseCount / GameContext.INSTANCE.getHandsCount();
            if (minimalEntryProbability < raiseProbability) {
                minimalEntryProbability = raiseProbability;
            }
        }

        return minimalEntryProbability;
    }
}
