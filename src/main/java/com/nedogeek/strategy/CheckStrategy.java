package com.nedogeek.strategy;

import com.nedogeek.model.Commands;
import com.nedogeek.model.MoveResponse;

public class CheckStrategy implements Strategy {

    @Override public MoveResponse evaluateResponse() {
        return new MoveResponse(Commands.Check);
    }
}
