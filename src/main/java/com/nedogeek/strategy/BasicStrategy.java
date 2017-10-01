package com.nedogeek.strategy;

import com.nedogeek.model.Commands;
import com.nedogeek.model.HandData;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Position;
import com.nedogeek.util.MoveDataAnalyzer;

public class BasicStrategy implements Strategy {

    @Override
    public MoveResponse evaluateResponse(HandData handData, MoveData moveData) {
        Position position = handData.getPosition();
        int initialCardsWeight = handData.getInitialCardsWeight();
        double winProbability = MoveDataAnalyzer.calculateHandWinProbability(moveData);

        MoveResponse callResponse = new MoveResponse(Commands.Call);

        return callResponse;
    }
}
