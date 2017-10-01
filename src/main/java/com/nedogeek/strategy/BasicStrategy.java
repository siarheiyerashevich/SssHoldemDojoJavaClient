package com.nedogeek.strategy;

import com.nedogeek.model.Commands;
import com.nedogeek.model.HandData;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Position;
import com.nedogeek.util.MoveDataAnalyzer;

public class BasicStrategy implements Strategy {

    private static final double DEFAULT_WIN_RATE = 0.9;

    @Override
    public MoveResponse evaluateResponse(HandData handData, MoveData moveData) {
        Position position = handData.getPosition();
        int initialCardsWeight = handData.getInitialCardsWeight();
        double winProbability = MoveDataAnalyzer.calculateHandWinProbability(initialCardsWeight);
        double currentWinRate = calculateCurrentWinRate(position);

        return currentWinRate >= winProbability ? new MoveResponse(Commands.Call) : new MoveResponse(Commands.Check);
    }

    private double calculateCurrentWinRate(Position position) {
        switch (position) {
            case MIDDLE_POSITION:
                return DEFAULT_WIN_RATE;
            case CUT_OFF:
            case BUTTON:
                return DEFAULT_WIN_RATE + 0.015;
            case SMALL_BLIND:
            case BIG_BLIND:
                return DEFAULT_WIN_RATE + 0.03;
            case UNDER_THE_GUN:
                return DEFAULT_WIN_RATE - 0.03;
        }

        throw new IllegalArgumentException("Invalid position: " + position);
    }
}
