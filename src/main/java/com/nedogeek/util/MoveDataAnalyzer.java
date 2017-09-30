package com.nedogeek.util;

import com.nedogeek.Client;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.Position;

public class MoveDataAnalyzer {

    public Position calculatePosition(MoveData moveData) {
        if (Client.USER_NAME.equalsIgnoreCase(moveData.getDealer())) {
            return Position.BUTTON;
        }

        return null;
    }

    public double calculateHandWinProbability(MoveData moveData) {
        return 0;
    }
}
