package com.nedogeek.strategy;

import com.nedogeek.model.Commands;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.MoveResponse;
import com.nedogeek.model.Position;
import com.nedogeek.util.MoveDataAnalyzer;

public class BasicStrategy implements Strategy {

    MoveDataAnalyzer dataAnalyzer = new MoveDataAnalyzer();

    @Override
    public MoveResponse evaluateResponse(MoveData moveData) {
        Position position = dataAnalyzer.calculatePosition(moveData);
        double winProbability = dataAnalyzer.calculateHandWinProbability(moveData);

        MoveResponse callResponse = new MoveResponse(Commands.Call);

        return callResponse;
    }
}
