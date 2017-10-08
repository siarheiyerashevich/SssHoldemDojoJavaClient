package com.nedogeek.strategy.preflop;

import com.nedogeek.context.GameContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.AggressorData;
import com.nedogeek.util.MoveDataAnalyzer;

import java.util.Map;
import java.util.Set;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.CALL_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.CALL_RAISE_COEFFICIENT;

public class CallPreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return CALL_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return CALL_CALL_COEFFICIENT;
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
        Set<String> callers = aggressorData.getCallers();
        Map<String, AggressionData> aggressionMap = GameContext.INSTANCE.getAggressionMap();
        double minimalEntryProbability = 1;

        for (String caller : callers) {
            AggressionData aggressionData = aggressionMap.get(caller);
            double callCount =
                    aggressionData.getCallCount() + aggressionData.getRaiseCount() + aggressionData.getThreeBetCount();
            double callProbability = callCount / GameContext.INSTANCE.getHandsCount();
            if (minimalEntryProbability < callProbability) {
                minimalEntryProbability = callProbability;
            }
        }

        return minimalEntryProbability;
    }
}
