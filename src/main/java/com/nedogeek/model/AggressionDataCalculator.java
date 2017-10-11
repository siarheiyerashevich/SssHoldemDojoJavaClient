package com.nedogeek.model;

import com.nedogeek.context.GameContext;

public class AggressionDataCalculator {

    public static int getPlayerCardPercentage(String playerName, String preFlopStatus) {

        AggressionData aggressionData = GameContext.INSTANCE.getAggressionMap().get(playerName);
        double cardsPercentage = 0;
        if (preFlopStatus == null) {
            cardsPercentage = 100;
        } else if (preFlopStatus.equalsIgnoreCase("Call")) {
            cardsPercentage = getCallProbability(aggressionData);
        } else if (preFlopStatus.equalsIgnoreCase("Rise")) {
            cardsPercentage = getRaiseProbability(aggressionData);
        } else if (preFlopStatus.equalsIgnoreCase("ThreeBet")) {
            cardsPercentage = getThreeBetProbability(aggressionData);
        } else if (preFlopStatus.equalsIgnoreCase("FourPlusBet")) {
            cardsPercentage = getFourBetPlusProbability(aggressionData);
        }
        return (int) (100 * cardsPercentage);
    }

    // FIXME: What should be done if ZERO??
    public static double getCallProbability(AggressionData aggressionData) {

        int handsCount = GameContext.INSTANCE.getHandsCount();
        if (handsCount >= GameContext.MINIMAL_VALID_STATS_HAND_COUNT) {
            double callCount =
                    aggressionData.getCallCount() + aggressionData.getRaiseCount() + aggressionData.getThreeBetCount() +
                            aggressionData.getFourBetPlusCount();
            return callCount / GameContext.INSTANCE.getHandsCount();
        } else {
            return 30;
        }
    }

    public static double getRaiseProbability(AggressionData aggressionData) {

        int handsCount = GameContext.INSTANCE.getHandsCount();
        if (handsCount >= GameContext.MINIMAL_VALID_STATS_HAND_COUNT) {
            double raiseCount = aggressionData.getRaiseCount() + aggressionData.getThreeBetCount() +
                    aggressionData.getFourBetPlusCount();
            return raiseCount / GameContext.INSTANCE.getHandsCount();
        } else {
            return 15;
        }
    }

    public static double getThreeBetProbability(AggressionData aggressionData) {

        int handsCount = GameContext.INSTANCE.getHandsCount();
        if (handsCount >= GameContext.MINIMAL_VALID_STATS_HAND_COUNT) {
            double threeBetCount = aggressionData.getThreeBetCount() + aggressionData.getFourBetPlusCount();
            return threeBetCount / GameContext.INSTANCE.getHandsCount();
        } else {
            return 5;
        }
    }

    public static double getFourBetPlusProbability(AggressionData aggressionData) {

        int handsCount = GameContext.INSTANCE.getHandsCount();
        if (handsCount >= GameContext.MINIMAL_VALID_STATS_HAND_COUNT) {
            return aggressionData.getFourBetPlusCount() / GameContext.INSTANCE.getHandsCount();
        } else {
            return 3;
        }
    }
}
