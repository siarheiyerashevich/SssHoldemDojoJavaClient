package com.nedogeek.model;

import com.nedogeek.context.GameContext;

public class AggressionDataCalculator {

    public static int getPlayerCardPercentage(String playerName, String preFlopStatus) {
        AggressionData aggressionData = GameContext.INSTANCE.getAggressionMap().get(playerName);
        double winProbability = 0;
        if(preFlopStatus.equalsIgnoreCase("Call")) {
            winProbability = getCallProbability(aggressionData);
        } else if(preFlopStatus.equalsIgnoreCase("Rise")) {
            winProbability = getRaiseProbability(aggressionData);
        } else if(preFlopStatus.equalsIgnoreCase("ThreeBet")) {
            winProbability = getThreeBetProbability(aggressionData);
        } else if(preFlopStatus.equalsIgnoreCase("FourPlusBet")) {
            winProbability = getFourBetPlusProbability(aggressionData);
        }
        return (int) (100 * winProbability);
    }

    // FIXME: What should be done if ZERO??
    public static double getCallProbability(AggressionData aggressionData) {

        double callCount =
                aggressionData.getCallCount() + aggressionData.getRaiseCount() + aggressionData.getThreeBetCount() +
                        aggressionData.getFourBetPlusCount();
        return callCount / GameContext.INSTANCE.getHandsCount();
    }

    public static double getRaiseProbability(AggressionData aggressionData) {

        double raiseCount = aggressionData.getRaiseCount() + aggressionData.getThreeBetCount() +
                aggressionData.getFourBetPlusCount();
        return raiseCount / GameContext.INSTANCE.getHandsCount();
    }

    public static double getThreeBetProbability(AggressionData aggressionData) {

        double threeBetCount = aggressionData.getThreeBetCount() + aggressionData.getFourBetPlusCount();
        return threeBetCount / GameContext.INSTANCE.getHandsCount();
    }

    public static double getFourBetPlusProbability(AggressionData aggressionData) {

        return aggressionData.getFourBetPlusCount() / GameContext.INSTANCE.getHandsCount();
    }
}
