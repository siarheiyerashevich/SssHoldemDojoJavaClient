package com.nedogeek.strategy;

import com.nedogeek.model.HandData;
import com.nedogeek.model.MoveData;
import com.nedogeek.model.MoveResponse;

public interface Strategy {

    MoveResponse evaluateResponse(HandData handData, MoveData moveData);
}
