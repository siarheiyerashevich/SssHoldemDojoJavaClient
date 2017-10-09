package com.nedogeek.strategy.preflop;

import com.nedogeek.context.GameContext;
import com.nedogeek.model.AggressionData;
import com.nedogeek.model.AggressorData;
import com.nedogeek.util.MoveDataAnalyzer;

import java.util.Map;
import java.util.Set;

import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.FOUR_BET_PLUS_CALL_COEFFICIENT;
import static com.nedogeek.strategy.preflop.PreFlopActionCoefficients.FOUR_BET_PLUS_RAISE_COEFFICIENT;

public class FourBetPlusPreFlopStrategy extends PreFlopActionStrategy {

    public double getInitialRaiseProbabilityLimit() {
        return FOUR_BET_PLUS_RAISE_COEFFICIENT;
    }

    public double getInitialCallProbabilityLimit() {
        return FOUR_BET_PLUS_CALL_COEFFICIENT;
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
        Set<String> fourBetPlusBetters = aggressorData.getFourBetPlusBetters();
        Map<String, AggressionData> aggressionMap = GameContext.INSTANCE.getAggressionMap();
        double minimalEntryProbability = 1;

        for (String fourBetPlusBetter : fourBetPlusBetters) {
            double fourBetPlusCount = aggressionMap.get(fourBetPlusBetter).getFourBetPlusCount();
            double fourBetPlusProbability = fourBetPlusCount / GameContext.INSTANCE.getHandsCount();
            if (minimalEntryProbability < fourBetPlusProbability) {
                minimalEntryProbability = fourBetPlusProbability;
            }
        }

        return minimalEntryProbability;
    }
}
